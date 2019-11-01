package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.entity.UserFollowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowingRepository extends JpaRepository<UserFollowing,String> {

    List<UserFollowing> findByFollower(User follower);

    List<UserFollowing> findByFollowing(User following);

}
