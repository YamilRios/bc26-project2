package com.proyecto1.product.controller;

import com.proyecto1.product.entity.Product;
import com.proyecto1.product.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller for Product.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

  /**
   * Looger for class Product.
   */
  private static final Logger LOG = LogManager
          .getLogger(ProductController.class);

  /**
   * Injection dependency ProductService.
   */
  @Autowired
  private ProductService productService;

  /**
   * Method for return all products.
   * @return Flux of products
   */
  @GetMapping("/findAll")
  public Flux<Product> getCustomers() {
    LOG.info("Service call FindAll - product");
    return productService.findAll();
  }

  @GetMapping("/find/{id}")
  public Mono<Product> getCustomer(@PathVariable String id) {
    LOG.info("Service call FindById - product");
    return productService.findById(id);
  }

  @PostMapping("/create")
  public Mono<Product> createCustomer(@RequestBody Product p) {
    LOG.info("Service call create - product");
    return productService.create(p);
  }

  @PutMapping("/update/{id}")
  public Mono<Product> updateCustomer(@RequestBody Product p, @PathVariable String id) {
    LOG.info("Service call Update - product");
    return productService.update(p, id);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<Product> deleteCustomer(@PathVariable String id) {
    LOG.info("Service call delete - Product");
    return productService.delete(id);
  }
}
