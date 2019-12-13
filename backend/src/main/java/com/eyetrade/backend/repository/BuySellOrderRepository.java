package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.BuySellOrder;
import com.eyetrade.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BuySellOrderRepository extends JpaRepository<BuySellOrder, UUID> {
    List<BuySellOrder> findBuySellOrdersByUser(User user);

}
