package com.food.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.entity.Order;
import com.food.entity.OrderItem;

public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByUserEmail(String email);


}
