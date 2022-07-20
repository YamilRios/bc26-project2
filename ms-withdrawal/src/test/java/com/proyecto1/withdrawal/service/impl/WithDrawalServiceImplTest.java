package com.proyecto1.withdrawal.service.impl;

import com.proyecto1.withdrawal.client.TransactionClient;
import com.proyecto1.withdrawal.entity.Product;
import com.proyecto1.withdrawal.entity.Transaction;
import com.proyecto1.withdrawal.entity.Withdrawal;
import com.proyecto1.withdrawal.repository.WithdrawalRepository;
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
public class WithDrawalServiceImplTest {

    @Mock
    private WithdrawalRepository withdrawalRepository;

    @Mock
    private TransactionClient transactionClient;

    @InjectMocks
    private WithdrawalServiceImpl withdrawalServiceImpl;



    @Test
     void createDepositTest() {
        Withdrawal withdrawalMono = Withdrawal.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .withdrawalAmount(BigDecimal.valueOf(2000))
                .description("demo withdrawal")
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


        Mockito.when(transactionClient.getTransactionWithDetails(transaction.getId())).thenReturn(Mono.just(transaction));
        Mockito.when(withdrawalRepository.save(Mockito.any())).thenReturn(Mono.just(withdrawalMono));

        assertDoesNotThrow(() -> withdrawalServiceImpl.create(withdrawalMono)
                .subscribe(response -> {
                    assertEquals(withdrawalMono.getWithdrawalAmount(), response.getWithdrawalAmount());
                }));
    }
    @Test
     void updateDepositTest() {
        Withdrawal withdrawalMono = Withdrawal.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .withdrawalAmount(BigDecimal.valueOf(2000))
                .description("demo withdrawal")
                .transactionId("342873574h")
                .build();
        String id = "6767668789fds9";

        Mockito.when(withdrawalRepository.findById(id)).thenReturn(Mono.just(withdrawalMono));
        Mockito.when(withdrawalRepository.save(withdrawalMono)).thenReturn(Mono.just(withdrawalMono));

        assertDoesNotThrow(() -> withdrawalServiceImpl.update(withdrawalMono,id)
                .subscribe(response -> {
                    assertEquals(withdrawalMono.getWithdrawalAmount(), response.getWithdrawalAmount());
                }));
    }

    @Test
     void findAll() {
        Withdrawal withdrawalMono = Withdrawal.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .withdrawalAmount(BigDecimal.valueOf(2000))
                .description("demo withdrawal")
                .transactionId("342873574h")
                .build();

        Mockito.when(withdrawalRepository.findAll()).thenReturn(Flux.just(withdrawalMono));

        assertDoesNotThrow(() -> withdrawalServiceImpl.findAll()
                .subscribe(response -> {
                    assertEquals(withdrawalMono.getWithdrawalAmount(), response.getWithdrawalAmount());
                }));
    }

    @Test
     void FindById() {

        Withdrawal withdrawalMono = Withdrawal.builder()
                .id("2346723847262")
                .date(LocalDate.now())
                .withdrawalAmount(BigDecimal.valueOf(2000))
                .description("demo withdrawal")
                .transactionId("342873574h")
                .build();

        Mockito.when(withdrawalRepository.findById("2346723847262")).thenReturn(Mono.just(withdrawalMono));

        assertDoesNotThrow(() -> withdrawalServiceImpl.findById(withdrawalMono.getId())
                .subscribe(response -> {
                    assertEquals(withdrawalMono.getWithdrawalAmount(), response.getWithdrawalAmount());
                }));
    }

    @Test
     void Delete() {

        Withdrawal withdrawalMono = Withdrawal.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .withdrawalAmount(BigDecimal.valueOf(2000))
                .description("demo withdrawal")
                .transactionId("342873574h")
                .build();
        String id = "6767668789fds9";

        Mockito.when(withdrawalRepository.findById("6767668789fds9")).thenReturn(Mono.just(withdrawalMono));
        Mockito.when(withdrawalRepository.delete(withdrawalMono)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> withdrawalServiceImpl.delete(id)
                .subscribe(response -> {
                    assertEquals(new Withdrawal(), response);
                }));

    }
}
