package com.eyetrade.backend.repository;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.model.entity.CurrencyFollowing;
import com.eyetrade.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyFollowingRepository extends JpaRepository<CurrencyFollowing,String> {

    boolean existsByBaseCurrencyTypeAndFollower(CurrencyType type,User follower);
    CurrencyFollowing findByFollower(User follower);
}
