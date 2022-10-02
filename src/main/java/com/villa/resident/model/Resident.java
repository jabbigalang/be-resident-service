package com.villa.resident.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("resident")
public class Resident {
    @Id
    private String id;
    private String houseId;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String emailAddress;
    private String mobileNumber;
}
