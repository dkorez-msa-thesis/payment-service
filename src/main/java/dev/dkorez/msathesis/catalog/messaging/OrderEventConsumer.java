package dev.dkorez.msathesis.catalog.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dkorez.msathesis.catalog.model.PaymentDto;
import dev.dkorez.msathesis.catalog.service.PaymentCoordinator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderEventConsumer {
    private final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);

    private final ObjectMapper objectMapper;
    private final PaymentCoordinator paymentCoordinator;

    @Inject
    public OrderEventConsumer(PaymentCoordinator paymentCoordinator, ObjectMapper objectMapper) {
        this.paymentCoordinator = paymentCoordinator;
        this.objectMapper = objectMapper;
    }

    @Incoming("order-events")
    public CompletionStage<Void> processUpdates(String event) {
        try {
            logger.info("incoming order-events: {}", event);
            OrderEvent orderEvent = objectMapper.readValue(event, OrderEvent.class);

            if (orderEvent.getType() == OrderEventType.CANCELLED) {
                List<PaymentDto> payments = paymentCoordinator.findPaymentsForOrder(orderEvent.getOrderId());
                if (payments != null && !payments.isEmpty()) {
                    for (PaymentDto payment: payments) {
                        paymentCoordinator.updateStatus(payment.getId(), "CANCEL", false);
                    }
                }
            }
        } catch (JsonProcessingException e) {
            logger.error("error processing product-event: {}", e.getMessage(), e);
        }

        return CompletableFuture.completedFuture(null);
    }
}
