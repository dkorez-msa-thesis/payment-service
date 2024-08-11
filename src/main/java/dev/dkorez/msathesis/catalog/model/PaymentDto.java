package dev.dkorez.msathesis.catalog.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    Long id;
    Long orderId;
    BigDecimal amount;
    String paymentMethod;
    String paymentStatus;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
