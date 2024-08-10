package me.mcdobr.customer;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private final KeyCommands<String> keyCommands;
    private final ValueCommands<String, Long> customerIdCommands;

    public CustomerService(RedisDataSource redisDataSource) {
        this.keyCommands = redisDataSource.key();
        this.customerIdCommands = redisDataSource.value(Long.class);
    }

    List<CustomerDto> findAll() {
        List<Customer> customers = Customer.listAll();
        return customers.stream().map(CustomerService::toDto).toList();
    }

    CustomerDto findById(Long customerId) {
        return toDto(Customer.findById(customerId));
    }

    @Transactional
    CustomerDto create(CustomerCreationRequestDto creationRequest) {
        if (keyCommands.exists(creationRequest.getIdempotencyKey())) {
            return findById(customerIdCommands.get(creationRequest.getIdempotencyKey()));
        } else {
            Customer customer = toEntity(creationRequest);
            customer.persist();
            CustomerDto dto = toDto(customer);

            // Pretty bad for transactional because there's a network call
            customerIdCommands.append(creationRequest.getIdempotencyKey(), customer.id);
            if (log.isDebugEnabled()) {
                log.debug("Created {}", dto);
            }
            return dto;
        }
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
