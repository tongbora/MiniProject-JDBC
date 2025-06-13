package org.istad.model.service.impl;

import org.istad.mapper.ProductMapper;
import org.istad.model.dto.ProductResponDto;
import org.istad.model.entities.Product;
import org.istad.model.entities.User;
import org.istad.model.ropository.ProductCartRepository;
import org.istad.model.ropository.ProductRepository;
import org.istad.model.ropository.UserRepository;
import org.istad.model.service.ProductCartService;
import org.istad.model.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class ProductCartServiceImpl implements ProductCartService {
    ProductRepository productRepository = new ProductRepository();
    ProductCartRepository productCartRepository = new ProductCartRepository();
    UserRepository userRepository = new UserRepository();
    UserServiceImpl userService = new UserServiceImpl();

    @Override
    public void addProductToCart(String uuid) {
        //check if product exist
        Product product = productRepository.findByUuid(uuid);
        // check if user exist or not
        String[] emailAndPassword = userService.getEmailAndPasswordFromFile();
        try{
            if(!emailAndPassword[0].equals("null") && !emailAndPassword[1].equals("null") && product != null) {
                User user = userRepository.getUserByEmail(emailAndPassword[0]);
                productCartRepository.addProductToCart(user.getId(), product.getId());
            }
        }catch(Exception e){
            System.out.println("Error while adding product to Cart In Service: " + e.getMessage());
        }
    }

    @Override
    public void removeProductFromCart(String uuid) {
        //check if product exist
        Product product = productRepository.findByUuid(uuid);
        // check if user exist or not
        String[] emailAndPassword = userService.getEmailAndPasswordFromFile();
        try{
            if(!emailAndPassword[0].equals("null") && !emailAndPassword[1].equals("null") && product != null) {
                User user = userRepository.getUserByEmail(emailAndPassword[0]);
                productCartRepository.deleteProductFromCart(user.getId(), product.getId());
            }
        }catch(Exception e){
            System.out.println("Error while deleting product to Cart In Service: " + e.getMessage());
        }
    }

    @Override
    public List<ProductResponDto> getAllProductInCart() {
        List<ProductResponDto> productResponDto = new ArrayList<>();
        // check if user exist or not
        String[] emailAndPassword = userService.getEmailAndPasswordFromFile();
        try{
            if(!emailAndPassword[0].equals("null") && !emailAndPassword[1].equals("null")) {
                User user = userRepository.getUserByEmail(emailAndPassword[0]);
                productCartRepository.getAllProductInCart(user.getId()).forEach(pID -> {
                    Product product = productRepository.findById(pID);
                    productResponDto.add(ProductMapper.productToProductResponDto(product));
                });
                return productResponDto;
            }
        }catch(Exception e){
            System.out.println("Error while getting product from Product Cart: " + e.getMessage());
        }
        return List.of();
    }
}
