package dev.dkorez.msathesis.catalog.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentDto {
    Long orderId;
    BigDecimal amount;
    String paymentMethod;
    String paymentStatus;
}
