package com.eyetrade.backend.mapper;

import com.eyetrade.backend.constants.PrivacyType;
import com.eyetrade.backend.constants.Role;
import com.eyetrade.backend.model.dto.user.TraderUserDto;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.dto.user.BasicUserDto;
import com.eyetrade.backend.model.resource.user.CompleteUserResource;
import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import com.eyetrade.backend.model.resource.user.PartialUserResource;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

public class UserMapper {

    public static User basicUserDtoToEntity(BasicUserDto basicUserDto) {
        User user = new User();
        user.setEmail(basicUserDto.getEmail());
        user.setName(basicUserDto.getName());
        user.setSurname(basicUserDto.getSurname());
        user.setPhone(basicUserDto.getPhone());
        user.setLocationX(basicUserDto.getLocationX());
        user.setLocationY(basicUserDto.getLocationY());
        user.setCountry(basicUserDto.getCountry());
        user.setCity(basicUserDto.getCity());
        user.setPrivacyType(PrivacyType.PUBLIC_USER); // Public user on default
        user.setRole(Role.BASIC_USER);
        user.setConfirmed(false); // User is not confirmed yet
        return user;
    }

    public static User traderUserDtoToEntity(TraderUserDto traderRegisterDto) {
        // First, map the fields that are also exist in basic users
        User user = basicUserDtoToEntity(traderRegisterDto);
        // Then, map the trader user fields
        user.setRole(Role.TRADER_USER);
        user.setIban(traderRegisterDto.getIban());
        user.setIdentityNo(traderRegisterDto.getIdentityNo());
        return user;
    }

    public static MinimalUserResource entityToMinimalUserResource(User user) {
        MinimalUserResource resource = new MinimalUserResource();
        resource.setEmail(user.getEmail());
        resource.setName(user.getName());
        resource.setSurname(user.getSurname());
        return resource;
    }

    public static PartialUserResource entityToPartialUserResource(User user, long followerCount, long followingCount){
        PartialUserResource resource = new PartialUserResource();
        resource.setEmail(user.getEmail());
        resource.setName(user.getName());
        resource.setSurname(user.getSurname());

        resource.setLocationX(user.getLocationX());
        resource.setLocationY(user.getLocationY());
        resource.setCountry(user.getCountry());
        resource.setCity(user.getCity());
        resource.setRole(user.getRole());
        resource.setFollowerCount(followerCount);
        resource.setFollowingCount(followingCount);
        return resource;
    }

    public static CompleteUserResource entityToCompleteUserResource(User user, long followerCount, long followingCount){
        CompleteUserResource resource = new CompleteUserResource();
        resource.setEmail(user.getEmail());
        resource.setName(user.getName());
        resource.setSurname(user.getSurname());

        resource.setLocationX(user.getLocationX());
        resource.setLocationY(user.getLocationY());
        resource.setCountry(user.getCountry());
        resource.setCity(user.getCity());
        resource.setRole(user.getRole());
        resource.setFollowerCount(followerCount);
        resource.setFollowingCount(followingCount);

        resource.setPhone(user.getPhone());
        resource.setPrivacyType(user.getPrivacyType());
        resource.setIban(user.getIban());
        resource.setIdentityNo(user.getIdentityNo());
        return resource;
    }

}
