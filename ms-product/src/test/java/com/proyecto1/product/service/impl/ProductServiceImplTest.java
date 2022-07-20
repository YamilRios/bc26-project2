package com.proyecto1.product.service.impl;

import com.proyecto1.product.entity.Product;
import com.proyecto1.product.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
     void createDepositTest() {
        Product productMono = Product.builder()
                .id(ObjectId.get().toString())
                .indProduct(1)
                .descIndProduct("cuenta bancaria")
                .typeProduct(1)
                .descTypeProduct("cuenta de ahorro")
                .build();

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(Mono.just(productMono));

        assertDoesNotThrow(() -> productServiceImpl.create(productMono)
                .subscribe(response -> {
                    assertEquals(productMono.getIndProduct(), response.getIndProduct());
                }));
    }

    @Test
     void updateDepositTest() {
        Product productMono = Product.builder()
                .id(ObjectId.get().toString())
                .indProduct(1)
                .descIndProduct("cuenta bancaria")
                .typeProduct(1)
                .descTypeProduct("cuenta de ahorro")
                .build();
        String id = "6767668789fds9";
        Mockito.when(productRepository.findById(id)).thenReturn(Mono.just(productMono));
        Mockito.when(productRepository.save(productMono)).thenReturn(Mono.just(productMono));


        assertDoesNotThrow(() -> productServiceImpl.update(productMono, id)
                .subscribe(response -> {
                    assertEquals(productMono.getIndProduct(), response.getIndProduct());
                }));
    }

    @Test
     void findAll() {
        Product productMono = Product.builder()
                .id(ObjectId.get().toString())
                .indProduct(1)
                .descIndProduct("cuenta bancaria")
                .typeProduct(1)
                .descTypeProduct("cuenta de ahorro")
                .build();

        Mockito.when(productRepository.findAll()).thenReturn(Flux.just(productMono));

        assertDoesNotThrow(() -> productServiceImpl.findAll()
                .subscribe(response -> {
                    assertEquals(productMono.getIndProduct(), response.getIndProduct());
                }));
    }
    @Test
     void FindById() {

        Product product = new Product();
        product.setId("12buhvg24uhjknv2");
        product.setIndProduct(1);
        product.setDescIndProduct("cuenta bancaria");
        product.setTypeProduct(1);
        product.setDescTypeProduct("cuenta de ahorro");

        Mockito.when(productRepository.findById("12buhvg24uhjknv2")).thenReturn(Mono.just(product));

        assertDoesNotThrow(() -> productServiceImpl.findById(product.getId())
                .subscribe(response -> {
                    assertEquals(product.getIndProduct(), response.getIndProduct());
                }));
    }

    @Test
     void Delete() {

        Product productMono = Product.builder()
                .id(ObjectId.get().toString())
                .indProduct(1)
                .descIndProduct("cuenta bancaria")
                .typeProduct(1)
                .descTypeProduct("cuenta de ahorro")
                .build();
        String id = "unhb2342342342";

        Mockito.when(productRepository.findById("unhb2342342342")).thenReturn(Mono.just(productMono));
        Mockito.when(productRepository.delete(productMono)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> productServiceImpl.delete(id)
                .subscribe(response -> {
                    assertEquals(new Product(), response);
                }));

    }
}
