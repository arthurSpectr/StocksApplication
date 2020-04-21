package com.stocks.model;

public class StockSell {

    private int quantity;
    private int grade;

    public StockSell(String row) {
        String[] split = row.split(",");

        grade = split[1].equals("buy") ? 1 : 0;
        quantity = Integer.parseInt(split[2]);

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
