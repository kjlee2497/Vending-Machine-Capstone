package com.techelevator.Model;

public class VendingItem {
    private String mapKey;
    private String name;
    private double cost;
    private int quantity = 5;
    private String type;

    public VendingItem(String mapKey, String name, double cost, int quantity, String type) {
        this.mapKey = mapKey;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;
    }

    public VendingItem() {
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if(quantity == 0) {
            return "\n" + mapKey +
                    "\nName: " + name +
                    "  |  Quantity: " + "SOLD OUT" +
                    "  |  Type: " + type +
                    "  |  Cost: " + cost;
        } else {
            return mapKey +
                    "\nName: " + name +
                    "  |  Quantity: " + quantity +
                    "  |  Type: " + type +
                    "  |  Cost: " + cost;
        }
    }
}
