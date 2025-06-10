package org.istad.mapper;

import org.istad.model.dto.UserCreateDto;
import org.istad.model.dto.UserResponDto;
import org.istad.model.entities.User;

import java.util.Random;
import java.util.UUID;

public class UserMapper {

    public static  UserResponDto userToUserResponDto(User user) {
        return new UserResponDto(
            user.getUuid(),
            user.getUsername(),
            user.getEmail()
        );
    }

    public static User userCreateDtoToUser(UserCreateDto userCreateDto) {
        return new User(
                new Random().nextInt(999999999),
                UUID.randomUUID().toString(),
                userCreateDto.username(),
                userCreateDto.email(),
                userCreateDto.password(),
                false
        );
    }
}
