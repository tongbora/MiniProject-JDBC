package org.istad.model.dto;

public record UserCreateDto(
        String username,
        String email,
        String password
) {
}
