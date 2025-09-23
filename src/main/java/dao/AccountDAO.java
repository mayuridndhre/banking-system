package dao;

import model.Account;
import java.sql.*;

public class AccountDAO {

    // Create account
    public Account createAccount(Connection conn, int customerId, String type, double initialDeposit) throws Exception {
        String sql = "INSERT INTO account (customer_id, type, balance) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, customerId);
            ps.setString(2, type);
            ps.setDouble(3, initialDeposit);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Account a = new Account(customerId, type, initialDeposit);
                    a.setId(rs.getInt(1));
                    return a;
                }
            }
        }
        return null;
    }

    // Get account by ID
    public Account getById(Connection conn, int id) throws Exception {
        String sql = "SELECT id, customer_id, type, balance FROM account WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account a = new Account(
                            rs.getInt("customer_id"),
                            rs.getString("type"),
                            rs.getDouble("balance")
                    );
                    a.setId(rs.getInt("id"));
                    return a;
                }
            }
        }
        return null;
    }

    // Update account balance
    public void updateBalance(Connection conn, int accountId, double newBalance) throws Exception {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        }
    }
}
