package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.UserMapper;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.entity.UserFollowing;
import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.repository.UserFollowingRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
public class UserFollowingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowingRepository userFollowingRepository;

    @Transactional
    public UserResource followUser(UUID followerId, String followingEmail){
        // find both of the users
        User follower = userRepository.findById(followerId);
        User following = userRepository.findByEmail(followingEmail);
        // create the relationship object
        UserFollowing relation = new UserFollowing();
        relation.setFollower(follower);
        relation.setFollowing(following);
        relation.setTimestamp(new Date().getTime());
        // save it
        userFollowingRepository.saveAndFlush(relation);
        // return the response
        return UserMapper.entityToResource(following);
    }

}
