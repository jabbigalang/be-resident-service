package com.villa.resident.controller;

import com.villa.resident.model.Resident;
import com.villa.resident.service.ResidentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/residents")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ResidentController {
    private ResidentService residentService;

    @GetMapping
    public ResponseEntity<List<Resident>> getResidents() {
        return new ResponseEntity<>(residentService.getResidents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resident> getResident(@PathVariable String id) {
        return new ResponseEntity<>(residentService.getResident(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Resident> createResident(@RequestBody Resident resident) {
        return new ResponseEntity<>(residentService.createResident(resident), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resident> updateResident(@PathVariable String id, @RequestBody Resident resident) {
        return new ResponseEntity<>(residentService.updateResident(id, resident), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResident(@PathVariable String id) {
        residentService.deleteResident(id);
        return new ResponseEntity<>("Resident deleted", HttpStatus.OK);
    }

}
