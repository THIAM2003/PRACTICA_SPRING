package com.dailycodework.dreamshops.service.cart;

import java.math.BigDecimal;

import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.User;

public interface ICartService {

    Cart getCart (Long id);
    void clearCart (Long id);
    BigDecimal getTotalPrice (Long id);     
    Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);
}
