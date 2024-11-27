package com.example.kltn.controller;

import com.example.kltn.entity.Payment;
import com.example.kltn.service.PaymentService;
import com.example.kltn.service.MomoService;
import com.example.kltn.dto.PaymentRequest;
import com.example.kltn.entity.Customer;
import com.example.kltn.service.CustomerService;
import com.example.kltn.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.kltn.constant.Status;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final MomoService momoService;
    private final CustomerService customerService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAll();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getById(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = paymentService.saveOrUpdate(payment);
        return ResponseEntity.ok(createdPayment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        payment.setId(id);
        Payment updatedPayment = paymentService.saveOrUpdate(payment);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/momo/create")
    public ResponseEntity<?> createMomoPayment(@RequestBody PaymentRequest request) {
        try {
            String orderInfo = request.getOrderInfo();
            if (orderInfo == null) {
                orderInfo = "Thanh toán đơn hàng " + request.getOrderId();
            }
            
            String paymentUrl = momoService.createPayment(
                request.getOrderId(), 
                request.getAmount().toString(), 
                orderInfo
            );
            
            // Lưu thông tin thanh toán
            Payment payment = new Payment();
            payment.setPaymentMethod("MOMO");
            payment.setPaymentStatus(Status.PAYMENT_PENDING.getValue());
            payment.setAmount(request.getAmount());
            payment.setPaymentDate(new Date());
            
            // Liên kết với customer nếu có
            if (request.getCustomerId() != null) {
                Customer customer = customerService.getById(request.getCustomerId());
                payment.setCustomer(customer);
            }
            
            paymentService.saveOrUpdate(payment);
            
            return ResponseEntity.ok(Map.of(
                "paymentUrl", paymentUrl,
                "orderId", request.getOrderId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/momo/callback")
    public ResponseEntity<?> momoCallback(@RequestParam Map<String, String> params) {
        try {
            String orderId = params.get("orderId");
            String resultCode = params.get("resultCode");
            
            if ("0".equals(resultCode)) {
                Payment payment = paymentService.getById(Long.parseLong(orderId));
                if (payment != null) {
                    payment.setPaymentStatus(Status.PAYMENT_SUCCESS.getValue());
                    paymentService.saveOrUpdate(payment);
                    
                    // Cập nhật order status nếu có
                    if (payment.getOrder() != null) {
                        payment.getOrder().setStatus(Status.ORDER_PAID.getValue());
                        orderService.saveOrUpdate(payment.getOrder());
                    }
                }
                return ResponseEntity.ok(Map.of("message", "Thanh toán thành công"));
            }
            return ResponseEntity.badRequest().body(Map.of("message", "Thanh toán thất bại"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/momo-notify")
    public ResponseEntity<?> momoNotify(@RequestBody Map<String, String> notifyParams) {
        try {
            if (momoService.processPaymentNotify(notifyParams)) {
                return ResponseEntity.ok(Map.of("message", "Notify processed successfully"));
            }
            return ResponseEntity.badRequest().body(Map.of("message", "Failed to process notify"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 