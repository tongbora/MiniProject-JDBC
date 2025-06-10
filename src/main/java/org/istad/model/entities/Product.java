package org.istad.model.entities;
import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String uuid;
    private String productName;
    private Double price;
    private Integer quantity;
    private Category category;
}
