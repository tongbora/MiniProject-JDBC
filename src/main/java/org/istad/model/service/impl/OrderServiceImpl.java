package org.istad.model.service.impl;

import org.istad.mapper.ProductMapper;
import org.istad.model.dto.ProductResponDto;
import org.istad.model.entities.Order;
import org.istad.model.entities.Product;
import org.istad.model.entities.User;
import org.istad.model.ropository.OrderRepository;
import org.istad.model.ropository.ProductCartRepository;
import org.istad.model.ropository.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl {
    OrderRepository orderRepo = new OrderRepository();
    ProductCartServiceImpl productCartService = new ProductCartServiceImpl();
    ProductCartRepository productCartRepository = new ProductCartRepository();
    UserServiceImpl userService = new UserServiceImpl();
    UserRepository userRepository = new UserRepository();
    public Order makeOrder() {
        Order order = new Order();
        String[] emailAndPassword = userService.getEmailAndPasswordFromFile();
        try{
            if(!emailAndPassword[0].equals("null") && !emailAndPassword[1].equals("null")) {
                User user = userRepository.getUserByEmail(emailAndPassword[0]);
                List<Integer> pIDs = productCartRepository.getAllProductInCart(user.getId());
                for (Integer pID: pIDs) {
                    orderRepo.save(new Order(1,user.getId(),pID,Date.valueOf(LocalDate.now())));
                    productCartRepository.deleteProductFromCart(pID);
                }
                return order;
            }
        }catch(Exception e){
            System.out.println("Error while adding product to Cart In Service: " + e.getMessage());
        }
        return order;
    }
}
