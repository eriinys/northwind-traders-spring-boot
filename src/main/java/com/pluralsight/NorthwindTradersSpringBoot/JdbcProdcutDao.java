package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProdcutDao implements ProductDao {
   private DataSource dataSource;

   @Autowired
   public JdbcProdcutDao(DataSource dataSource){
       this.dataSource = dataSource;
   }

    @Override
    public void add(Product product) {

        String checkExistingCategory = "SELECT CategoryID FROM categories " +
                "WHERE CategoryName = ?";

        String addToCategories = "INSERT INTO categories (CategoryName) " +
                "VALUES(?) ";

        String addToProducts = "INSERT INTO products (ProductName, CategoryID, UnitPrice) " +
                "VALUES(?, ?, ?) ";

        int categoryId = 0;

            try(Connection cs = dataSource.getConnection();
                PreparedStatement ps = cs.prepareStatement(checkExistingCategory)){ //checks if category already exists
                ps.setString(1, product.getCategory());
                 try(ResultSet rs = ps.executeQuery()){
                     if(rs.next()){
                         categoryId = rs.getInt("CategoryID");
                         //if category already exists, assign the CategoryID to categoryId
                     } else {
                         //if category does NOT exist, insert a new category using second query
                         try(PreparedStatement ps2 = cs.prepareStatement(addToCategories, PreparedStatement.RETURN_GENERATED_KEYS)){
                             ps2.setString(1, product.getCategory());
                             ps2.executeUpdate();
                             try(ResultSet keys = ps2.getGeneratedKeys()){
                                 if(keys.next()) {
                                     //set the newly auto-generated pk (CategoryID) to categoryId
                                     categoryId = keys.getInt(1);
                                 }
                             }
                         }
                     }
                 }
                 //insert the new product using categoryId provided in the previous queries above
                try(PreparedStatement ps3 = cs.prepareStatement(addToProducts, PreparedStatement.RETURN_GENERATED_KEYS)){
                    ps3.setString(1, product.getName());
                    ps3.setInt(2, categoryId);
                    ps3.setDouble(3, product.getPrice());
                    ps3.executeUpdate();

                    //optional: print out the auto-generated pk (ProductID) of the new product that was added
                    try (ResultSet keys = ps3.getGeneratedKeys()) {
                        if (keys.next()) {
                            int productId = keys.getInt(1);
                            System.out.println("ProductID #" + productId + " successfully added.");
                        }
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT products.ProductID, products.ProductName, products.UnitPrice, categories.CategoryName " +
                "FROM products " +
                "JOIN categories " +
                "ON products.CategoryID = categories.CategoryID";

        try(Connection cs = dataSource.getConnection();
        PreparedStatement ps = cs.prepareStatement(sql)){

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    int id = rs.getInt("ProductID");
                    String name = rs.getString("ProductName");
                    String category = rs.getString("CategoryName");
                    double price = rs.getDouble("UnitPrice");

                    Product p = new Product(id, name, category, price);
                    products.add(p);
                }
                return products;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
