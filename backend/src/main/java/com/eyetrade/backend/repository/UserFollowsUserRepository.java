package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.entity.UserFollowsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserFollowsUserRepository extends JpaRepository<UserFollowsUser, UUID> {


    List<UserFollowsUser> findByFollower(User follower);

    List<UserFollowsUser> findByFollowing(User following);

    long countByFollower(User follower);

    long countByFollowing(User following);

}
