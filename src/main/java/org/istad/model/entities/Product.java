package org.istad.model.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String uuid;
    private String productName;
    private Double price;
    private Integer quantity;
    private Boolean isDeleted;
    private String category;
}
