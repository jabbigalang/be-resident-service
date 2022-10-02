package com.villa.resident.service;

import com.villa.resident.exception.ResidentNotCreatedException;
import com.villa.resident.exception.ResidentNotDeletedException;
import com.villa.resident.exception.ResidentNotFoundException;
import com.villa.resident.exception.ResidentNotUpdatedException;
import com.villa.resident.model.Resident;
import com.villa.resident.repository.ResidentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class ResidentServiceImpl implements ResidentService {

    private ResidentRepository residentRepository;

    @Override
    public List<Resident> getResidents() {
        List<Resident> residents = residentRepository.findAll();

        if (residents == null || residents.size() == 0) throw new ResidentNotFoundException("No resident found.");

        return residentRepository.findAll();
    }

    @Override
    public Resident getResident(String id) {
        Optional<Resident> optionalResident = residentRepository.findById(id);

        if (optionalResident.isEmpty()) throw new ResidentNotFoundException("No resident found.");

        return optionalResident.get();
    }

    @Override
    public Resident createResident(Resident resident) {
        Resident createdResident;

        try {
            createdResident = residentRepository.save(resident);
        } catch (Exception exception) {
            throw new ResidentNotCreatedException("Resident not created.");
        }

        return createdResident;
    }

    @Override
    public Resident updateResident(String id, Resident resident) {
        Resident updatedResident;
        Resident existingResident = getResident(id);

        try {
            resident.setId(existingResident.getId());
            updatedResident = residentRepository.save(resident);
        } catch (Exception exception) {
            throw new ResidentNotUpdatedException("Resident not update.");
        }

        return updatedResident;
    }

    @Override
    public void deleteResident(String id) {
        Resident existingResident = getResident(id);

        try {
            residentRepository.deleteById(id);
        } catch (Exception exception) {
            throw new ResidentNotDeletedException("Resident not deleted.");
        }
    }
}
