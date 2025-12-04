package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class NorthwindApplication implements CommandLineRunner {
    @Autowired
    private ProductDao dao;

    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        boolean in = true;

        while(in){
            System.out.println("""
                    Choose from following options:
                    1) List Products
                    2) Add Product
                    0) Exit
                    """);
            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice){
                case 1 -> {
                    for(Product p : dao.getAll()){
                        System.out.println(p.toString());
                    }
                }
                case 2 -> {
                    System.out.println("Enter the product id:\n");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter the product name:\n");
                    String name = scanner.nextLine();
                    System.out.println("Enter the product category:\n");
                    String category = scanner.nextLine();
                    System.out.println("Enter the product price:\n");
                    double price = Double.parseDouble(scanner.nextLine());

                    dao.add(new Product(id, name, category, price));
                    System.out.println("Product successfully added!");
                }
                case 0 ->{
                    System.out.println("Exiting program...Goodbye!");
                    in = false;
                }
            }
        }
    }
}
