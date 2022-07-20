package com.proyecto1.signatory.service.impl;

import com.proyecto1.signatory.client.CustomerClient;
import com.proyecto1.signatory.client.ProductClient;
import com.proyecto1.signatory.client.TransactionClient;
import com.proyecto1.signatory.entity.Customer;
import com.proyecto1.signatory.entity.Product;
import com.proyecto1.signatory.entity.Signatory;
import com.proyecto1.signatory.entity.Transaction;
import com.proyecto1.signatory.repository.SignatoryRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class SignatoryServiceImplTest {
    @Mock
    private SignatoryRepository signatoryRepository;

    @Mock
    private TransactionClient transactionClient;

    @Mock
    private ProductClient productClient;

    @Mock
    private CustomerClient customerClient;

    @InjectMocks
    private SignatoryServiceImpl signatoryServiceImpl;

    @Test
     void createDepositTest() {
        Signatory signatoryMono = Signatory.builder()
                .id(ObjectId.get().toString())
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("8797897797")
                .transactionId("34242423234")
                .build();

        Product product = new Product();
        product.setId("83457346534534");
        product.setIndProduct(1);
        product.setDescIndProduct("cuenta empresarial");
        product.setTypeProduct(6);
        product.setDescTypeProduct("cuenta de credito");

        Customer customer = new Customer();
        customer.setId("2325454534234234");
        customer.setName("zoraida");
        customer.setLastName("zegarra");
        customer.setDocNumber("7349837243");
        customer.setTypeCustomer(2);
        customer.setDescTypeCustomer("empresarial");

        Transaction transaction = new Transaction();
        transaction.setId("34242423234");
        transaction.setCustomerId("7890729347");
        transaction.setProductId("8974593463");
        transaction.setAccountNumber("384724239423");
        transaction.setMovementLimit(1);
        transaction.setCreditLimit(BigDecimal.valueOf(100));
        transaction.setAvailableBalance(BigDecimal.valueOf(30));
        transaction.setMaintenanceCommission(BigDecimal.valueOf(30));
        transaction.setCardNumber("764249236473249234234");
        transaction.setRetirementDateFixedTerm(LocalDate.now());
        transaction.setProduct(product);


        Mockito.when(transactionClient.getAccountWithDetails(transaction.getId())).thenReturn(Mono.just(transaction));
        Mockito.when(productClient.getProduct(product.getId())).thenReturn(Mono.just(product));
        Mockito.when(customerClient.getCustomer(customer.getId())).thenReturn(Mono.just(customer));
        Mockito.when(signatoryRepository.save(Mockito.any())).thenReturn(Mono.just(signatoryMono));

        assertDoesNotThrow(() -> signatoryServiceImpl.create(signatoryMono)
                .subscribe(response -> {
                    assertEquals(signatoryMono.getDocNumber(), response.getDocNumber());
                }));
    }


    @Test
     void updateDepositTest() {
        Signatory signatoryMono = Signatory.builder()
                .id(ObjectId.get().toString())
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("8797897797")
                .transactionId("342873574h")
                .build();

        String id = "6767668789fds9";

        Mockito.when(signatoryRepository.findById(id)).thenReturn(Mono.just(signatoryMono));
        Mockito.when(signatoryRepository.save(signatoryMono)).thenReturn(Mono.just(signatoryMono));

        assertDoesNotThrow(() -> signatoryServiceImpl.update(signatoryMono,id)
                .subscribe(response -> {
                    assertEquals(signatoryMono.getDocNumber(), response.getDocNumber());
                }));
    }

    @Test
     void findAll() {

        Signatory signatoryMono = Signatory.builder()
                .id(ObjectId.get().toString())
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("8797897797")
                .transactionId("342873574h")
                .build();

        Mockito.when(signatoryRepository.findAll()).thenReturn(Flux.just(signatoryMono));

        assertDoesNotThrow(() -> signatoryServiceImpl.findAll()
                .subscribe(response -> {
                    assertEquals(signatoryMono.getDocNumber(), response.getDocNumber());
                }));
    }


    @Test
     void FindById() {

        Signatory signatoryMono = Signatory.builder()
                .id("2346723847262")
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("8797897797")
                .transactionId("342873574h")
                .build();

        Mockito.when(signatoryRepository.findById("2346723847262")).thenReturn(Mono.just(signatoryMono));

        assertDoesNotThrow(() -> signatoryServiceImpl.findById(signatoryMono.getId())
                .subscribe(response -> {
                    assertEquals(signatoryMono.getDocNumber(), response.getDocNumber());
                }));
    }

    @Test
     void Delete() {

        Signatory signatoryMono = Signatory.builder()
                .id(ObjectId.get().toString())
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("8797897797")
                .transactionId("342873574h")
                .build();
        String id = "6767668789fds9";

        Mockito.when(signatoryRepository.findById("6767668789fds9")).thenReturn(Mono.just(signatoryMono));
        Mockito.when(signatoryRepository.delete(signatoryMono)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> signatoryServiceImpl.delete(id)
                .subscribe(response -> {
                    assertEquals(new Signatory(), response);
                }));

    }

}
