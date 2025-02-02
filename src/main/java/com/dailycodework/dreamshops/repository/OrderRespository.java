package com.dailycodework.dreamshops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.model.Order;

public interface OrderRespository extends JpaRepository<Order, Long> {

}
