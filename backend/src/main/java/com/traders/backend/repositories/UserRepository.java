package com.traders.backend.repositories;

import com.traders.backend.models.domain_models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// that interface must be fixed

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // some example methods, they should be changed with respect to the needs!

    List<User> findAll();

    @Query(value = "Select * From User Where email = :email")
    List<User> findByEmail(@Param("email") String email);

    @Query(value = "Select * From User Where name = :name")
    List<User> findByName(@Param("name") String name);

}
