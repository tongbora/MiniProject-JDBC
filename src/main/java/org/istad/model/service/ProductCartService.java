package org.istad.model.service;

import org.istad.model.dto.ProductResponDto;

import java.util.List;

public interface ProductCartService {
    public List<ProductResponDto> getAllProductInCart();
    public void addProductToCart(String uuid);
    public void removeProductFromCart(String uuid);
}
