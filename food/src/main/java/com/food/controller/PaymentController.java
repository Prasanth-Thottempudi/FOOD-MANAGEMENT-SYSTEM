package com.food.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.entity.Payment;
import com.food.request.CreateOrderRequest;
import com.food.request.VerifyPaymentRequest;
import com.food.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final String razorpayKey;

    public PaymentController(PaymentService paymentService,
                             @Value("${razorpay.key}") String razorpayKey) {
        this.paymentService = paymentService;
        this.razorpayKey = razorpayKey;
    }

    /**
     * Create a Razorpay order on server and return payment details to frontend.
     * Frontend will use "key" (razorpayKey) and the returned order_id to open Checkout.
     */
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest req) {
        Payment p = paymentService.createRazorpayOrder(req.getUserId(), req.getInternalOrderId(), req.getAmount(), req.getReceipt());
        return ResponseEntity.ok(Map.of(
            "razorpayKey", razorpayKey,
            "razorpayOrderId", p.getRazorpayOrderId(),
            "amount", p.getAmount(),
            "currency", p.getCurrency()
        ));
    }

    /**
     * Endpoint called by the frontend once payment completes to verify signature.
     * The frontend receives razorpay_payment_id, razorpay_order_id, razorpay_signature from Checkout.
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody VerifyPaymentRequest body) {
        boolean ok = paymentService.verifyAndSavePayment(body.getRazorpayOrderId(), body.getRazorpayPaymentId(), body.getRazorpaySignature());
        if (ok) {
            return ResponseEntity.ok(Map.of("status", "success"));
        } else {
            return ResponseEntity.status(400).body(Map.of("status", "failure", "reason", "signature_mismatch_or_record_not_found"));
        }
    }

    /**
     * Webhook receiver for Razorpay events.
     * Razorpay sends header "X-Razorpay-Signature"
     * Ensure your webhook URL is set in Razorpay dashboard for Test mode during development.
     */
    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(@RequestHeader("X-Razorpay-Signature") String signature,
                                          @RequestBody String payload) {
        boolean ok = paymentService.verifyWebhookSignature(payload, signature);
        if (!ok) {
            return ResponseEntity.status(400).body("invalid signature");
        }


        return ResponseEntity.ok("received");
    }

   
}
