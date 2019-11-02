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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    // Get the followers of the current user
    public List<UserResource> getSelfFollowers(UUID id){
        User following = userRepository.findById(id);
        return getFollowers(following);
    }

    // Get the followers of another user
    public List<UserResource> getUserFollowers(String email){
        User following = userRepository.findByEmail(email);
        return getFollowers(following);
    }

    // Get the followings of the current user
    public List<UserResource> getSelfFollowings(UUID id){
        User follower = userRepository.findById(id);
        return getFollowings(follower);
    }

    // Get the followings of another user
    public List<UserResource> getUserFollowings(String email){
        User follower = userRepository.findByEmail(email);
        return getFollowings(follower);
    }

    private List<UserResource> getFollowers(User following){
        // Our user will be the following but we need her followers
        List<UserFollowing> relations = userFollowingRepository.findByFollowing(following);
        List<UserResource> followers = new ArrayList<>();
        for(UserFollowing relation : relations){
            UserResource follower = UserMapper.entityToResource(relation.getFollower());
            followers.add(follower);
        }
        return followers;
    }

    private List<UserResource> getFollowings(User follower){
        // Our user will be the follower but we need her followings
        List<UserFollowing> relations = userFollowingRepository.findByFollower(follower);
        List<UserResource> followings = new ArrayList<>();
        for(UserFollowing relation : relations){
            UserResource following = UserMapper.entityToResource(relation.getFollowing());
            followings.add(following);
        }
        return followings;
    }

}
