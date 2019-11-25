package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.entity.UserTradingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@Repository
public interface UserTradingAccountRepository extends JpaRepository<UserTradingAccount, UUID> {

    UserTradingAccount findUserTradingAccountById(UUID id);

    UserTradingAccount findUserTradingAccountByUser(User user);

}
