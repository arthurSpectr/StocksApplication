package com.stocks.dao;

import com.stocks.model.StockSell;
import com.stocks.model.StockUpdate;

import java.sql.*;

public final class StockRepo {

    private static final StockRepo INSTANCE = new StockRepo();
    private static final String URL = "jdbc:sqlite:./database.db";
    private static Connection connection;

    private StockRepo() {
    }

    public static StockRepo getInstance() {
        return INSTANCE;
    }

    public static Connection getConnection() {

        if(connection == null) {
            try {
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return connection;
    }

    public void createNewTable() {

        String sql = "CREATE TABLE IF NOT EXISTS stock (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	price INT NOT NULL,\n"
                + "	quantity INT real,\n"
                + "	grade INT real,\n"
                + " unique(price)\n"
                + ");";

        try (Statement stmt = getConnection().createStatement()) {

            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(StockUpdate update) {
        String insertQuery = "INSERT INTO stock (price, quantity, grade) VALUES (?, ?, ?) ON CONFLICT(price) DO UPDATE SET quantity = ?;";

        try (PreparedStatement insertStmt = getConnection().prepareStatement(insertQuery)) {

            insertStmt.setInt(1, update.getPrice());
            insertStmt.setInt(2, update.getQuantity());
            insertStmt.setInt(3, update.getGrade());
            insertStmt.setInt(4, update.getQuantity());

            insertStmt.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeMinPrice(StockSell sell) {
        String updateQuery = "UPDATE stock SET quantity = quantity-? WHERE price=(select min(price) as price from stock where grade=0)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(updateQuery)) {

            pstmt.setInt(1, sell.getQuantity());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeMaxPrice(StockSell sell) {
        String updateQuery = "UPDATE stock SET quantity = quantity-? WHERE price=(select max(price) as price from stock where grade=1)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(updateQuery)) {

            pstmt.setInt(1, sell.getQuantity());

            pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public StockUpdate getBestAskStocks() {
        String sql = "SELECT min(price) as price, quantity FROM stock WHERE grade=0";
        StockUpdate update = new StockUpdate();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            update.setPrice(rs.getInt("price"));
            update.setQuantity(rs.getInt("quantity"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public StockUpdate getBestBidStocks() {
        String sql = "SELECT max(price) as price, quantity FROM stock WHERE grade=1";
        StockUpdate update = new StockUpdate();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            update.setPrice(rs.getInt("price"));
            update.setQuantity(rs.getInt("quantity"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public StockUpdate getStockByPrice(int price) {

        String sql = "SELECT grade, price, quantity FROM stock WHERE price=?";
        StockUpdate update = new StockUpdate();

        try (PreparedStatement stmt  = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, price);
            ResultSet rs = stmt.executeQuery();

            update.setPrice((rs.getInt("price")));
            update.setQuantity(rs.getInt("quantity"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return update;
    }

}
