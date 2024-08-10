package me.mcdobr.customer;

import jakarta.transaction.Transactional;
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
    public List<CustomerDto> findCustomers() {
        return customerService.findAll();
    }

    @POST
    public CustomerDto create(CustomerCreationRequestDto creationRequest) {
        return customerService.create(creationRequest);
    }
}
