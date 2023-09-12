package com.techelevator.View;

import java.util.Scanner;

public class Menu {
    public void displayOptions() {
        System.out.println("\n(1) Display Vending Machine Items");
        System.out.println("\n(2) Purchase");
        System.out.println("\n(3) Exit");
    }

    public void purchaseMenu(double currentBalance) {

        System.out.printf("\nCurrent Money Provided: $%.2f", currentBalance);
//        System.out.println("\nCurrent Money Provided: " + currentBalance);
        System.out.println("\n(1) Feed Money");
        System.out.println("(2) Select Product");
        System.out.println("(3) Finish Transaction");

//        Scanner userInput = new Scanner(System.in);
//        String choice = userInput.nextLine().toLowerCase();

    }

}
