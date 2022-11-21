package com.patryk.fraud;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FraudCheckService {

    private  final FraudCheckHistoryRepository fraudCheckHistoryRepository;


    public boolean isFraudulentCustomer(Integer customerId){
        fraudCheckHistoryRepository.save(
                FraudCheckHistory
                        .builder()
                        .customerId(customerId)
                        .isFraudster(true)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return false;
    }

}
