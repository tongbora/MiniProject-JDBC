package org.istad.controller;

import org.istad.model.dto.ProductResponDto;
import org.istad.model.service.ProductCartService;
import org.istad.model.service.impl.ProductCartServiceImpl;

import java.util.List;

public class ProductCartController {
    ProductCartService productCartService = new ProductCartServiceImpl();
    public List<ProductResponDto> getAllProductInCart(){
        return productCartService.getAllProductInCart();
    }
    public void addProductToCart(String uuid){
        productCartService.addProductToCart(uuid);
    }
}
