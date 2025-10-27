package com.food.service;


import com.food.entity.Payment;

public interface PaymentService {
    /**
     * Create a Razorpay order (server-side), save Payment record as PENDING.
     * amount is in paise (e.g., 10000 = Rs 100.00)
     */
    Payment createRazorpayOrder(Long userId, Long internalOrderId, Long amountInPaise, String receipt);

    /**
     * Verify the signature coming back from Checkout (order_id, payment_id, signature).
     * If valid, mark Payment as SUCCESS and persist razorpayPaymentId/signature.
     */
    boolean verifyAndSavePayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature);

    /**
     * Verify webhook signature and process webhook payload (order.paid / payment.captured etc.)
     */
    boolean verifyWebhookSignature(String payloadBody, String receivedSignature);

    // other helper methods
}
