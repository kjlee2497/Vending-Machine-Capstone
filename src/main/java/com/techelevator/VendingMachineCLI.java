package com.techelevator;

// Menu is provided to you as a suggested class to handle user input
// Build out a menu class to start

import com.techelevator.Controller.VendingMainController;
import com.techelevator.Model.LogItem;
import com.techelevator.Model.VendingItem;
import com.techelevator.View.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE };
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

	private Menu menu;
	private VendingMainController vendingMainController = new VendingMainController();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public static void main(String[] args) {
		// You will need to create a Menu class to handle display.
		//Menu menu = new Menu();
		Menu menu = new Menu();
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

	public void run() {

		Map<String, VendingItem> inventory = vendingMainController.loadVendingItems();
		List<LogItem> logHistory = new ArrayList<>();
		boolean purchasing = false;

		double currentBalance = 0.00;
		int counter = 1;
//		int discountPurchases = 0;
//		int fullPricePurchases = 0;

		while (true) {
			// Use a method from your Menu class to initialize this value

//			Use currentBalance as a parameter for
//			future methods so that we can access it within this scope
//		i.e.
//			vendingMainController.loadVendingItems();

//			Using purchasing as a boolean to control which menu shows up when the loop resets
//			When you select purchasing, it sets "purchasing" to true
//			Until it's set to false in "Finish Transaction", it will keep the user on the purchase menu
			Scanner userInput = new Scanner(System.in);
			String choice = "";

			if(purchasing) {
				choice = "2";
			} else {
				menu.displayOptions();
				System.out.println("\nPlease type in your choice");
				choice = userInput.nextLine().toLowerCase();
			}

//							***********************  DISPLAY ***********************

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS.toLowerCase()) || choice.equals("1")) {
				vendingMainController.displayInventory(inventory);
			}

//							***********************  PURCHASE ***********************
			else if (choice.equals(MAIN_MENU_OPTION_PURCHASE.toLowerCase()) || choice.equals("2")) {
				purchasing = true;
				menu.purchaseMenu(currentBalance);
				String purchaseOption = userInput.nextLine();

//							=======================  FEED MONEY =======================
				if(purchaseOption.equals(PURCHASE_MENU_OPTION_FEED_MONEY) || purchaseOption.equals("1")){
					System.out.println("\nHow much money are you entering?");
					String moneyStr = userInput.nextLine();
					double money = Double.parseDouble(moneyStr);

					currentBalance = vendingMainController.feedMoney(currentBalance, money);
					LogItem logItem = vendingMainController.createFeedLog(money, currentBalance);
					logHistory.add(logItem);
				}

//							=======================  SELECT PRODUCT =======================
				else if(purchaseOption.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT) || purchaseOption.equals("2")){
					vendingMainController.displayInventory(inventory);

					System.out.println("Please enter the slot number:  ");
					String slotNumberChoice = userInput.nextLine().toUpperCase();
					currentBalance = VendingMainController.selectProduct(inventory, currentBalance, slotNumberChoice, counter);

					LogItem logItem = vendingMainController.createLogItem(slotNumberChoice,
																			counter,
																			currentBalance);
					logHistory.add(logItem);
					counter++;
				}


//							=======================  FINISH TRANSACTION =======================
				else if(purchaseOption.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION) || purchaseOption.equals("3")){
					double initialBalance = currentBalance;
					currentBalance = vendingMainController.finishTransaction(currentBalance);

					LogItem logItem = vendingMainController.createChangeLog(initialBalance, currentBalance);
					logHistory.add(logItem);

					purchasing = false;
				}

				else {
					System.out.println("Not a valid response.  Returning to main menu...");
				}
			}

//							***********************  EXIT/ELSE ***********************

			else {
				vendingMainController.writeLogFile(logHistory);

				System.out.println("Closing program...");
				break;
			}
		}
	}
}

//