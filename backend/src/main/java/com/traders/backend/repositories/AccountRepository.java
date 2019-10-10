package com.traders.backend.repositories;

import com.traders.backend.models.domain_models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NoRepositoryBean
public interface AccountRepository extends JpaRepository<Account, Integer> {

    // some example methods, they should be changed with respect to the needs!

    List<Account> findAll();

    @Query(value = "Select * From Account Where name = :name")
    List<Account> findByName(@Param("name") String name);

}
