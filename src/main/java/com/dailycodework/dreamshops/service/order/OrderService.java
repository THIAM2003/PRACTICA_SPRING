package com.dailycodework.dreamshops.service.order;

import org.springframework.stereotype.Service;

import com.dailycodework.dreamshops.model.Order;
import com.dailycodework.dreamshops.repository.OrderRespository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class OrderService implements IOrderService {

    private final OrderRespository orderRepository;
    
    @Override
    public Order placerOrder(Long userId) {
        return null;
    }

    @Override
    public Order getOrder(Long orderId) {
        return null;
    }

}
