package org.istad.controller;

import org.istad.model.dto.UserCreateDto;
import org.istad.model.dto.UserResponDto;
import org.istad.model.ropository.UserRepository;
import org.istad.model.service.impl.UserServiceImpl;

import java.io.File;

public class UserController {
    private final UserServiceImpl userService = new UserServiceImpl();
    public UserResponDto register(UserCreateDto user){
        return userService.register(user);
    }
    public UserResponDto getUserByUuid(String uuid){
        return userService.getUserByUuid(uuid);
    }
    public UserResponDto getUserByEmail(String email){
        return userService.getUserByEmail(email);
    }



    public boolean login (String email, String password){
        return userService.login(email, password);
    }
    public boolean logout(){
        return userService.logout();
    }
    public void autoLogin(){
        userService.autoLogin();
    }
    public String[] getEmailAndPasswordFromFile(){
        return userService.getEmailAndPasswordFromFile();
    }
}
