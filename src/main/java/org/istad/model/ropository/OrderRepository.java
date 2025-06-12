package org.istad.model.ropository;

import org.istad.model.entities.Order;
import org.istad.utils.DatabaseConnectionConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class OrderRepository implements Repository<Order, Integer> {

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public Order findById(Integer id) {
        return null;
    }

    @Override
    public Order save(Order order) {
        String sql = """
                INSERT INTO orders (user_id, product_id, order_date) VALUES (?, ?, ?)
                """;
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, order.getUser_id());
            ps.setInt(2, order.getProduct_id());
            ps.setDate(3, order.getOrder_date());
            int rowAffected =  ps.executeUpdate();
            if(rowAffected>0){
                System.out.println("Order added successfully");
            }
            return rowAffected > 0 ? order : null;
        }catch (Exception e){
            System.out.println("Error during creation of order: " + e.getMessage());
        }
        return null;
    }
}
