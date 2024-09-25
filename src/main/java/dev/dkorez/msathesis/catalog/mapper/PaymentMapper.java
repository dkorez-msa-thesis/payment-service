package dev.dkorez.msathesis.catalog.mapper;

import dev.dkorez.msathesis.catalog.entity.PaymentDao;
import dev.dkorez.msathesis.catalog.model.CreatePaymentDto;
import dev.dkorez.msathesis.catalog.model.PaymentDto;

public class PaymentMapper {
    public static PaymentDto toDto(PaymentDao entity) {
        if (entity == null)
            return null;

        PaymentDto dto = new PaymentDto();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getCheckoutId());
        dto.setAmount(entity.getAmount());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setPaymentStatus(entity.getPaymentStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public static PaymentDao fromDto(PaymentDto dto) {
        if (dto == null)
            return null;

        PaymentDao entity = new PaymentDao();
        entity.setCheckoutId(dto.getOrderId());
        entity.setAmount(dto.getAmount());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setPaymentStatus(dto.getPaymentStatus());

        return entity;
    }

    public static PaymentDao fromCreateDto(CreatePaymentDto dto) {
        if (dto == null)
            return null;

        PaymentDao entity = new PaymentDao();
        entity.setCheckoutId(dto.getCheckoutId());
        entity.setAmount(dto.getAmount());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setPaymentStatus(dto.getPaymentStatus());

        return entity;
    }
}
