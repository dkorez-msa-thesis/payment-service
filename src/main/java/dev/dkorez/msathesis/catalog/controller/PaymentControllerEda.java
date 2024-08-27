package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.model.CreatePaymentDto;
import dev.dkorez.msathesis.catalog.model.PaymentDto;
import dev.dkorez.msathesis.catalog.service.PaymentCoordinator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("eda/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentControllerEda {
    private final PaymentCoordinator paymentCoordinator;
    private static final boolean SENT_EVENT = true;

    @Inject
    public PaymentControllerEda(PaymentCoordinator paymentCoordinator) {
        this.paymentCoordinator = paymentCoordinator;
    }

    @GET
    @Path("/{id}")
    public PaymentDto find(@PathParam("id") Long id) {
        return paymentCoordinator.findById(id);
    }

    @GET
    @Path("/order/{order_id}")
    public List<PaymentDto> findPaymentsForOrder(@PathParam("order_id") Long id) {
        return paymentCoordinator.findPaymentsForOrder(id);
    }

    @POST
    public Response create(CreatePaymentDto request) {
        PaymentDto response = paymentCoordinator.create(request, SENT_EVENT);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
    
    @PATCH
    @Path("/{id}/status")
    public PaymentDto updateStatus(@PathParam("id") Long id, String status) {
        return paymentCoordinator.updateStatus(id, status, SENT_EVENT);
    }
}
