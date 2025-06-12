package org.istad.model.dto;

public record ProductCreateDto(
        String productName,
        Double price,
        Integer quantity,
        String category
) {
}
