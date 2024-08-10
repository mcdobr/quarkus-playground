package me.mcdobr.customer;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
    @GET
    public List<CustomerDto> findCustomers() {
        List<Customer> customers = Customer.listAll();
        return customers.stream().map(CustomerResource::toDto).toList();
    }

    @POST
    @Transactional
    public CustomerDto create(CustomerCreationRequestDto creationRequest) {
        Customer customer = toEntity(creationRequest);
        customer.persist();

        return toDto(customer);
    }

    private static Customer toEntity(CustomerCreationRequestDto creationRequest) {
        Customer customer = new Customer();
        customer.name = creationRequest.getName();
        return customer;
    }

    private static CustomerDto toDto(Customer customer) {
        return new CustomerDto(customer.id, customer.name);
    }
}
