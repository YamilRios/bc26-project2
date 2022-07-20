package com.proyecto1.deposit.service.impl;


import com.proyecto1.deposit.client.TransactionClient;
import com.proyecto1.deposit.dto.DepositDTO;
import com.proyecto1.deposit.entity.Deposit;
import com.proyecto1.deposit.entity.Product;
import com.proyecto1.deposit.entity.Transaction;
import com.proyecto1.deposit.repository.DepositRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
public class DepositServiceImplTest {

    @Mock
    private DepositRepository depositRepository;

    @Mock
    private TransactionClient transactionClient;

    @InjectMocks
    private DepositServiceImpl depositServiceImpl;

    @Test
     void createDepositTest() {
        DepositDTO depositMono = DepositDTO.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .depositAmount(BigDecimal.valueOf(200))
                .description("demo 1")
                .transactionId("34242423234")
                .build();

        Product product = new Product();
        product.setId("83457346534534");
        product.setIndProduct(2);
        product.setDescIndProduct("cuenta empresarial");
        product.setTypeProduct(1);
        product.setDescTypeProduct("cuenta de ahorro");

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


        Deposit deposit = new Deposit();
        BeanUtils.copyProperties(depositMono,deposit);

        Mockito.when(transactionClient.getTransactionWithDetails(transaction.getId())).thenReturn(Mono.just(transaction));
        Mockito.when(depositRepository.save(Mockito.any())).thenReturn(Mono.just(deposit));

        assertDoesNotThrow(() -> depositServiceImpl.create(depositMono)
                .subscribe(response -> {
                    assertEquals(depositMono.getDepositAmount(), response.getDepositAmount());
                }));
    }

    @Test
     void updateDepositTest() {
        DepositDTO depositMono = DepositDTO.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .depositAmount(BigDecimal.valueOf(200))
                .description("demo 1")
                .transactionId("234234jnjk2344")
                .build();
        String id = "6767668789fds9";

        Deposit deposit = new Deposit();
        BeanUtils.copyProperties(depositMono,deposit);

        Mockito.when(depositRepository.findById(id)).thenReturn(Mono.just(deposit));
        Mockito.when(depositRepository.save(deposit)).thenReturn(Mono.just(deposit));

        assertDoesNotThrow(() -> depositServiceImpl.update(depositMono,id)
                .subscribe(response -> {
                    assertEquals(depositMono.getDepositAmount(), response.getDepositAmount());
                }));
    }

    @Test
     void findAll() {
        Deposit depositMono = Deposit.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .depositAmount(BigDecimal.valueOf(200))
                .description("demo 1")
                .transactionId("234234jnjk2344")
                .build();

        Mockito.when(depositRepository.findAll()).thenReturn(Flux.just(depositMono));

        assertDoesNotThrow(() -> depositServiceImpl.findAll()
                .subscribe(response -> {
                    assertEquals(depositMono.getDepositAmount(), response.getDepositAmount());
                }));
    }

    @Test
     void FindById() {

        Deposit deposit = new Deposit();
        deposit.setId("12buhvg24uhjknv2");
        deposit.setDate(LocalDate.now());
        deposit.setDepositAmount(BigDecimal.valueOf(200));
        deposit.setDescription("demo2");
        deposit.setTransactionId("2323423424");

        Mockito.when(depositRepository.findById("12buhvg24uhjknv2")).thenReturn(Mono.just(deposit));

        assertDoesNotThrow(() -> depositServiceImpl.findById(deposit.getId())
                .subscribe(response -> {
                    assertEquals(deposit.getDepositAmount(), response.getDepositAmount());
                }));
    }

    @Test
     void Delete() {

        Deposit depositMono = Deposit.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .depositAmount(BigDecimal.valueOf(200))
                .description("demo 1")
                .transactionId("234234jnjk2344")
                .build();

        String id = "unhb2342342342";

        Mockito.when(depositRepository.findById("unhb2342342342")).thenReturn(Mono.just(depositMono));
        Mockito.when(depositRepository.delete(depositMono)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> depositServiceImpl.delete(id)
                .subscribe(response -> {
                    assertEquals(new Deposit(), response);
                }));

    }
}
