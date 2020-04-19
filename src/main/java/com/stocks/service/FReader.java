package com.stocks.service;

import com.stocks.dao.StockRepo;
import com.stocks.model.StockSell;
import com.stocks.model.StockUpdate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FReader {

    public boolean readFiles(List<String> files) {

        try {

            for (String file : files) {

                List<String> strings = Files.readAllLines(new File(file).toPath());

                for (String string : strings) {
                    switch (string.toCharArray()[0]) {

                        case 'u': {
                            updateSave(string);
                            break;
                        }
                        case 'q': {

                            break;
                        }
                        case 'o': {
                            updateDelete(string);
                            break;
                        }

                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean readDirectories(List<String> directories) {

        return false;
    }

    public boolean getStocks(String row) {

        String[] split = row.split(",");

        if(split.length == 2) {

            if(split[1].equals("best_bid")) {

            }

            if(split[1].equals("best_ask")) {

            }

        }

        if(split.length == 3) {

        }

        return true;
    }

    public boolean updateSave(String row) {
        StockRepo repo = StockRepo.getInstance();

        StockUpdate update = new StockUpdate(row);

        repo.save(update);

        return true;
    }

    public boolean updateDelete(String row) {
        StockRepo repo = StockRepo.getInstance();

        StockSell sell = new StockSell(row);

        if(sell.getType().equals("buy")) {
            repo.removeMinPrice(sell);
        }

        if(sell.getType().equals("sell")) {
            repo.removeMaxPrice(sell);
        }

        return true;
    }

}
