package org.istad.model.service;

import org.istad.model.dto.UserCreateDto;
import org.istad.model.dto.UserResponDto;

public interface UserService {
    UserResponDto register(UserCreateDto user);
    UserResponDto getUserByUuid(String uuid);
    UserResponDto getUserByEmail(String email);
}
