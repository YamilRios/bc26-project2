package com.proyecto1.signatory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "schema_sig.signatories")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Signatory {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String docNumber;
    private String transactionId;
}
