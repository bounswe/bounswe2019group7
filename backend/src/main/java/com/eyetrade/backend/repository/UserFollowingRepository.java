package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowingRepository extends JpaRepository<User,String> {
}
