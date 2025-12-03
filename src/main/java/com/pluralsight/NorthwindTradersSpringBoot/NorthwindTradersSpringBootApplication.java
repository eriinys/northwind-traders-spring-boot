package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class NorthwindTradersSpringBootApplication {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(NorthwindTradersSpringBootApplication.class, args);
        ProductDao dao = context.getBean(ProductDao.class);

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
