package org.istad.model.service.impl;

import org.istad.mapper.ProductMapper;
import org.istad.model.dto.ProductCreateDto;
import org.istad.model.dto.ProductResponDto;
import org.istad.model.entities.Product;
import org.istad.model.entities.ProductCart;
import org.istad.model.entities.User;
import org.istad.model.ropository.ProductRepository;
import org.istad.model.ropository.UserRepository;
import org.istad.model.service.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    // all dependency this class need
    ProductRepository productRepository = new ProductRepository();


    @Override
    public List<ProductResponDto> getAllProduct() {
        List<ProductResponDto> productResponDto = new ArrayList<>();
        productRepository.findAll().forEach(product -> {productResponDto.add(ProductMapper.productToProductResponDto(product));});
       return productResponDto;
    }

    @Override
    public ProductResponDto getProductById(Integer productId) {
        return ProductMapper.productToProductResponDto(productRepository.findById(productId));
    }

    @Override
    public List<ProductResponDto> getProductByName(String productName) {
        List<ProductResponDto> productResponDto = new ArrayList<>();
        productRepository.searchProductByName(productName).forEach(product -> {productResponDto.add(ProductMapper.productToProductResponDto(product));});
        return productResponDto;
    }

    @Override
    public ProductResponDto createProduct(ProductCreateDto productCreateDto) {
        return ProductMapper.productToProductResponDto(productRepository.save(ProductMapper.productCreateDtoToProduct(productCreateDto)));
    }

}
