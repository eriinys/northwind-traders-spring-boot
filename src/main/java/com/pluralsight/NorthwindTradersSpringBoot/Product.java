package com.pluralsight.NorthwindTradersSpringBoot;

public class Product {
    private int productId;
    private String name;
    private String category;
    private double price;

    public Product(int productId, String name, String category, double price) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString(){
        return String.format("ID: %d, Name: %s, Category: %s, Price $%.2f", productId,name, category, price);
    }
}
