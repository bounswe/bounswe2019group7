package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {

    User findById(UUID id);

    User findByEmail(String email);

    User findByEmailAndPword(String email, String pword);

    @Query(value = "select * from users where name like '%:name%'",nativeQuery = true)
    List<User> findUserByName(String name);

    User findUserByIdentityNo(String identityNo);

    User findByPhone(String phone);

    List<User> findAll();

}
