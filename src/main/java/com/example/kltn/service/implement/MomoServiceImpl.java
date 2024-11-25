package com.example.kltn.service.implement;

import com.example.kltn.service.MomoService;
import com.example.kltn.config.MomoConfig;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MomoServiceImpl implements MomoService {
    
    private final MomoConfig momoConfig;
    
    @Override
    public String createPayment(String orderId, String amount, String orderInfo) {
        // TODO: Implement tạo payment URL với Momo API
        return momoConfig.getEndpoint() + "?orderId=" + orderId;
    }
    
    @Override
    public boolean processPaymentNotify(Map<String, String> notifyParams) {
        // TODO: Implement xử lý notify từ Momo
        return true;
    }
} 