package com.proyecto1.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String docNumber;
    private int typeCustomer;
    private String descTypeCustomer;
}
