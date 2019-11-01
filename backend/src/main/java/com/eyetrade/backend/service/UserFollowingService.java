package com.eyetrade.backend.service;

import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.repository.UserFollowingRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserFollowingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowingRepository userFollowingRepository;

    @Transactional
    public UserResource followUser(String followerEmail, String followingEmail){
        return null;
    }

}
