package com.dailycodework.dreamshops.service.order;

import java.util.List;

import com.dailycodework.dreamshops.model.Order;

public interface IOrderService {

    Order placerOrder(Long userId);
    Order getOrder(Long orderId);
    List<Order> getUserOrders(Long userId);
}
