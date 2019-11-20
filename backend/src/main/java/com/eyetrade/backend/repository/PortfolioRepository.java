package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {

    Portfolio findPortfolioById(UUID uuid);

    Portfolio findPortfolioByName(String name);
}
