package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.request.OrderRequest;
import com.food.response.OrderResponse;
import com.food.response.Response;
import com.food.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create a new order
    @PostMapping("/create-order")
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    // Get a specific order by ID
    @GetMapping("/get-order-by-id/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Get all orders
    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Update order status
    @PutMapping("/update-order-status/{id}/status")
    public Response updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

    // Delete an order
    @DeleteMapping("/{id}")
    public Response deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }
    
    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUsername(@PathVariable String username) {
        List<OrderResponse> orders = orderService.getOrdersByUsername(username);
        return ResponseEntity.ok(orders);
    }

}
