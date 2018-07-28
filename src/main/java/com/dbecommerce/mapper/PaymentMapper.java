package com.dbecommerce.mapper;

import com.dbecommerce.domain.Payment;
import com.dbecommerce.domain.dto.PaymentDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper {

    public PaymentDto mapToPaymentDto(Payment payment) {
        return new PaymentDto(payment.getId(), payment.getPaid());
    }

    public List<PaymentDto> mapToListPaymentDto(List<Payment> payments) {
        return payments.stream()
                .map(p -> mapToPaymentDto(p))
                .collect(Collectors.toList());
    }

}
