package org.istad.controller;

import org.istad.model.dto.ProductCreateDto;
import org.istad.model.dto.ProductResponDto;
import org.istad.model.service.impl.ProductServiceImpl;

import java.util.List;

public class ProductController {
    ProductServiceImpl productService = new ProductServiceImpl();
    public List<ProductResponDto> getAllProduct(){
        return productService.getAllProduct();
    }
    public ProductResponDto getProductById(Integer productId){
        return productService.getProductById(productId);
    }
    public List<ProductResponDto> getProductByName(String productName){
        return productService.getProductByName(productName);
    }
    public ProductResponDto createProduct(ProductCreateDto productCreateDto){
        return productService.createProduct(productCreateDto);
    }
}
