package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.model.CreatePaymentDto;
import dev.dkorez.msathesis.catalog.model.PaymentDto;
import dev.dkorez.msathesis.catalog.service.PaymentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentControllerRest {
    private final PaymentService paymentService;

    @Inject
    public PaymentControllerRest(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @POST
    public PaymentDto create(CreatePaymentDto request) {
        return paymentService.create(request);
    }

    @GET
    @Path("/{id}")
    public PaymentDto find(@PathParam("id") Long id) {
        return paymentService.findById(id);
    }

    @GET
    @Path("/order/{order_id}")
    public List<PaymentDto> findPaymentsForOrder(@PathParam("order_id") Long id) {
        return paymentService.findPaymentsForOrder(id);
    }

    @PATCH
    @Path("/{id}/status")
    public PaymentDto updateStatus(@PathParam("id") Long id, String status) {
        return paymentService.updateStatus(id, status);
    }
}
