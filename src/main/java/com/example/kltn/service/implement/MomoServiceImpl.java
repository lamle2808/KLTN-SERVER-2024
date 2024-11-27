package com.example.kltn.service.implement;

import com.example.kltn.service.MomoService;
import com.example.kltn.config.MomoConfig;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.HashMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
@RequiredArgsConstructor
@Slf4j
public class MomoServiceImpl implements MomoService {
    
    private final MomoConfig momoConfig;
    private final RestTemplate restTemplate;
    
    @Override
    public String createPayment(String orderId, String amount, String orderInfo) {
        try {
            log.info("Bắt đầu tạo payment Momo - orderId: {}, amount: {}", orderId, amount);
            
            String requestId = String.valueOf(System.currentTimeMillis());
            String rawSignature = "accessKey=" + momoConfig.getAccessKey() +
                                "&amount=" + amount +
                                "&extraData=" +
                                "&ipnUrl=" + momoConfig.getNotifyUrl() +
                                "&orderId=" + orderId +
                                "&orderInfo=" + orderInfo +
                                "&partnerCode=" + momoConfig.getPartnerCode() +
                                "&redirectUrl=" + momoConfig.getReturnUrl() +
                                "&requestId=" + requestId +
                                "&requestType=captureWallet";
                                
            String signature = createSignature(rawSignature, momoConfig.getSecretKey());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("partnerCode", momoConfig.getPartnerCode());
            requestBody.put("partnerName", momoConfig.getPartnerName());
            requestBody.put("storeId", momoConfig.getStoreId());
            requestBody.put("requestId", requestId);
            requestBody.put("amount", Long.parseLong(amount));
            requestBody.put("orderId", orderId);
            requestBody.put("orderInfo", orderInfo);
            requestBody.put("redirectUrl", momoConfig.getReturnUrl());
            requestBody.put("ipnUrl", momoConfig.getNotifyUrl());
            requestBody.put("lang", momoConfig.getLang());
            requestBody.put("requestType", "captureWallet");
            requestBody.put("signature", signature);
            requestBody.put("extraData", "");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            log.info("Gọi API Momo với payload: {}", requestBody);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(
                momoConfig.getEndpoint(), 
                request, 
                Map.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String payUrl = response.getBody().get("payUrl").toString();
                log.info("Tạo payment URL thành công: {}", payUrl);
                return payUrl;
            }
            
            log.error("Lỗi từ Momo API: {}", response.getBody());
            throw new RuntimeException("Không thể tạo payment URL");
            
        } catch (Exception e) {
            log.error("Lỗi tạo payment Momo: " + e.getMessage(), e);
            throw new RuntimeException("Lỗi tạo payment: " + e.getMessage());
        }
    }
    
    @Override
    public boolean processPaymentNotify(Map<String, String> notifyParams) {
        try {
            log.info("Xử lý notify từ Momo: {}", notifyParams);
            
            // Verify signature từ Momo
            String receivedSignature = notifyParams.get("signature");
            String rawSignature = "accessKey=" + momoConfig.getAccessKey() +
                                "&amount=" + notifyParams.get("amount") +
                                "&extraData=" + notifyParams.get("extraData") +
                                "&orderId=" + notifyParams.get("orderId") +
                                "&orderInfo=" + notifyParams.get("orderInfo") +
                                "&orderType=" + notifyParams.get("orderType") +
                                "&partnerCode=" + notifyParams.get("partnerCode") +
                                "&payType=" + notifyParams.get("payType") +
                                "&requestId=" + notifyParams.get("requestId") +
                                "&responseTime=" + notifyParams.get("responseTime") +
                                "&resultCode=" + notifyParams.get("resultCode") +
                                "&transId=" + notifyParams.get("transId");
                                
            String signature = createSignature(rawSignature, momoConfig.getSecretKey());
            
            if (!signature.equals(receivedSignature)) {
                log.error("Invalid signature from Momo");
                return false;
            }
            
            String resultCode = notifyParams.get("resultCode");
            return "0".equals(resultCode);
            
        } catch (Exception e) {
            log.error("Lỗi xử lý notify Momo: " + e.getMessage(), e);
            return false;
        }
    }

    private String createSignature(String message, String secretKey) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(message.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo chữ ký: " + e.getMessage());
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
} 