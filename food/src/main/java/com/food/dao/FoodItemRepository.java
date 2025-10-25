package com.food.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.entity.FoodItem;

public interface FoodItemRepository  extends JpaRepository<FoodItem, Long>{

    List<FoodItem> findByCategoryId(Long categoryId);

}
