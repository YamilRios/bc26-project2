package com.proyecto1.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	private String id;
    private int indProduct;
    private String descIndProduct;
    private int typeProduct;
    private String descTypeProduct;
}
