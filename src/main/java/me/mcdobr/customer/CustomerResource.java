package me.mcdobr.customer;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
    @GET
    public List<CustomerDto> findCustomers() {
        return List.of(new CustomerDto(1L));
    }
}
