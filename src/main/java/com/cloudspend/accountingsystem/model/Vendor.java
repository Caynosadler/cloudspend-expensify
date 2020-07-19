package com.cloudspend.accountingsystem.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Vendor {

    @Id
    public String id;

    private String name;
    private ArrayList<Expense> expenses;

    public String getName() {
        return name;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public Vendor(String name){
        this.name = name;
        expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense){
        expenses.add(expense);
    }
}
