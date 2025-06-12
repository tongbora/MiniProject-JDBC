package org.istad.model.ropository;

import org.istad.utils.DatabaseConnectionConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductCartRepository {
    // add product to cart in database (Table = product_cart)
    public void addProductToCart(Integer userId, Integer productId) {
        String sql = "INSERT INTO product_cart (user_id, product_id) VALUES (?, ?)";
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            int rowAffected =  ps.executeUpdate();
            if(rowAffected>0){
                System.out.println("Add product to Cart successfully");
            }
        }catch (Exception e){
            System.out.println("Error during add product to cart : " + e.getMessage());
        }
    }

    // get all product in cart
    public List<Integer> getAllProductInCart(Integer userId) {
        String sql = "SELECT product_id FROM product_cart WHERE user_id = ?";
        List<Integer> productId = new ArrayList<>();
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                productId.add(rs.getInt("product_id"));
            }
            return productId;
        }catch (Exception e){
            System.out.println("Error during find all product in cart: " + e.getMessage());
        }
        return List.of();
    }

    // delete product in cart
    public void deleteProductFromCart(Integer productId) {
        String sql = "DELETE FROM product_cart WHERE product_id = ?";
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, productId);
            int rowAffected = ps.executeUpdate();
            if(rowAffected>0){
                System.out.println("Delete product from cart successfully");
            }
        }catch (Exception e){
            System.out.println("Error during delete product from cart : " + e.getMessage());
        }
    }
}
