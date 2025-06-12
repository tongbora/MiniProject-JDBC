package org.istad.view;

import org.istad.controller.UserController;
import org.istad.model.dto.UserCreateDto;

import java.io.File;
import java.util.Scanner;

public class UI {
    Scanner scanner = new Scanner(System.in);
    UserController userController = new UserController();
    UserView userView = new UserView();
    ProductView productView = new ProductView();
    File file = new File("user.txt");
    public void ui(){
       while(true){
           if(!file.exists()){
               System.out.println("""
                --- Welcome to GROUP 2 STORE ---
                      Please Sign up first
                """);
               System.out.print("Enter your username: ");
               String username = scanner.nextLine();
               System.out.print("Enter your email: ");
               String email = scanner.nextLine();
               System.out.print("Enter your password: ");
               String password = scanner.nextLine();
               userController.register(new UserCreateDto(username,email,password));
               System.out.println("Thank you for registering your new user");
           }
           else{
               System.out.println("--- Welcome to GROUP 2 STORE ---");
               userController.autoLogin();
               System.out.println("""
                   1. View Account
                   2. View Store
                   3. Exit Program
                   """);
               System.out.print("Enter your choice: ");
               int choice = scanner.nextInt();
               switch(choice){
                   case 1: {
                       userView.profile();
                       break;
                   }
                   case 2: {
                       productView.productView();
                       break;
                   }
                   case 3: {
                       System.exit(0);
                   }
               }
           }
       }
    }
}
