package com.food.service;

import java.util.List;
import com.food.request.OrderRequest;
import com.food.response.OrderResponse;
import com.food.response.Response;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrderById(Long orderId);

    List<OrderResponse> getAllOrders();

    Response updateOrderStatus(Long orderId, String status);

    Response deleteOrder(Long orderId);
    
    List<OrderResponse> getOrdersByUsername(String username);

}
