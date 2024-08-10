package me.mcdobr.customer;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GET
    public List<CustomerDto> findAll() {
        return customerService.findAll();
    }

    @GET
    @Path("/{id}")
    public CustomerDto findById(@PathParam("id") Long customerId) {
        return customerService.findById(customerId);
    }


    @POST
    public CustomerDto create(CustomerCreationRequestDto creationRequest) {
        return customerService.create(creationRequest);
    }
}
