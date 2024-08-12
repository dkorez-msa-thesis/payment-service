package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.messaging.PaymentEvent;
import dev.dkorez.msathesis.catalog.messaging.PaymentEventProducer;
import dev.dkorez.msathesis.catalog.messaging.PaymentEventType;
import dev.dkorez.msathesis.catalog.model.CreatePaymentDto;
import dev.dkorez.msathesis.catalog.model.PaymentDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PaymentCoordinator {
    private final PaymentService paymentService;
    private final PaymentEventProducer eventProducer;

    @Inject
    public PaymentCoordinator(PaymentService paymentService, PaymentEventProducer eventProducer) {
        this.paymentService = paymentService;
        this.eventProducer = eventProducer;
    }

    public PaymentDto findById(Long id) {
        return paymentService.findById(id);
    }

    public List<PaymentDto> findPaymentsForOrder(Long orderId) {
        return paymentService.findPaymentsForOrder(orderId);
    }

    public void create(CreatePaymentDto payment, boolean sendEvent) {
        PaymentDto response = paymentService.create(payment);

        if (sendEvent) {
            PaymentEvent event = new PaymentEvent(PaymentEventType.CREATED, response.getId(), response);
            eventProducer.sendEvent(event);
        }
    }

    public void updateStatus(Long id, String status, boolean sendEvent) {
        PaymentDto response = paymentService.updateStatus(id, status);

        if (sendEvent) {
            PaymentEvent event = new PaymentEvent(PaymentEventType.UPDATED, response.getId(), response);
            eventProducer.sendEvent(event);
        }
    }
}
