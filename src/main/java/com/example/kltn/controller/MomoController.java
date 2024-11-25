package com.example.kltn.controller;

import com.example.kltn.service.MomoService;
import com.example.kltn.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class MomoController {
    private final MomoService momoService;
    private final PaymentService paymentService;

    @PostMapping("/create-momo")
    public ResponseEntity<?> createMomoPayment(@RequestParam String orderId, 
                                             @RequestParam String amount,
                                             @RequestParam String orderInfo) {
        try {
            String paymentUrl = momoService.createPayment(orderId, amount, orderInfo);
            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi tạo thanh toán: " + e.getMessage());
        }
    }

    @GetMapping("/momo-return")
    public ResponseEntity<?> momoReturn(@RequestParam Map<String, String> params) {
        // Xử lý callback từ Momo
        String orderId = params.get("orderId");
        String resultCode = params.get("resultCode");
        
        if ("0".equals(resultCode)) {
            // Cập nhật trạng thái thanh toán thành công
            return ResponseEntity.ok("Thanh toán thành công");
        }
        return ResponseEntity.badRequest().body("Thanh toán thất bại");
    }
} 