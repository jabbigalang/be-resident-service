package com.villa.resident.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.villa.resident.exception.ResidentNotCreatedException;
import com.villa.resident.exception.ResidentNotDeletedException;
import com.villa.resident.exception.ResidentNotFoundException;
import com.villa.resident.exception.ResidentNotUpdatedException;
import com.villa.resident.model.Resident;
import com.villa.resident.service.ResidentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.List;
import static com.villa.resident.testutil.DataUtils.getResident;
import static com.villa.resident.testutil.DataUtils.getResidentList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureWebMvc
@WebMvcTest
public class ResidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResidentService residentService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAllResidentsTest_Success() throws Exception {
        when(residentService.getResidents()).thenReturn(getResidentList());

        MvcResult mvcResult = mockMvc.perform(get("/residents")).andReturn();

        List<Resident> residentList = mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(residentList.size()).isEqualTo(4);
        assertThat(residentList.equals(getResidentList()));
    }

    @Test
    public void getAllResidentsTest_NotFound() throws Exception {
        when(residentService.getResidents()).thenThrow(new ResidentNotFoundException("No resident found."));

        MvcResult mvcResult = mockMvc.perform(get("/residents")).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("No resident found.");
    }

    @Test
    public void getResidentTest_Success() throws Exception {
        when(residentService.getResident(anyString())).thenReturn(getResident());

        MvcResult mvcResult = mockMvc.perform(get("/residents/1")).andReturn();

        Resident resident = mapper.readValue(mvcResult.getResponse().getContentAsString(), Resident.class);

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(resident.getId().equals("1"));
        assertThat(resident.getFirstname().equals("jabbi"));
    }

    @Test
    public void getResidentTest_NotFound() throws Exception {
        when(residentService.getResident(anyString())).thenThrow(new ResidentNotFoundException("No resident found."));

        MvcResult mvcResult = mockMvc.perform(get("/residents/1")).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("No resident found.");
    }

    @Test
    public void createResidentTest_Success() throws Exception {
        when(residentService.createResident(any(Resident.class))).thenReturn(getResident());

        MvcResult mvcResult = mockMvc.perform(
                post("/residents")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\": \"1\", \"firstname\": \"jabbi\"}")).andReturn();

        Resident resident = mapper.readValue(mvcResult.getResponse().getContentAsString(), Resident.class);

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(201);
        assertThat(resident.getId().equals("1"));
        assertThat(resident.getFirstname().equals("jabbi"));
    }

    @Test
    public void createResidentTest_NotSuccessful() throws Exception {
        when(residentService.createResident(any(Resident.class))).thenThrow(new ResidentNotCreatedException("Resident was not created."));

        MvcResult mvcResult = mockMvc.perform(
                post("/residents")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\": \"1\", \"firstname\": \"jabbi\"}")).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(500);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("Resident was not created.");
    }

    @Test
    public void updateResidentTest_Success() throws Exception {
        when(residentService.updateResident(anyString(), any(Resident.class))).thenReturn(getResident());

        MvcResult mvcResult = mockMvc.perform(
                put("/residents/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\": \"1\", \"firstname\": \"jabbi\"}")).andReturn();

        Resident resident = mapper.readValue(mvcResult.getResponse().getContentAsString(), Resident.class);

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(resident.getId().equals("1"));
        assertThat(resident.getFirstname().equals("jabbi"));
    }

    @Test
    public void updateResidentTest_NotSuccessful() throws Exception {
        when(residentService.createResident(any(Resident.class))).thenThrow(new ResidentNotUpdatedException("Resident was not updated."));

        MvcResult mvcResult = mockMvc.perform(
                post("/residents")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\": \"1\", \"firstname\": \"jabbi\"}")).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(500);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("Resident was not updated.");
    }

    @Test
    public void deleteResidentTest_Success() throws Exception {
        mockMvc.perform(delete("/residents/1"));

        verify(residentService, times(1)).deleteResident(anyString());
    }

    @Test
    public void deleteResidentTest_NotSuccessful() throws Exception {
        doThrow(new ResidentNotDeletedException("Resident not deleted.")).when(residentService).deleteResident(anyString());

        MvcResult mvcResult = mockMvc.perform(delete("/residents/1")).andReturn();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(500);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("Resident not deleted.");
    }
}
