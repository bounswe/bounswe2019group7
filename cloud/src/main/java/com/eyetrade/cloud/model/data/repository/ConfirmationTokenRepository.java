package com.eyetrade.cloud.model.data.repository;

import com.eyetrade.cloud.model.data.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository  extends JpaRepository<ConfirmationToken,String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
