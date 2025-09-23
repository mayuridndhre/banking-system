package dao;

import model.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionDAO {

    // Record transaction
    public void record(Connection conn, int accountId, String type, double amount, String description) throws Exception {
        String sql = "INSERT INTO transaction_record (account_id, type, amount, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.setString(4, description);
            ps.executeUpdate();
        }
    }

    // List transactions for account
    public List<Transaction> listForAccount(int accountId) throws Exception {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT id, account_id, type, amount, timestamp, description FROM transaction_record WHERE account_id = ? ORDER BY timestamp DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction tx = new Transaction(
                            rs.getInt("account_id"),
                            rs.getString("type"),
                            rs.getDouble("amount"),
                            new Date(rs.getTimestamp("timestamp").getTime()),
                            rs.getString("description")
                    );
                    tx.setId(rs.getInt("id"));
                    list.add(tx);
                }
            }
        }
        return list;
    }
}
