package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.UserMapper;
import com.eyetrade.backend.model.dto.UserDto;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    public UserResource getUserProfile(UUID userID){
        User user =userRepository.findById(userID);
        return UserMapper.entityToResource(user);
    }

    public UserResource updateProfile(UUID userID, UserDto newUserDto){

        User newUserProfile = UserMapper.dtoToEntity(newUserDto);
        User user =userRepository.findById(userID);

        user.setCity(newUserProfile.getCity());
        user.setEmail(newUserProfile.getEmail());
        user.setIban(newUserProfile.getIban());
        user.setIdentityNo(newUserProfile.getIdentityNo());
        user.setLocationX(newUserProfile.getLocationX());
        user.setLocationY(newUserProfile.getLocationY());
        user.setName(newUserProfile.getName());
        user.setSurname(newUserProfile.getSurname());
        user.setPhone(newUserProfile.getPhone());

        userRepository.save(user);
        return UserMapper.entityToResource(user);
    }
}
