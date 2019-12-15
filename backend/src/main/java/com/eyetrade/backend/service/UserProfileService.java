package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.ErrorConstants;
import com.eyetrade.backend.constants.PrivacyType;
import com.eyetrade.backend.constants.Role;
import com.eyetrade.backend.mapper.UserMapper;
import com.eyetrade.backend.model.dto.user.BasicToTraderDto;
import com.eyetrade.backend.model.dto.user.BasicUserDto;
import com.eyetrade.backend.model.dto.user.TraderUserDto;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.user.CompleteUserResource;
import com.eyetrade.backend.model.resource.user.PartialUserResource;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowingService userFollowingService;

    public CompleteUserResource getSelfProfile(UUID selfId){
        User selfUser = userRepository.findById(selfId);
        return UserMapper.entityToCompleteUserResource(selfUser,
                userFollowingService.countFollowers(selfUser),
                userFollowingService.countFollowings(selfUser));
    }

    public PartialUserResource getOtherProfile(UUID selfId, String otherUserEmail){
        User selfUser = userRepository.findById(selfId);
        User otherUser = userRepository.findByEmail(otherUserEmail);
        if(otherUser.getPrivacyType() == PrivacyType.PUBLIC_USER
                || userFollowingService.getSelfFollowings(selfUser.getId()).contains(otherUser)){
            return UserMapper.entityToPartialUserResource(otherUser,
                    userFollowingService.countFollowers(otherUser),
                    userFollowingService.countFollowings(otherUser));
        }
        else{
            // you have no access for that user because it is private and you do not follow
            return null;
        }
    }

    public CompleteUserResource updateBasicProfile(UUID userId, BasicUserDto newBasicUserDto){
        User oldUser = userRepository.findById(userId);
        User updatedUser = UserMapper.basicUserDtoToEntity(newBasicUserDto);
        updatedUser.setId(oldUser.getId());
        updatedUser.setPword(oldUser.getPword());
        updatedUser.setConfirmed(oldUser.isConfirmed());
        userRepository.save(updatedUser);
        return UserMapper.entityToCompleteUserResource(updatedUser,
                userFollowingService.countFollowers(updatedUser),
                userFollowingService.countFollowings(updatedUser));
    }

    public CompleteUserResource updateTraderProfile(UUID userId, TraderUserDto newTraderUserDto){
        User oldUser = userRepository.findById(userId);
        User updatedUser = UserMapper.traderUserDtoToEntity(newTraderUserDto);
        updatedUser.setId(oldUser.getId());
        updatedUser.setPword(oldUser.getPword());
        updatedUser.setConfirmed(oldUser.isConfirmed());
        userRepository.save(updatedUser);
        return UserMapper.entityToCompleteUserResource(updatedUser,
                userFollowingService.countFollowers(updatedUser),
                userFollowingService.countFollowings(updatedUser));
    }

    public CompleteUserResource updatePrivacy(UUID userId, PrivacyType privacy){
        User user = userRepository.findById(userId);
        user.setPrivacyType(privacy);
        userRepository.save(user);
        return UserMapper.entityToCompleteUserResource(user,
                userFollowingService.countFollowers(user),
                userFollowingService.countFollowings(user));
    }

    public CompleteUserResource upgradeBasicToTrader(UUID userId, BasicToTraderDto dto) throws IllegalAccessException {
        User user = userRepository.findById(userId);
        if(user.getRole() == Role.TRADER_USER){
            throw new IllegalAccessException(ErrorConstants.USER_IS_ALREADY_TRADER);
        }
        user.setRole(Role.TRADER_USER);
        user.setIdentityNo(dto.getIdentityNo());
        user.setIban(dto.getIban());
        userRepository.save(user);
        return UserMapper.entityToCompleteUserResource(user,
                userFollowingService.countFollowers(user),
                userFollowingService.countFollowings(user));
    }

}
