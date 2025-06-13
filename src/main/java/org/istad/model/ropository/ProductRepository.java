package org.istad.model.ropository;

import org.istad.model.entities.Product;
import org.istad.model.service.impl.ProductServiceImpl;
import org.istad.utils.DatabaseConnectionConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Repository<Product, Integer> {
    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setUuid(rs.getString("uuid"));
                product.setProductName(rs.getString("product_name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setIsDeleted(rs.getBoolean("is_deleted"));
                product.setCategory(rs.getString("category"));
                products.add(product);
            }
            return products;
        }catch (Exception e){
            System.out.println("Error during find all product: " + e.getMessage());
        }
        return List.of();
    }

    @Override
    public Product findById(Integer id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setUuid(rs.getString("uuid"));
                product.setProductName(rs.getString("product_name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setIsDeleted(rs.getBoolean("is_deleted"));
                product.setCategory(rs.getString("category"));
                return product;
            }
        }catch (Exception e){
            System.out.println("Error during findById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Product save(Product product) {
        String sql = """
                INSERT INTO products (uuid, product_name, price, quantity, is_deleted, category) VALUES (?, ?, ?, ?, ?,?)
                """;
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, product.getUuid());
            ps.setString(2, product.getProductName());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setBoolean(5, product.getIsDeleted());
            ps.setString(6,product.getCategory());
            int rowAffected =  ps.executeUpdate();
            return rowAffected > 0 ? product : null;
        }catch (Exception e){
            System.out.println("Error during creation of product: " + e.getMessage());
        }
        return null;
    }

    // find product by uuid
    public Product findByUuid(String uuid) {
        String sql = "SELECT * FROM products WHERE uuid = ?";
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setUuid(rs.getString("uuid"));
                product.setProductName(rs.getString("product_name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setIsDeleted(rs.getBoolean("is_deleted"));
                product.setCategory(rs.getString("category"));
                return product;
            }
        }catch (Exception e){
            System.out.println("Error during findByUuid: " + e.getMessage());
        }
        return null;
    }

    // search product by name
    public List<Product> searchProductByName(String name){
        String sql = "SELECT * FROM products WHERE product_name ILIKE ?";
        List<Product> products = new ArrayList<>();
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setUuid(rs.getString("uuid"));
                product.setProductName(rs.getString("product_name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setIsDeleted(rs.getBoolean("is_deleted"));
                product.setCategory(rs.getString("category"));
                products.add(product);
            }
            return products;
        }catch (Exception e){
            System.out.println("Error during search product by name: " + e.getMessage());
        }
        return List.of();
    }

}
