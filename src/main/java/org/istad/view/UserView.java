package org.istad.view;

import org.istad.controller.UserController;
import org.istad.model.dto.UserResponDto;

import java.io.File;
import java.util.Scanner;

public class UserView {
    final UserController userController = new UserController();
    public void profile(){
        String[] emailPassword = userController.getEmailAndPasswordFromFile();
        if(emailPassword[0].equals("null") && emailPassword[1].equals("null")){
            System.out.println("======= Your Account =======");
            System.out.println("Username : null");
            System.out.println("Email : null");
            System.out.println("=============================");
            System.out.println("""
                1. Login
                2. Logout
                3. Exit
                """);
        }
        else{
            UserResponDto user = userController.getUserByEmail(emailPassword[0]);
            System.out.println("======= Your Account =======");
            System.out.println("Username : " + user.username());
            System.out.println("Email : " + emailPassword[0]);
            System.out.println("=============================");
            System.out.println("""
                1. Login
                2. Logout
                3. Exist
                """);
        }
        System.out.println("Enter your choice : ");
        Integer choice = new Scanner(System.in).nextInt();
        switch(choice){
            case 1:{
                System.out.println("Enter your email : ");
                String email = new Scanner(System.in).nextLine();
                System.out.println("Enter your password : ");
                String password = new Scanner(System.in).nextLine();
                if ( userController.login(email, password)){
                    System.out.println("You have logged in successfully");
                }
                break;
            }
            case 2:{
                if(userController.logout()){
                    System.out.println("You have logged out successfully");
                }
                break;
            }
            case 3:{
                System.exit(0);
            }
        }
    }
}
