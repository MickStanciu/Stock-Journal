package com.example.web.converter;

public class ShareAggregator {

    private String symbol;
    private double averageBoughtPrice;
    private int quantity;

    public ShareAggregator(String symbol) {
        this.symbol = symbol;
        this.averageBoughtPrice = 0.00;
        this.quantity = 0;
    }

    private ShareAggregator() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getAverageBoughtPrice() {
        return averageBoughtPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantityAndPrice(int quantity, double price) {
        if (averageBoughtPrice == 0.00 && this.quantity == 0) {
            this.quantity = quantity;
            this.averageBoughtPrice = price;
        } else {
            double temporary = this.quantity * this.averageBoughtPrice;
            temporary += quantity * price;
            this.quantity += quantity;
            this.averageBoughtPrice = temporary / this.quantity;
        }
    }


}
