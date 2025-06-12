package org.istad.model.dto;

public record ProductResponDto(
        String uuid,
        String productName,
        Double price,
        Integer quantity,
        String category
) {
}
