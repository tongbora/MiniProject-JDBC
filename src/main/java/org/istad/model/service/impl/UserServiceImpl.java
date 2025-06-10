package org.istad.model.service.impl;

import org.istad.mapper.UserMapper;
import org.istad.model.dto.UserCreateDto;
import org.istad.model.dto.UserResponDto;
import org.istad.model.entities.User;
import org.istad.model.ropository.UserRepository;
import org.istad.model.service.UserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Scanner;

public class UserServiceImpl implements UserService {
    Scanner sc = new Scanner(System.in);
    private final UserRepository userRepository = new UserRepository();

    @Override
    public UserResponDto register(UserCreateDto user) {
        try(FileOutputStream file = new FileOutputStream("user.txt")) {
            file.write((user.email() + "\n").getBytes());
            file.write((user.password() + "\n").getBytes());
        }catch(Exception e){
            System.out.println("Error during login: " + e.getMessage());
        }
        return UserMapper.userToUserResponDto(userRepository.save(UserMapper.userCreateDtoToUser(user)));
    }

    @Override
    public UserResponDto getUserByUuid(String uuid) {
       User user = userRepository.findAll().stream()
                .filter(u -> u.getUuid().equals(uuid))
                .findFirst()
               .get();
        return UserMapper.userToUserResponDto(userRepository.findById(user.getId()));
    }

    @Override
    public UserResponDto getUserByEmail(String email) {
        return UserMapper.userToUserResponDto(userRepository.getUserByEmail(email));
    }


    // Login
    public boolean login(String email, String password) {
        try{
            if(userRepository.getUserByEmail(email) != null && userRepository.getUserByPassword(password) != null){
                // set email and password to file again
                try(FileOutputStream file = new FileOutputStream("user.txt")) {
                    file.write((email + "\n").getBytes());
                    file.write((password + "\n").getBytes());
                }
                return true;
            } else if (userRepository.getUserByEmail(email) == null) {
                System.out.println("Email not found");
                return false;
            }else if (userRepository.getUserByPassword(password) == null) {
                System.out.println("Password not found");
                return false;
            }
        }catch(Exception e){
            System.out.println("Error during login: " + e.getMessage());
        }
        return false;
    }


    // Logout
    public boolean logout() {
        try(FileOutputStream file = new FileOutputStream("user.txt")){
            file.write(("null" + "\n").getBytes());
            file.write(("null" + "\n").getBytes());
        }catch(Exception e){
            System.out.println("Error during login: " + e.getMessage());
        }
        return true;
    }

    // auto login
    public void autoLogin() {
        File file = new File("user.txt");
        if(file.exists()){
            String[] emailAndPassword = getEmailAndPasswordFromFile();
            if(!emailAndPassword[0].equals("null") && !emailAndPassword[1].equals("null")){
                login(emailAndPassword[0], emailAndPassword[1]);
                User u = userRepository.getUserByEmail(emailAndPassword[0]);
                System.out.println("Hey! Welcome back " + u.getUsername());
            }
        }
    }
    // get email and password from file
    public String[] getEmailAndPasswordFromFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader("user.txt"))){
            String email = reader.readLine();
            String password = reader.readLine();
            if(email != null && password != null){
                return new String[]{email, password};
            } else if (email.equals("null") || password.equals("null")) {
                return new String[]{"null", "null"};
            }
        }catch(Exception e){
            System.out.println("Error during get username and password " + e.getMessage());
        }
        return null;
    }
}
