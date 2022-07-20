package com.proyecto1.customer.service.impl;

import com.proyecto1.customer.controller.CustomerController;
import com.proyecto1.customer.dto.CustomerDTO;
import com.proyecto1.customer.entity.Customer;
import com.proyecto1.customer.repository.CustomerRepository;
import com.proyecto1.customer.service.CustomerService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    @Test
    void createCustomerTest() {
        CustomerDTO customerMono = CustomerDTO.builder()
                .id(ObjectId.get().toString())
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("2342342342")
                .typeCustomer(1)
                .descTypeCustomer("personal").build();

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerMono,customer);

        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(Mono.just(customer));

        assertDoesNotThrow(() -> customerServiceImpl.create(customerMono)
                .subscribe(response -> {
                    assertEquals(customerMono.getLastName(), response.getLastName());
                }));

    }

    @Test
    void updateCustomerTest() {
        CustomerDTO customerMono = CustomerDTO.builder()
                .name("yasmin")
                .lastName("oyarce")
                .docNumber("2342342342")
                .typeCustomer(1)
                .descTypeCustomer("personal").build();
        String id = "unhb2342342342";

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerMono,customer);

        Mockito.when(customerRepository.findById(id)).thenReturn(Mono.just(customer));

        Mockito.when(customerRepository.save(customer)).thenReturn(Mono.just(customer));

        assertDoesNotThrow(() -> customerServiceImpl.update(customerMono, id)
                .subscribe(response -> {
                    assertEquals(customerMono.getLastName(), response.getLastName());
                }));

    }

    @Test
    void findAll() {
        Customer customerMono = Customer.builder()
                .name("yasmin")
                .lastName("oyarce")
                .docNumber("2342342342")
                .typeCustomer(1)
                .descTypeCustomer("personal").build();

        Mockito.when(customerRepository.findAll()).thenReturn(Flux.just(customerMono));

        assertDoesNotThrow(() -> customerServiceImpl.findAll()
                .subscribe(response -> {
                    assertEquals(customerMono.getLastName(), response.getLastName());
                }));
    }

    @Test
    void FindById() {

        Customer customer = new Customer();
        customer.setId("12buhvg24uhjknv2");
        customer.setName("zoraida");
        customer.setLastName("oyarce");
        customer.setDocNumber("23424234234");
        customer.setTypeCustomer(1);
        customer.setDescTypeCustomer("personal");

        Mockito.when(customerRepository.findById("12buhvg24uhjknv2")).thenReturn(Mono.just(customer));

        assertDoesNotThrow(() -> customerServiceImpl.findById(customer.getId())
                .subscribe(response -> {
                    assertEquals(customer.getLastName(), response.getLastName());
                }));

    }

    @Test
    void Delete() {

        Customer customerMono = Customer.builder()
                .name("yasmin")
                .lastName("oyarce")
                .docNumber("2342342342")
                .typeCustomer(1)
                .descTypeCustomer("personal").build();

        String id = "unhb2342342342";

        Mockito.when(customerRepository.findById("unhb2342342342")).thenReturn(Mono.just(customerMono));
        Mockito.when(customerRepository.delete(customerMono)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> customerServiceImpl.delete(id)
                .subscribe(response -> {
                    assertEquals(new Customer(), response);
                }));

    }
}
