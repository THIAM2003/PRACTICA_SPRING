package com.dailycodework.dreamshops.service.order;

import com.dailycodework.dreamshops.model.Order;

public interface IOrderService {

    Order placerOrder(Long userId);
    Order getOrder(Long orderId);
}
