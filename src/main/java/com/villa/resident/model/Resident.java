package com.villa.resident.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resident {
    private String id;
    private String houseId;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String emailAddress;
    private String mobileNumber;
}
