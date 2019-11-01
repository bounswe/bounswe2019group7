package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findById(UUID id);

    User findByEmail(String email);

    User findByEmailAndPword(String email, String pword);

    User findUserByIdentityNo(String identityNo);

    User findByPhone(String phone);

    List<User> findAll();

}
