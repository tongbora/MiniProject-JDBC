package org.istad.mapper;

import org.istad.model.dto.ProductCreateDto;
import org.istad.model.dto.ProductResponDto;
import org.istad.model.entities.Product;

import java.util.Random;
import java.util.UUID;

public class ProductMapper {
    public static ProductResponDto productToProductResponDto(Product product) {
        return new ProductResponDto(
                product.getUuid(),
                product.getProductName(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory()
        );
    }
    public static Product productCreateDtoToProduct(ProductCreateDto productCreateDto) {
        return new Product(
                0,
                UUID.randomUUID().toString(),
                productCreateDto.productName(),
                productCreateDto.price(),
                productCreateDto.quantity(),
                false,
                productCreateDto.category()
        );
    }
}
