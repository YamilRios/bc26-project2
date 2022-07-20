package com.proyecto1.transaction.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
public class Signatory {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String docNumber;
    private String transactionId;
}
