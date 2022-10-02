package com.villa.resident.service;

import com.villa.resident.model.Resident;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface ResidentService {

    List<Resident> getResidents();

    Resident getResident(String id);

    Resident createResident(Resident resident);

    Resident updateResident(String id, Resident resident);

    void deleteResident(String id);

}
