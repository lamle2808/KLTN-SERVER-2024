package com.example.kltn.controller;

import com.example.kltn.service.implement.VNPayServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class VNPayController {

    private final VNPayServiceImpl vnPayService;

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestParam String orderId, @RequestParam String amount, @RequestParam String orderInfo) {
        try {
            String paymentUrl = vnPayService.createPaymentUrl(orderId, amount, orderInfo);
            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating payment URL: " + e.getMessage());
        }
    }

    @GetMapping("/vnpay_return")
    public ResponseEntity<String> vnPayReturn(@RequestParam Map<String, String> allParams) {
        // Xử lý logic sau khi nhận phản hồi từ VNPAY
        // Kiểm tra trạng thái giao dịch và cập nhật đơn hàng
        return ResponseEntity.ok("Thanh toán thành công!");
    }
} 