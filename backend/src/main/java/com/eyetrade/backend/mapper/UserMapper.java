package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.dto.UserDto;
import com.eyetrade.backend.model.resource.UserResource;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

public class UserMapper {
    public static User dtoToEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPword(userDto.getPassword());
        user.setCity(userDto.getCity());
        user.setLocationX(userDto.getLocationX());
        user.setLocationY(userDto.getLocationY());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setRole(userDto.getRole());
        user.setIban(userDto.getIban());
        user.setIdentityNo(userDto.getIdentityNo());
        user.setStatus(userDto.getStatus());
        return user;
    }

    public static UserResource entityToResource(User user) {
        if (user == null) {
            return null;
        }
        UserResource userResource = new UserResource();
        userResource.setEmail(user.getEmail());
        userResource.setName(user.getName());
        userResource.setSurname(user.getSurname());
        userResource.setConfirmed(user.isConfirmed());
        return userResource;
    }
}
