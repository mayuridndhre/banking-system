package service;

import dao.AccountDAO;
import dao.TransactionDAO;
import dao.DBConnection;
import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class BankService {

    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO txDAO = new TransactionDAO();

    // Open account with initial deposit -> atomic (account + initial transaction)
    public Account openAccount(int customerId, String type, double initialDeposit) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);

                // create account
                Account acc = accountDAO.createAccount(conn, customerId, type, initialDeposit);
                if (acc == null) throw new Exception("Account creation failed");

                // record initial deposit (if deposit > 0)
                if (initialDeposit > 0) {
                    txDAO.record(conn, acc.getId(), "deposit", initialDeposit, "Initial deposit");
                }

                conn.commit();
                return acc;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

 // Deposit (uses SELECT ... FOR UPDATE to lock row)
    public void deposit(int accountId, double amount, String desc) throws Exception {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");
        try (Connection conn = DBConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);

                // lock row
                double balance;
                String sel = "SELECT balance FROM account WHERE id = ? FOR UPDATE"; // ✅ fixed
                try (PreparedStatement ps = conn.prepareStatement(sel)) {
                    ps.setInt(1, accountId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new Exception("Account not found");
                        balance = rs.getDouble("balance");
                    }
                }

                double newBalance = balance + amount;
                accountDAO.updateBalance(conn, accountId, newBalance);
                txDAO.record(conn, accountId, "deposit", amount, desc == null ? "Deposit" : desc);

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    // Withdraw (checks sufficient balance)
    public void withdraw(int accountId, double amount, String desc) throws Exception {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");
        try (Connection conn = DBConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);

                double balance;
                String sel = "SELECT balance FROM account WHERE id = ? FOR UPDATE";
                try (PreparedStatement ps = conn.prepareStatement(sel)) {
                    ps.setInt(1, accountId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new Exception("Account not found");
                        balance = rs.getDouble("balance");
                    }
                }

                if (balance < amount) throw new Exception("Insufficient funds. Current balance: " + balance);

                double newBalance = balance - amount;
                accountDAO.updateBalance(conn, accountId, newBalance);
                txDAO.record(conn, accountId, "withdraw", amount, desc == null ? "Withdraw" : desc);

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    // Transfer (from -> to)
    public void transfer(int fromAccountId, int toAccountId, double amount) throws Exception {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");
        if (fromAccountId == toAccountId) throw new IllegalArgumentException("Same account");

        try (Connection conn = DBConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);

                int first = Math.min(fromAccountId, toAccountId);
                int second = Math.max(fromAccountId, toAccountId);

                String sel = "SELECT id, balance FROM account WHERE id IN (?, ?) FOR UPDATE"; // ✅ fixed
                double balanceFrom = -1, balanceTo = -1;
                try (PreparedStatement ps = conn.prepareStatement(sel)) {
                    ps.setInt(1, first);
                    ps.setInt(2, second);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            double b = rs.getDouble("balance");
                            if (id == fromAccountId) balanceFrom = b;
                            if (id == toAccountId) balanceTo = b;
                        }
                    }
                }

                if (balanceFrom < 0) throw new Exception("From account not found");
                if (balanceTo < 0) throw new Exception("To account not found");
                if (balanceFrom < amount) throw new Exception("Insufficient funds in source account");

                accountDAO.updateBalance(conn, fromAccountId, balanceFrom - amount);
                accountDAO.updateBalance(conn, toAccountId, balanceTo + amount);

                txDAO.record(conn, fromAccountId, "transfer-out", amount, "Transfer to acct " + toAccountId);
                txDAO.record(conn, toAccountId, "transfer-in", amount, "Transfer from acct " + fromAccountId);

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    // Fetch transactions for account
    public List<model.Transaction> getTransactions(int accountId) throws Exception {
        return txDAO.listForAccount(accountId);
    }
}
