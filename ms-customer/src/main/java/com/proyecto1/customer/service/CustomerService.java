package com.proyecto1.customer.service;

import com.proyecto1.customer.dto.CustomerDTO;
import com.proyecto1.customer.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<Customer> findAll();

    Mono<Customer> create(CustomerDTO c);

    Mono<Customer> findById(String id);

    Mono<Customer> update(CustomerDTO c, String id);

    Mono<Customer> delete(String id);
}
