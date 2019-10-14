package com.eyetrade.cloud.util.mapper;

import com.eyetrade.cloud.model.data.entity.User;
import com.eyetrade.cloud.model.dto.UserDto;
import com.eyetrade.cloud.model.resource.UserResource;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

public class UserMapper {
    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setCity(userDto.getCity());
        user.setLocationX(userDto.getLocationX());
        user.setLocationY(userDto.getLocationY());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setUserType(userDto.getUserType());
        return user;
    }

    public static UserResource toResource(User user) {
        if (user == null) {
            return null;
        }
        UserResource userResource = new UserResource();
        userResource.setEmail(user.getEmail());
        userResource.setName(user.getName());
        userResource.setSurname(user.getSurname());
        userResource.setConfirmed(user.isConfirmed());
        userResource.setIdentifier(user.getIdentifier());
        return userResource;
    }
}
