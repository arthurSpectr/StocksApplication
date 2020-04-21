package com.stocks;

import com.stocks.dao.StockRepo;
import com.stocks.service.StockService;

public class Main {

    public static void main(String[] args) {
        StockRepo repo = StockRepo.getInstance();
        repo.createNewTable();

        StockService reader = new StockService();

        if (args.length > 0) {

            reader.parseFile(args[0]);

        }


    }
}
