package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.entity.PaymentDao;
import dev.dkorez.msathesis.catalog.exception.NotFoundException;
import dev.dkorez.msathesis.catalog.mapper.PaymentMapper;
import dev.dkorez.msathesis.catalog.model.CreatePaymentDto;
import dev.dkorez.msathesis.catalog.model.PaymentDto;
import dev.dkorez.msathesis.catalog.repository.PaymentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Inject
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public PaymentDto create(CreatePaymentDto order) {
        PaymentDao entity = PaymentMapper.fromCreateDto(order);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        paymentRepository.persist(entity);

        return PaymentMapper.toDto(entity);
    }

    public PaymentDto findById(Long id) {
        return paymentRepository.findByIdOptional(id)
                .map(PaymentMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Payment " + id + " not found"));
    }

    public List<PaymentDto> findPaymentsForOrder(Long orderId) {
        return paymentRepository.list(" checkoutId = ?1", orderId).stream()
                .map(PaymentMapper::toDto)
                .toList();
    }

    @Transactional
    public PaymentDto updateStatus(Long id, String status) {
        PaymentDao entity = paymentRepository.findByIdOptional(id).orElseThrow(() ->
                new NotFoundException("payment " + id + " not found"));

        entity.setPaymentStatus(status);
        entity.setUpdatedAt(LocalDateTime.now());
        paymentRepository.persist(entity);

        return PaymentMapper.toDto(entity);
    }
}
