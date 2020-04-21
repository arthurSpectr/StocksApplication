package com.stocks.service;

import com.stocks.dao.StockRepo;
import com.stocks.model.StockSell;
import com.stocks.model.StockUpdate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;

public class StockService {

    BufferedWriter bufferedWriter;

    public void parseFile(String file) {

        try (Connection conn = StockRepo.getConnection();
                BufferedWriter fw = getFileWriter()) {

            for (String row : Files.readAllLines(new File(file).toPath())) {
                switch (row.toCharArray()[0]) {
                    case 'u': {
                        update(row);
                        break;
                    }
                    case 'q': {
                        query(row);
                        break;
                    }
                    case 'o': {
                        order(row);
                        break;
                    }
                }
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(String row) {
        StockRepo repo = StockRepo.getInstance();

        StockUpdate update = new StockUpdate(row);

        repo.save(update);
    }

    public boolean order(String row) {
        StockRepo repo = StockRepo.getInstance();

        StockSell sell = new StockSell(row);

        if(sell.getGrade() == 1) {
            repo.removeMinPrice(sell);
        }

        if(sell.getGrade() == 0) {
            repo.removeMaxPrice(sell);
        }

        return true;
    }

    public void query(String row) throws IOException {
        StockRepo repo = StockRepo.getInstance();

        String[] split = row.split(",");

        if(split[1].equals("best_bid")) {
            StockUpdate bestBidStocks = repo.getBestBidStocks();
            System.out.println("best stock for buy " + bestBidStocks.getPrice() + ", " + bestBidStocks.getQuantity());
            getFileWriter().write(bestBidStocks.getPrice() + ", " + bestBidStocks.getQuantity() + "\n");
        }

        if(split[1].equals("best_ask")) {
            StockUpdate bestAskStocks = repo.getBestAskStocks();
            System.out.println("best stock for sell " + bestAskStocks.getPrice() + ", " + bestAskStocks.getQuantity());
            getFileWriter().write(bestAskStocks.getPrice() + ", " + bestAskStocks.getQuantity() + "\n");
        }

        if(split[1].equals("size")) {
            StockUpdate stockByPrice = repo.getStockByPrice(Integer.parseInt(split[2]));
            System.out.println("size of stock with price " + stockByPrice.getPrice() +" has size " + stockByPrice.getQuantity());
            getFileWriter().write(stockByPrice.getQuantity() + "\n");
        }

    }

    public BufferedWriter getFileWriter() {

        try {
            if (bufferedWriter == null) {
                bufferedWriter = new BufferedWriter(new FileWriter("./StocksApplication.log", true));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedWriter;
    }

}
