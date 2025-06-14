package org.istad.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCart {
    private Integer id;
    private List<Integer> productId;
    private Integer userId;
}