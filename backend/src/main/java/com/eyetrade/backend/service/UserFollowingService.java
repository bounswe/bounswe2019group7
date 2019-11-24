package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.UserMapper;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.entity.UserFollowsUser;
import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import com.eyetrade.backend.repository.UserFollowsUserRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
public class UserFollowingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowsUserRepository userFollowsUserRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public MinimalUserResource followUser(UUID followerId, String followingEmail){
        // find both of the users
        User follower = userRepository.findById(followerId);
        User following = userRepository.findByEmail(followingEmail);
        // create the relationship object
        UserFollowsUser relation = new UserFollowsUser();
        relation.setFollower(follower);
        relation.setFollowing(following);
        relation.setTimestamp(new Date().getTime());
        // save it
        userFollowsUserRepository.saveAndFlush(relation);
        // create the notification
        notificationService.createNotification(follower, following);
        // return the response
        return UserMapper.entityToMinimalUserResource(following);
    }

    // Get the count of the followings of the given user
    public long countFollowings(User follower){
        return userFollowsUserRepository.countByFollower(follower);
    }

    // Get the count of the followers of the given user
    public long countFollowers(User following){
        return userFollowsUserRepository.countByFollowing(following);
    }

    // Get the followers of the current user
    public List<MinimalUserResource> getSelfFollowers(UUID id){
        User following = userRepository.findById(id);
        return getFollowers(following);
    }

    // Get the followers of another user
    public List<MinimalUserResource> getUserFollowers(String email){
        User following = userRepository.findByEmail(email);
        return getFollowers(following);
    }

    // Get the followings of the current user
    public List<MinimalUserResource> getSelfFollowings(UUID id){
        User follower = userRepository.findById(id);
        return getFollowings(follower);
    }

    // Get the followings of another user
    public List<MinimalUserResource> getUserFollowings(String email){
        User follower = userRepository.findByEmail(email);
        return getFollowings(follower);
    }

    private List<MinimalUserResource> getFollowers(User following){
        // Our user will be the following but we need her followers
        List<UserFollowsUser> relations = userFollowsUserRepository.findByFollowing(following);
        List<MinimalUserResource> followers = new ArrayList<>();
        for(UserFollowsUser relation : relations){
            MinimalUserResource follower = UserMapper.entityToMinimalUserResource(relation.getFollower());
            followers.add(follower);
        }
        return followers;
    }

    private List<MinimalUserResource> getFollowings(User follower){
        // Our user will be the follower but we need her followings
        List<UserFollowsUser> relations = userFollowsUserRepository.findByFollower(follower);
        List<MinimalUserResource> followings = new ArrayList<>();
        for(UserFollowsUser relation : relations){
            MinimalUserResource following = UserMapper.entityToMinimalUserResource(relation.getFollowing());
            followings.add(following);
        }
        return followings;
    }


}
