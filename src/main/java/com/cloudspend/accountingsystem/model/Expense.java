package com.cloudspend.accountingsystem.model;

public class Expense {
    private String amount;
    private String transactionDate;
    private String category;

    public Expense(String amount, String transactionDate, String category) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
