package com.food.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.entity.Cart;
import com.food.entity.CartItem;
import com.food.entity.FoodItem;

public interface CartitemRepository extends JpaRepository<CartItem, Long>{

	List<CartItem> findByFoodItem(FoodItem foodItem);
    List<CartItem> findByCart(Cart cart);

}
