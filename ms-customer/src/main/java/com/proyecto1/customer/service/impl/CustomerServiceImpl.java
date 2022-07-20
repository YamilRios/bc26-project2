package com.proyecto1.customer.service.impl;

import com.proyecto1.customer.dto.CustomerDTO;
import com.proyecto1.customer.entity.Customer;
import com.proyecto1.customer.repository.CustomerRepository;
import com.proyecto1.customer.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LogManager.getLogger(CustomerServiceImpl.class);
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Flux<Customer> findAll() {
        log.info("Method call FindAll - customer");
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> create(CustomerDTO c) {
        log.info("Method call Create - customer");
       Customer customer = new Customer();
        BeanUtils.copyProperties(c,customer);
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Customer> findById(String id) {
        log.info("Method call FindById - customer");
        return customerRepository.findById(id);
    }

    @Override
    public Mono<Customer> update(CustomerDTO c, String id) {
        log.info("Method call Update - customer");
        Customer customer = new Customer();
        BeanUtils.copyProperties(c,customer);

        return customerRepository.findById(id)
                .map( x -> {
                    x.setName(c.getName());
                    x.setLastName(c.getLastName());
                    x.setDocNumber(c.getDocNumber());
                    x.setTypeCustomer(c.getTypeCustomer());
                    x.setDescTypeCustomer(c.getDescTypeCustomer());
                    return x;
                }).flatMap(customerRepository::save);
    }

    @Override
    public Mono<Customer> delete(String id) {
        log.info("Method call Delete - customer");
        return customerRepository.findById(id).flatMap(
                x -> customerRepository.delete(x).then(Mono.just(new Customer())));
    }
}
