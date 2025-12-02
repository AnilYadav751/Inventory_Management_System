package org.example;

import java.sql.*;

public class InventoryService {

    // 1. ADD PRODUCT (Basic Insert)
    public void addProduct(String name, double price, int quantity) {
        String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println(" Product added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. PROCESS SALE (Transaction Management: Update Stock + Log Sale)
    public void processSale(int productId, int quantitySold) {
        String checkStockSql = "SELECT price, quantity FROM products WHERE id = ?";
        String updateStockSql = "UPDATE products SET quantity = quantity - ? WHERE id = ?";
        String logSaleSql = "INSERT INTO sales_log (product_id, quantity_sold, total_amount) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();

            // --- START TRANSACTION ---
            conn.setAutoCommit(false);

            // Step A: Check Availability & Get Price
            double price = 0;
            int currentStock = 0;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkStockSql)) {
                checkStmt.setInt(1, productId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    price = rs.getDouble("price");
                    currentStock = rs.getInt("quantity");
                } else {
                    throw new SQLException("Product ID not found.");
                }
            }

            if (currentStock < quantitySold) {
                throw new SQLException(" Insufficient stock! Available: " + currentStock);
            }

            // Step B: Deduct Stock
            try (PreparedStatement updateStmt = conn.prepareStatement(updateStockSql)) {
                updateStmt.setInt(1, quantitySold);
                updateStmt.setInt(2, productId);
                updateStmt.executeUpdate();
            }

            // Step C: Log Sale
            try (PreparedStatement logStmt = conn.prepareStatement(logSaleSql)) {
                logStmt.setInt(1, productId);
                logStmt.setInt(2, quantitySold);
                logStmt.setDouble(3, price * quantitySold);
                logStmt.executeUpdate();
            }

            // --- COMMIT TRANSACTION ---
            conn.commit();
            System.out.println(" Sale processed successfully! Total: $" + (price * quantitySold));

        } catch (SQLException e) {
            System.out.println(" Transaction Failed: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();              // Rollback changes on error
                System.out.println(" Changes rolled back.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);     // Reset to default
                if (conn != null) conn.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // 3. GENERATE REPORT (Complex Query)
    public void generateLowStockReport(int threshold) {
        String sql = "SELECT id, name, quantity FROM products WHERE quantity <= ?";

        System.out.println("\n--- 📉 LOW STOCK REPORT (Threshold: " + threshold + ") ---");
        System.out.printf("%-5s %-20s %-10s%n", "ID", "Name", "Quantity");
        System.out.println("----------------------------------------");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, threshold);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.printf("%-5d %-20s %-10d%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}