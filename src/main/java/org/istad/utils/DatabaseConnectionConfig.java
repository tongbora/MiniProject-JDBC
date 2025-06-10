package org.istad.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionConfig {
    private static String databaseUrl = DBConfig.getUrl();
    private static String databaseUser = DBConfig.getUsername();
    private static String databasePassword = DBConfig.getPassword();

    public static Connection getConnection() {
        try{
            return DriverManager.getConnection(databaseUrl,databaseUser,databasePassword);
        }catch(Exception e){
            System.err.println("Error ducing connection: " + e.getMessage());
        }
        return null;
    }
}
