package com.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.entity.Category;

public interface CategoryRepository  extends JpaRepository<Category, String>{

}
