package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.BuySellOrderMapper;
import com.eyetrade.backend.model.dto.BuySellOrderDto;
import com.eyetrade.backend.model.entity.BuySellOrder;
import com.eyetrade.backend.model.resource.transaction.BuySellOrderResource;
import com.eyetrade.backend.repository.BuySellOrderRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 13 Ara 2019
 */

@Service
public class BuySellOrderService {


    @Autowired
    private BuySellOrderMapper buySellOrderMapper;

    @Autowired
    private BuySellOrderRepository repository;

    @Autowired
    private UserRepository userRepository;
    @Transactional
    public BuySellOrderResource addBuySellOrder(BuySellOrderDto dto, UUID userId){
        BuySellOrder order=buySellOrderMapper.dtoToEntity(dto);
        order.setUser(userRepository.findById(userId));
        return buySellOrderMapper.entityToResource(repository.save(order));
    }

}
