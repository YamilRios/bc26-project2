package com.proyecto1.payment.service.impl;

import com.proyecto1.payment.client.TransactionClient;
import com.proyecto1.payment.entity.Payment;
import com.proyecto1.payment.entity.Product;
import com.proyecto1.payment.entity.Transaction;
import com.proyecto1.payment.repository.PaymentRepository;
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
public class PaymentServiceImplTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private TransactionClient transactionClient;

    @InjectMocks
    private PaymentServiceImpl paymentServiceImpl;

    @Test
     void createDepositTest() {
        Payment paymentMono = Payment.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .paymentAmount(BigDecimal.valueOf(200))
                .description("demo payment")
                .transactionId("34242423234")
                .build();

        Product product = new Product();
        product.setId("83457346534534");
        product.setIndProduct(1);
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
        Mockito.when(paymentRepository.save(Mockito.any())).thenReturn(Mono.just(paymentMono));

        assertDoesNotThrow(() -> paymentServiceImpl.create(paymentMono)
                .subscribe(response -> {
                    assertEquals(paymentMono.getPaymentAmount(), response.getPaymentAmount());
                }));
    }

    @Test
     void updateDepositTest() {
        Payment paymentMono = Payment.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .paymentAmount(BigDecimal.valueOf(200))
                .description("demo payment")
                .transactionId("234234jnjk2345")
                .build();

        String id = "6767668789fds9";

        Mockito.when(paymentRepository.findById(id)).thenReturn(Mono.just(paymentMono));
        Mockito.when(paymentRepository.save(paymentMono)).thenReturn(Mono.just(paymentMono));

        assertDoesNotThrow(() -> paymentServiceImpl.update(paymentMono,id)
                .subscribe(response -> {
                    assertEquals(paymentMono.getPaymentAmount(), response.getPaymentAmount());
                }));
    }

    @Test
     void findAll() {
        Payment paymentMono = Payment.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .paymentAmount(BigDecimal.valueOf(200))
                .description("demo payment")
                .transactionId("234234jnjk2345")
                .build();

        Mockito.when(paymentRepository.findAll()).thenReturn(Flux.just(paymentMono));

        assertDoesNotThrow(() -> paymentServiceImpl.findAll()
                .subscribe(response -> {
                    assertEquals(paymentMono.getPaymentAmount(), response.getPaymentAmount());
                }));
    }

    @Test
    void FindById() {

        Payment payment = new Payment();
        payment.setId("12buhvg24uhjknv2");
        payment.setDate(LocalDate.now());
        payment.setPaymentAmount(BigDecimal.valueOf(200));
        payment.setDescription("demo2");
        payment.setTransactionId("2323423424");

        Mockito.when(paymentRepository.findById("12buhvg24uhjknv2")).thenReturn(Mono.just(payment));

        assertDoesNotThrow(() -> paymentServiceImpl.findById(payment.getId())
                .subscribe(response -> {
                    assertEquals(payment.getPaymentAmount(), response.getPaymentAmount());
                }));
    }

    @Test
     void Delete() {

        Payment paymentMono = Payment.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .paymentAmount(BigDecimal.valueOf(200))
                .description("demo payment")
                .transactionId("234234jnjk2345")
                .build();
        String id = "6767668789fds9";

        Mockito.when(paymentRepository.findById("6767668789fds9")).thenReturn(Mono.just(paymentMono));
        Mockito.when(paymentRepository.delete(paymentMono)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> paymentServiceImpl.delete(id)
                .subscribe(response -> {
                    assertEquals(new Payment(), response);
                }));

    }
}
