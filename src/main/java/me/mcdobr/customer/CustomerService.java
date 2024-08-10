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
    private final ValueCommands<String, CustomerDto> customerCommands;

    public CustomerService(RedisDataSource redisDataSource) {
        this.keyCommands = redisDataSource.key();
        this.customerCommands = redisDataSource.value(CustomerDto.class);
    }

    List<CustomerDto> findAll() {
        List<Customer> customers = Customer.listAll();
        return customers.stream().map(CustomerService::toDto).toList();
    }

    CustomerDto findById(Long customerId) {
        return toDto(Customer.findById(customerId));
    }

    CustomerDto create(CustomerCreationRequestDto creationRequest) {
        if (keyCommands.exists(creationRequest.getIdempotencyKey())) {
            return customerCommands.get(creationRequest.getIdempotencyKey());
        } else {
            CustomerDto result = createNewCustomer(creationRequest);
            customerCommands.append(creationRequest.getIdempotencyKey(), result);
            return result;
        }
    }

    @Transactional
    CustomerDto createNewCustomer(CustomerCreationRequestDto creationRequest) {
        Customer customer = toEntity(creationRequest);
        customer.persist();
        CustomerDto result = toDto(customer);
        if (log.isDebugEnabled()) {
            log.debug("Created {}", result);
        }
        return result;
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
