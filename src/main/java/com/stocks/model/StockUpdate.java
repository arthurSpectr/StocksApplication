package com.stocks.model;

public class StockUpdate {

    private String transaction_type;
    private int price;
    private int quantity;
    private int grade;

    public StockUpdate() {
    }

    public StockUpdate (String row) {

        String[] split = row.split(",");
        transaction_type = split[0];
        price = Integer.parseInt(split[1]);
        quantity = Integer.parseInt(split[2]);
        grade = split[3].equals("bid") ? 1 : 0;

    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
}
