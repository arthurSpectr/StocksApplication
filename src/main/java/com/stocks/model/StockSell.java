package com.stocks.model;

public class StockSell {

    private String transaction_type;
    private int quantity;
    private String grade;

    public StockSell(String row) {
        String[] split = row.split(",");

        transaction_type = split[0];
        quantity = Integer.parseInt(split[1]);
        grade = split[2];
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return transaction_type;
    }

    public void setType(String type) {
        this.transaction_type = type;
    }
}
