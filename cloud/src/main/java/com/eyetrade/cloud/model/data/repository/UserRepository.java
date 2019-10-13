package com.eyetrade.cloud.model.data.repository;

import com.eyetrade.cloud.model.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User,String> {
    User findByEmail(String email);
    User findByIdentifier(String identifier);
}
