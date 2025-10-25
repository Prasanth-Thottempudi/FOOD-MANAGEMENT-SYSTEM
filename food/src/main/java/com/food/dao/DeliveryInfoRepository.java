package com.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.entity.DeliveryInfo;

public interface DeliveryInfoRepository  extends JpaRepository<DeliveryInfo, String>{

}
