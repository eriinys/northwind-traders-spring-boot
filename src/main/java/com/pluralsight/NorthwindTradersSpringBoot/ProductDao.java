package com.pluralsight.NorthwindTradersSpringBoot;
import java.util.List;

public interface ProductDao {
    default void add(Product product){}
    List<Product> getAll();
}
