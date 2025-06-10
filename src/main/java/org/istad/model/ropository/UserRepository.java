package org.istad.model.ropository;

import org.istad.model.entities.User;
import org.istad.utils.DatabaseConnectionConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User, Integer>{

    // Get all user
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUuid(rs.getString("uuid"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setIsDeleted(rs.getBoolean("is_deleted"));
                users.add(user);
            }
            System.out.println("Users found");
            return users;
        }catch (Exception e){
            System.out.println("Error during findAll: " + e.getMessage());
        }
        return List.of();
    }

    // Get user by id
    @Override
    public User findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUuid(rs.getString("uuid"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setIsDeleted(rs.getBoolean("is_deleted"));
                return user;
            }
        }catch (Exception e){
            System.out.println("Error during findById: " + e.getMessage());
        }
        return null;
    }

    // Create user
    @Override
    public User save(User user) {
        String spl = """
                INSERT INTO users (uuid, username, email, password, is_deleted) VALUES (?, ?, ?, ?, ?)
                """;
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(spl);
            ps.setString(1, user.getUuid());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setBoolean(5, user.getIsDeleted());
            int rowAffected =  ps.executeUpdate();
            if(rowAffected>0){
                System.out.println("User created successfully");
            }
            return rowAffected > 0 ? user : null;
        }catch (Exception e){
            System.out.println("Error during creation of user: " + e.getMessage());
        }
        return null;
    }

    // Get user by email
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUuid(rs.getString("uuid"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setIsDeleted(rs.getBoolean("is_deleted"));
                return user;
            }
        }catch (Exception e){
            System.out.println("Error during get user by email: " + e.getMessage());
        }
        return null;
    }
    // Get user by password
    public User getUserByPassword(String password) {
        String sql = "SELECT * FROM users WHERE password = ?";
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUuid(rs.getString("uuid"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setIsDeleted(rs.getBoolean("is_deleted"));
                return user;
            }
        }catch (Exception e){
            System.out.println("Error during get user by password: " + e.getMessage());
        }
        return null;
    }
}
