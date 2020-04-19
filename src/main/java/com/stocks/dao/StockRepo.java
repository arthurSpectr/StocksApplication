package com.stocks.dao;

import com.stocks.model.StockSell;
import com.stocks.model.StockUpdate;

import java.io.File;
import java.sql.*;

public final class StockRepo {

    private static final StockRepo repo = new StockRepo();

    private StockRepo() {
    }

    public static StockRepo getInstance() {
        return repo;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/sqlite/db/chinook.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return conn;
    }

    public void createNewDatabase() {

        String url = "jdbc:sqlite:D:/sqlite/db/database";

        if(new File("D:/sqlite/db/database").exists()) return;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/tests.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS stock (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	price INT NOT NULL,\n"
                + "	quantity INT real\n"
                + "	grade INT real\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(StockUpdate update) {
        String sql = "INSERT INTO stock(name,capacity) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, update.getTransaction_type());
            pstmt.setInt(1, update.getPrice());
            pstmt.setInt(2, update.getQuantity());
            pstmt.setInt(2, update.getQuantity());
//            pstmt.setString(4, update.getGrade());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeMinPrice(StockSell sell) {
        String selectQuery = "SELECT id, transaction_type, price, quantity, grade WHERE price=MIN(price) from stock";
        String removeQuery = "DELETE FROM from stock where id = ?";
        String updateQuery = "UPDATE stock SET quantity = ? , "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            int quantity = rs.getInt("quantity");

            if(quantity >= sell.getQuantity()) {
                PreparedStatement pstmt = conn.prepareStatement(removeQuery);

                pstmt.setInt(1, rs.getInt("id"));
                pstmt.executeUpdate();
            } else {
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);

                pstmt.setInt(1, quantity);
                pstmt.setInt(2, rs.getInt("id"));
                pstmt.executeUpdate();
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeMaxPrice(StockSell sell) {
        String selectQuery = "SELECT id WHERE price=Max(price) from stock";
        String removeQuery = "DELETE FROM from stock where id = ?";
        String updateQuery = "UPDATE stock SET quantity = ? , "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            int quantity = rs.getInt("quantity");

            if(quantity >= sell.getQuantity()) {
                PreparedStatement pstmt = conn.prepareStatement(removeQuery);

                pstmt.setInt(1, rs.getInt("id"));
                pstmt.executeUpdate();
            } else {
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);

                pstmt.setInt(1, quantity);
                pstmt.setInt(2, rs.getInt("id"));
                pstmt.executeUpdate();
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectBestStock(String row) {
        String sql = "SELECT transaction_type, price, quantity, grade FROM stock, WHERE ";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet selectStockByPrice(String row){
        int quantity = Integer.parseInt(row.split(",")[2]);
        ResultSet rs = null;

        String sql = "SELECT transaction_type, price, quantity, grade FROM stock, WHERE quantity = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setInt(1, quantity);

            rs = statement.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getDouble("capacity"));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
}
