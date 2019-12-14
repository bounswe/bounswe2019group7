package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.BuySellOrderMapper;
import com.eyetrade.backend.model.dto.BuySellOrderDto;
import com.eyetrade.backend.model.entity.BuySellOrder;
import com.eyetrade.backend.model.resource.transaction.BuySellOrderResource;
import com.eyetrade.backend.repository.BuySellOrderRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static com.eyetrade.backend.constants.ErrorConstants.*;

/**
 * Created by Emir Gökdemir
 * on 13 Ara 2019
 */

@Service
public class BuySellOrderService {

    @Autowired
    private BuySellOrderMapper mapper;

    @Autowired
    private BuySellOrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public BuySellOrderResource addBuySellOrder(BuySellOrderDto dto, UUID userId){
        if(dto.getMaxRate()<dto.getMinRate()){
            throw new ArithmeticException(MIN_RATE_CAN_NOT_BE_BIGGER_THAN_MAX);
        }
        BuySellOrder order= mapper.dtoToEntity(dto);
        order.setUser(userRepository.findById(userId));
        return mapper.entityToResource(repository.save(order));
    }

    public List<BuySellOrderResource> getSelfOrders(UUID userId){
        return mapper.entityToResource(repository.findBuySellOrdersByUser(userRepository.findById(userId)));
    }

    @Transactional
    public BuySellOrderResource updateBuySellOrder(BuySellOrderDto dto,UUID buySellOrderId, UUID userId) throws IllegalAccessException {
        if(dto.getMaxRate()<dto.getMinRate()){
            throw new ArithmeticException(MIN_RATE_CAN_NOT_BE_BIGGER_THAN_MAX);
        }
        BuySellOrder order=repository.getOne(buySellOrderId);
        if(!order.getUser().getId().equals(userId)){
            throw new IllegalAccessException(USER_CANNOT_UPDATE_ANOTHER_USERS_ORDER);
        }
        mapper.dtoToEntityForUpdate(dto,order);
        return mapper.entityToResource(repository.save(order));
    }

    @Transactional
    @Modifying
    public void deleteBuySellOrder(UUID buySellOrderId, UUID userId) throws IllegalAccessException {
        BuySellOrder order=repository.getOne(buySellOrderId);
        if(!order.getUser().getId().equals(userId)){
            throw new IllegalAccessException(USER_CANNOT_DELETE_ANOTHER_USERS_ORDER);
        }
        repository.delete(order);
    }

    protected List<BuySellOrderResource> getAllOrders(){
        return mapper.entityToResource(repository.findAll());
    }

}
