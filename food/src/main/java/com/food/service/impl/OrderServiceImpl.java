package com.food.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.dao.FoodItemRepository;
import com.food.dao.OrderItemRepository;
import com.food.dao.OrderRepository;
import com.food.dao.UserRepository;
import com.food.entity.FoodItem;
import com.food.entity.Order;
import com.food.entity.OrderItem;
import com.food.entity.User;
import com.food.request.OrderItemRequest;
import com.food.request.OrderRequest;
import com.food.response.OrderItemResponse;
import com.food.response.OrderResponse;
import com.food.response.Response;
import com.food.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private UserRepository userRepository;

    private OrderItemResponse mapToOrderItemResponse(OrderItem item) {
        OrderItemResponse response = new OrderItemResponse();
        response.setId(item.getId());
        response.setFoodItemName(item.getFoodItem().getName());
        response.setPrice(item.getPrice());
        response.setQuantity(item.getQuantity());
        response.setSpecialInstructions(item.getSpecialInstructions());
        response.setIsPrepared(item.getIsPrepared());
        response.setIsDelivered(item.getIsDelivered());
        return response;
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserName(order.getUser().getName());
        response.setOrderItems(order.getOrderItems().stream()
                .map(this::mapToOrderItemResponse)
                .collect(Collectors.toList()));
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus().name());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setPaymentCompleted(order.getPaymentCompleted());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());
        return response;
    }

    @Override
    public OrderResponse createOrder(OrderRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(com.food.entity.OrderStatus.PENDING);

        List<OrderItem> orderItems = request.getOrderItems().stream().map(itemRequest -> {
            FoodItem foodItem = foodItemRepository.findById(itemRequest.getFoodItemId())
                    .orElseThrow(() -> new RuntimeException(
                            "Food item not found with id: " + itemRequest.getFoodItemId()));

            if (foodItem.getAvailableQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient quantity for food item: " + foodItem.getName());
            }

            // Decrease stock
            foodItem.setAvailableQuantity(foodItem.getAvailableQuantity() - itemRequest.getQuantity());
            foodItemRepository.save(foodItem);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFoodItem(foodItem);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(foodItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            orderItem.setSpecialInstructions(itemRequest.getSpecialInstructions());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        BigDecimal totalPrice = orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        return mapToOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return mapToOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Response updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setStatus(com.food.entity.OrderStatus.valueOf(status.toUpperCase()));
        orderRepository.save(order);
        return new Response("Order status updated successfully", "200");
    }

    @Override
    public Response deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        orderRepository.delete(order);
        return new Response("Order deleted successfully", "200");
    }
    
    @Override
    public List<OrderResponse> getOrdersByUsername(String email) {
        List<Order> orders = orderRepository.findByUserEmail(email);

        return orders.stream().map(order -> {
            OrderResponse response = new OrderResponse();
            response.setId(order.getId());
            response.setUserName(order.getUser().getName());
            response.setTotalPrice(order.getTotalPrice());
            response.setStatus(order.getStatus().name());
            response.setPaymentMethod(order.getPaymentMethod());
            response.setPaymentCompleted(order.getPaymentCompleted());
            response.setCreatedAt(order.getCreatedAt());
            response.setUpdatedAt(order.getUpdatedAt());

            // Map order items
            List<OrderItemResponse> items = order.getOrderItems().stream().map(item -> {
                OrderItemResponse itemResp = new OrderItemResponse();
                itemResp.setId(item.getId());
                itemResp.setFoodItemName(item.getFoodItem().getName());
                itemResp.setPrice(item.getPrice());
                itemResp.setQuantity(item.getQuantity());
                itemResp.setSpecialInstructions(item.getSpecialInstructions());
                itemResp.setIsPrepared(item.getIsPrepared());
                itemResp.setIsDelivered(item.getIsDelivered());
                return itemResp;
            }).collect(Collectors.toList());

            response.setOrderItems(items);

            return response;
        }).collect(Collectors.toList());
    }
}
