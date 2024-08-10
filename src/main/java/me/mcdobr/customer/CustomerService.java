package me.mcdobr.customer;

import io.quarkus.redis.datasource.RedisDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CustomerService {
    List<CustomerDto> findAll() {
        List<Customer> customers = Customer.listAll();
        return customers.stream().map(CustomerService::toDto).toList();
    }

    @Transactional
    CustomerDto create(CustomerCreationRequestDto creationRequest) {
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
