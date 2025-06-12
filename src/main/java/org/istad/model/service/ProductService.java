package org.istad.model.service;

import org.istad.model.dto.ProductCreateDto;
import org.istad.model.dto.ProductResponDto;

import java.util.List;

public interface ProductService {
    List<ProductResponDto> getAllProduct();
    ProductResponDto getProductById(Integer productId);
    List<ProductResponDto> getProductByName(String productName);
    ProductResponDto createProduct(ProductCreateDto productCreateDto);
}
