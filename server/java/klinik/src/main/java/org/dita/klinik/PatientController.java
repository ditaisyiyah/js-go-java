package org.dita.klinik;
 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@RequestMapping("/api")
public class PatientController {
 
    @Autowired
    private PatientRepository patientRepository;
 
    @CrossOrigin(origins = "http://localhost:8082")
    @PostMapping("/patients")
    public ResponseEntity<Patient> create(
            @RequestBody Patient patient) {
        try {
            Patient newPatient = new Patient();
            newPatient.setName(patient.getName());
            newPatient.setBirthDate(patient.getBirthDate());
            newPatient.setPhoneNumber(patient.getPhoneNumber());
            return new ResponseEntity<>(patientRepository.save(newPatient), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @CrossOrigin(origins = "http://localhost:8082")
    @PutMapping("/patients/{id}")
    public ResponseEntity<Patient> update(
            @PathVariable("id") String id,
            @RequestBody(required = false) Patient patient) {
        
        Optional<Patient> patientData = patientRepository.findById(Long.parseLong(id));
        
        if (patientData.isPresent()) {
            // System.out.println("PUT HALOO");
            // System.out.println(patient.getName());
            Patient updatedPatient = patientData.get();
            updatedPatient.setName(patient.getName());
            updatedPatient.setBirthDate(patient.getBirthDate());
            updatedPatient.setPhoneNumber(patient.getPhoneNumber());
            return new ResponseEntity<>(patientRepository.save(updatedPatient), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 
    @CrossOrigin(origins = "http://localhost:8082")
    @DeleteMapping("/patients/{id}")
    public ResponseEntity<HttpStatus> delete(
            @PathVariable("id") Long id) {
        try {
            patientRepository.deleteById(id);
            // patientRepository.deleteAllById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}