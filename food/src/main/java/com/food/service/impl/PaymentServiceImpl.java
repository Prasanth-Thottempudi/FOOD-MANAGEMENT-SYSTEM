package com.food.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.dao.OrderRepository;
import com.food.dao.PaymentRepository;
import com.food.dao.UserRepository;
import com.food.entity.Order;
import com.food.entity.Payment;
import com.food.entity.PaymentStatus;
import com.food.entity.User;
import com.food.service.PaymentService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RazorpayClient razorpayClient;
    private final String razorpayKey;
    private final String razorpaySecret;
    private final String webhookSecret;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              UserRepository userRepository,
                              OrderRepository orderRepository,
                              @Value("${razorpay.key}") String razorpayKey,
                              @Value("${razorpay.secret}") String razorpaySecret,
                              @Value("${razorpay.webhook.secret}") String webhookSecret) throws RazorpayException {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.razorpayKey = razorpayKey;
        this.razorpaySecret = razorpaySecret;
        this.webhookSecret = webhookSecret;
        this.razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);
    }

    @Override
    @Transactional
    public Payment createRazorpayOrder(Long userId, Long orderId, Long amountInPaise, String receipt) {
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            Optional<Order> orderOpt = orderRepository.findById(orderId);

            if (userOpt.isEmpty() || orderOpt.isEmpty()) {
                throw new RuntimeException("User or Order not found");
            }

            User user = userOpt.get();
            Order order = orderOpt.get();

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", receipt);
            orderRequest.put("payment_capture", 1);

            com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);

            Payment payment = new Payment();
            payment.setUser(user);
            payment.setOrder(order);
            payment.setAmount(amountInPaise);
            payment.setCurrency("INR");
            payment.setRazorpayOrderId(razorpayOrder.get("id"));
            payment.setPaymentStatus(PaymentStatus.PENDING);

            // link payment to order
            order.setPayment(payment);

            paymentRepository.save(payment);
            orderRepository.save(order);

            return payment;

        } catch (RazorpayException e) {
            throw new RuntimeException("Failed to create Razorpay Order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public boolean verifyAndSavePayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        String payload = razorpayOrderId + "|" + razorpayPaymentId;
        String generatedSig = hmacSha256(payload, razorpaySecret);

        Optional<Payment> optPayment = paymentRepository.findByRazorpayOrderId(razorpayOrderId);

        if (optPayment.isEmpty()) {
            return false;
        }

        Payment payment = optPayment.get();

        if (generatedSig.equals(razorpaySignature)) {
            payment.setRazorpayPaymentId(razorpayPaymentId);
            payment.setRazorpaySignature(razorpaySignature);
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            payment.setUpdatedAt(LocalDateTime.now());

            // Update order status
            Order order = payment.getOrder();
            order.setPaymentCompleted(true);
            order.setStatus(com.food.entity.OrderStatus.CONFIRMED);
            orderRepository.save(order);

            paymentRepository.save(payment);
            return true;

        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            payment.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(payment);
            return false;
        }
    }

    @Override
    public boolean verifyWebhookSignature(String payloadBody, String receivedSignature) {
        String expected = hmacSha256(payloadBody, webhookSecret);
        return expected.equals(receivedSignature);
    }

    private String hmacSha256(String data, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Unable to compute HMAC SHA256", e);
        }
    }
}
