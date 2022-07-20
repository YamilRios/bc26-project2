package com.proyecto1.transaction;

import com.proyecto1.transaction.controller.TransactionController;
import com.proyecto1.transaction.entity.Customer;
import com.proyecto1.transaction.entity.Deposit;
import com.proyecto1.transaction.entity.Product;
import com.proyecto1.transaction.entity.Transaction;
import com.proyecto1.transaction.service.TransactionService;
import com.proyecto1.transaction.service.impl.TransacionServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@SpringBootTest
class TransactionApplicationTests {
	@Test
	void contextLoads() {
	}


}
