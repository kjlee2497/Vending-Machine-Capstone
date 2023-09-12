package com.techelevator.Model;

public class LogItem {
//    TODO:  Change date and time to LocalDate & LocalTime
//    TODO:  add mapKey
    private String dateTime;
    private String name;
    private double cost;
    private double newBalance;


    public LogItem(String dateTime, String name, double cost, double newBalance) {
        this.dateTime = dateTime;
        this.name = name;
        this.cost = cost;
        this.newBalance = newBalance;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    @Override
    public String toString() {
        return dateTime + " " +
                name + " " +
                String.format("%.2f", cost) + " " +
                String.format("%.2f", newBalance);
    }
}


