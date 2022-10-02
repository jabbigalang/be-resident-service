package com.villa.resident.service;

import com.villa.resident.exception.ResidentNotCreatedException;
import com.villa.resident.exception.ResidentNotDeletedException;
import com.villa.resident.exception.ResidentNotFoundException;
import com.villa.resident.exception.ResidentNotUpdatedException;
import com.villa.resident.model.Resident;
import com.villa.resident.repository.ResidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.villa.resident.testutil.DataUtils.getResident;
import static com.villa.resident.testutil.DataUtils.getResidentList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ResidentServiceTest {

    private ResidentRepository residentRepository;

    private ResidentServiceImpl residentService;

    @BeforeEach
    public void init() {
        residentRepository = Mockito.mock(ResidentRepository.class);

        residentService = new ResidentServiceImpl(residentRepository);
    }

    @Test
    public void getResidents_Success() throws Exception {
        when(residentRepository.findAll()).thenReturn(getResidentList());

        List<Resident> residents = residentService.getResidents();

        assertThat(residents.size()).isEqualTo(4);
    }

    @Test
    public void getResidents_NoneFound() throws Exception {
        when(residentRepository.findAll()).thenReturn(null);
        assertThrows(ResidentNotFoundException.class, () -> residentService.getResidents());

        when(residentRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ResidentNotFoundException.class, () -> residentService.getResidents());
    }

    @Test
    public void getResident_Success() throws Exception {
        when(residentRepository.findById(anyString())).thenReturn(Optional.of(getResident()));

        Resident resident = residentService.getResident("1");

        assertThat(resident.getId().equals("1"));
        assertThat(resident.getFirstname().equals("jabbi"));
    }

    @Test
    public void getResident_NoneFound() throws Exception {
        assertThrows(ResidentNotFoundException.class, () -> residentService.getResident("1"));
    }

    @Test
    public void createResident_Success() throws Exception {
        when(residentRepository.save(any(Resident.class))).thenReturn(getResident());

        Resident resident = residentService.createResident(getResident());

        assertThat(resident.getId().equals("1"));
        assertThat(resident.getFirstname().equals("jabbi"));
    }

    @Test
    public void createResident_NotCreated() throws Exception {
        when(residentRepository.save(any(Resident.class))).thenThrow(new RuntimeException());

        assertThrows(ResidentNotCreatedException.class, () -> residentService.createResident(getResident()));
    }

    @Test
    public void updateResident_Success() throws Exception {
        when(residentRepository.findById(anyString())).thenReturn(Optional.of(getResident()));
        when(residentRepository.save(any(Resident.class))).thenReturn(getResident());

        Resident resident = residentService.updateResident("1", getResident());

        assertThat(resident.getId().equals("1"));
        assertThat(resident.getFirstname().equals("jabbi"));
    }

    @Test
    public void updateResident_NotFound() throws Exception {
        when(residentRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResidentNotFoundException.class, () -> residentService.updateResident("1", getResident()));
    }


    @Test
    public void updateResident_NotCreated() throws Exception {
        when(residentRepository.findById(anyString())).thenReturn(Optional.of(getResident()));
        when(residentRepository.save(any(Resident.class))).thenThrow(new RuntimeException());

        assertThrows(ResidentNotUpdatedException.class, () -> residentService.updateResident("1", getResident()));
    }

    @Test
    public void deleteResident_Success() throws Exception {
        when(residentRepository.findById(anyString())).thenReturn(Optional.of(getResident()));

        residentService.deleteResident("1");

        verify(residentRepository, times(1)).deleteById(anyString());
    }

    @Test
    public void deleteResident_NotFound() throws Exception {
        when(residentRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResidentNotFoundException.class, () -> residentService.deleteResident("1"));
    }

    @Test
    public void deleteResident_NotDeleted() throws Exception {
        when(residentRepository.findById(anyString())).thenReturn(Optional.of(getResident()));
        doThrow(new RuntimeException()).when(residentRepository).deleteById(anyString());

        assertThrows(ResidentNotDeletedException.class, () -> residentService.deleteResident("1"));
    }

}
