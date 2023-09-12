package com.techelevator.Controller;

import com.techelevator.Model.LogItem;
import com.techelevator.Model.VendingItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendingMainController {


    Map<String, VendingItem> inventory = new HashMap<>();


    public Map<String, VendingItem> loadVendingItems() {
//  open main.csv
//  read all lines
//  Parse the lines into individual values
//  split lines into values
//  throw values into constructor
//  add to List
//  return List

        File inputFile = new File("main.csv");
//      List<VendingItem> stock = new ArrayList<>();
//      delete stock List if not needed

        try {
            Scanner input = new Scanner(inputFile);

            while(input.hasNextLine()) {
                String nextLine = input.nextLine();
                String[] itemStr = nextLine.split(",");
//          mapKey, name, cost, type
                String mapKey = itemStr[0];
                String name = itemStr[1];
                double cost = Double.parseDouble(itemStr[2]);
                String type = itemStr[3];
//          Revalue/Parse array items into properties (mapKey, name, cost, etc)
//          Throw values into constructor
//          inventory.put(mapKey,item)
                VendingItem item = new VendingItem(mapKey, name, cost, 5, type);
                inventory.put(mapKey, item);
//                System.out.println(inventory.get(mapKey));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return inventory;
    }

    public void displayInventory(Map<String, VendingItem> inventory) {
        System.out.println(inventory.get("A1"));
        System.out.println(inventory.get("A2"));
        System.out.println(inventory.get("A3"));
        System.out.println(inventory.get("A4"));
        System.out.println(inventory.get("B1"));
        System.out.println(inventory.get("B2"));
        System.out.println(inventory.get("B3"));
        System.out.println(inventory.get("B4"));
        System.out.println(inventory.get("C1"));
        System.out.println(inventory.get("C2"));
        System.out.println(inventory.get("C3"));
        System.out.println(inventory.get("C4"));
        System.out.println(inventory.get("D1"));
        System.out.println(inventory.get("D2"));
        System.out.println(inventory.get("D3"));
        System.out.println(inventory.get("D4"));
    }

    public double enterMoney(double currentBalance, double money) {
        System.out.println("\nBalance has been updated");
        currentBalance += money;
        return currentBalance;
    }

    public String getDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");

        return now.format(formatter);
    }

    public double feedMoney(double currentBalance, double money) {
        double moneyRemainder = Math.round((money % 0.05) * 100.0) / 100.0;

        if(moneyRemainder % 0.05 == 0) {
            currentBalance = enterMoney(currentBalance, money);
        } else {
            System.out.println("This machine does not accept pennies.  Redirecting to purchase menu.");
        }
        return currentBalance;
    }

    public static String getMessage(VendingItem currentItem) {
        String message = "";
        String type = currentItem.getType();
        if (type.equals("Munchy")) {
            message = "Crunch Crunch, Yum!";
        } else if (type.equals("Candy")) {
            message = "Yummy Yummy, So Sweet!";
        } else if (type.equals("Drink")) {
            message = "Glug Glug, Yum!";
        } else if (type.equals("Gum")) {
            message = "Chew Chew, Yum!";
        } else {
            message = "Enjoy!";
        }
        return message;
    }

    public static double selectProduct(Map<String, VendingItem> inventory,
                                       double currentBalance,
                                       String slotNumberChoice,
                                       int counter) {

//      Add logic to check that slotNumberChoice is valid

        VendingItem currentItem = inventory.get(slotNumberChoice);

        int quantity = currentItem.getQuantity();
        double cost = currentItem.getCost();
        double costRounded = Math.round(cost * 100.0) / 100.0;
        if (counter % 2 == 0) {
            double discount = 1.00;
            cost = cost - discount;
            costRounded = Math.round(cost * 100.0) / 100.0;
        }

        String message = getMessage(currentItem);

        if (quantity > 0 && currentBalance >= cost) {
            currentItem.setQuantity(quantity - 1);

            currentBalance -= cost;
            currentBalance = Math.round(currentBalance * 100.0) / 100.0;

            inventory.put(slotNumberChoice, currentItem);

            System.out.println(inventory.get(slotNumberChoice));
            System.out.println(message);
        }
        return currentBalance;
    }

    public double finishTransaction(double currentBalance) {
        double initialBalance = currentBalance;

        System.out.println("Your change is $" + currentBalance);
        //return change in quarters nickels and dimes
        int quarters = (int)(currentBalance / 0.25);
        currentBalance = currentBalance % 0.25;
        currentBalance = Math.round(currentBalance * 100.0) / 100.0;

        int dimes = (int)(currentBalance / 0.10);
        currentBalance = currentBalance % 0.10;
        currentBalance = Math.round(currentBalance * 100.0) / 100.0;

        int nickels = (int)(currentBalance / 0.05);
        currentBalance = currentBalance % 0.05;
        currentBalance = Math.round(currentBalance * 100.0) / 100.0;

        System.out.println("Machine has dispensed " + quarters + " quarters, " + dimes + " dimes, and " + nickels + " nickels.");
        return currentBalance;
    }

    public LogItem createLogItem(String slotNumberChoice,
                                 int counter,
                                 double currentBalance) {
        VendingItem currentItem = inventory.get(slotNumberChoice);
        String dateTime = getDateTime();
        double cost = currentItem.getCost();
        double costRounded = Math.round(cost * 100.0) / 100.0;

        if (counter % 2 == 0) {
            double discount = 1.00;
            cost = cost - discount;
            costRounded = Math.round(cost * 100.0) / 100.0;
        }

        String nameKey = currentItem.getName() + " " + currentItem.getMapKey();
        LogItem logItem = new LogItem(dateTime, nameKey, costRounded, currentBalance);


        return logItem;
    }

    public LogItem createFeedLog(double money, double currentBalance) {
        String dateTime = getDateTime();
        LogItem logItem = new LogItem(dateTime,"FEED MONEY:", money, currentBalance);

        return logItem;
    }

    public LogItem createChangeLog(double initialBalance, double currentBalance) {
        String dateTime = getDateTime();
        LogItem logItem = new LogItem(dateTime,"GIVE CHANGE:", initialBalance, currentBalance);

        return logItem;
    }

    public void writeLogFile(List<LogItem> logHistory) {
        File outputFile = new File("log.txt");
        try(PrintWriter output = new PrintWriter(outputFile)) {
            for (LogItem item: logHistory) {
                String dateTime = item.getDateTime();
                String name = item.getName();
                double cost = Math.round(item.getCost() * 100.0) / 100.0;
                double newBalance = Math.round(item.getNewBalance() * 100.0) / 100.0;

                String writeToFile = dateTime + " " +
                        name + " $" +
                        String.format("%.2f", cost) + " $" +
                        String.format("%.2f", newBalance);
                output.println(writeToFile);
                System.out.println(writeToFile);

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
