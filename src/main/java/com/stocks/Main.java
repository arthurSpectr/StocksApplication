package com.stocks;

import com.stocks.dao.StockRepo;
import com.stocks.service.FReader;

public class Main {

    public static void main(String[] args) {
        StockRepo repo = StockRepo.getInstance();
        repo.createNewTable();

        FReader reader = new FReader();

        if (args.length > 0) {

            reader.parseFile(args[0]);

        }


    }
}
