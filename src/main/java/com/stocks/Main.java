package com.stocks;

import com.stocks.dao.StockRepo;
import com.stocks.service.FReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        FReader reader = new FReader();

        StockRepo.createNewDatabase();

        if(args.length > 0) {
            List<String> directories = new ArrayList<>();
            List<String> files = new ArrayList<>();

            for (String arg : args) {

                if(new File(args[0]).isDirectory()) {

                    directories.add(arg);

                } else {
                    // TODO check if file suit to be read and after read
                    if(arg.endsWith("txt")) {
                        files.add(arg);
                    }
                }

                if(!directories.isEmpty()) {
                    reader.readDirectories(directories);
                }

                if(!files.isEmpty()) {
                    reader.readFiles(files);
                }
            }
        }


    }
}
