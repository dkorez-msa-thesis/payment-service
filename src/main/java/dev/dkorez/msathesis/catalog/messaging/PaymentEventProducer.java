package dev.dkorez.msathesis.catalog.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class PaymentEventProducer {
    @Inject
    @Channel("payment-events")
    Emitter<PaymentEvent> eventEmitter;

    public void sendEvent(PaymentEvent event) {
        eventEmitter.send(event);
    }
}
