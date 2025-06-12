package org.istad.model.ropository;

import org.istad.model.entities.Category;
import org.istad.model.entities.Product;
import org.istad.utils.DatabaseConnectionConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductRepository implements Repository<Product, Integer> {
    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public Product findById(Integer id) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    public List<Product> findProductByName(String name){
        String sql = """
                SELECT * FROM products
                WHERE product_name ILIKE ?
                """;
        try(Connection conn = DatabaseConnectionConfig.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name+'%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setUuid(rs.getString("uuid"));
                product.setProductName(rs.getString("product_name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setCategory(Category.);
            }
        }catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage());
        }

        return null;
    }
}
