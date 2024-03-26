package ClinicMindhub.Medical.Clinic.controllers;

import ClinicMindhub.Medical.Clinic.dto.PatientDTO;
import ClinicMindhub.Medical.Clinic.dto.RegisterDTO;
import ClinicMindhub.Medical.Clinic.dto.RegisterDTO;
import ClinicMindhub.Medical.Clinic.models.Patient;
import ClinicMindhub.Medical.Clinic.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    PatientRepository patientRepository;

    @PostMapping("/register")
    public ResponseEntity<?> addPatient(@RequestBody RegisterDTO registerDTO){


        if (registerDTO.firstName().isBlank()){
            return new ResponseEntity<>("You have to complete the field firstName", HttpStatus.FORBIDDEN);
        }

        if (registerDTO.lastName().isBlank()){
            return new ResponseEntity<>("You have to complete the field lastName", HttpStatus.FORBIDDEN);
        }

        if (registerDTO.email().isBlank()){
            return new ResponseEntity<>("You have to complete the field email", HttpStatus.FORBIDDEN);
        }

        if (registerDTO.password().isBlank()){
            return new ResponseEntity<>("You have to complete the field password", HttpStatus.FORBIDDEN);
        }

        if (registerDTO.genre() == null){
            return new ResponseEntity<>("You have to complete the field genre", HttpStatus.FORBIDDEN);
        }

        if (registerDTO.birthDate() == null){
            return new ResponseEntity<>("You have to complete the field birthDate", HttpStatus.FORBIDDEN);
        }

        if(!registerDTO.email().contains("@")){
            return new ResponseEntity<>("You have to enter an email", HttpStatus.FORBIDDEN);
        }

        if(patientRepository.findByEmail(registerDTO.email()) != null){
            return new ResponseEntity<>("There is a patient with that email", HttpStatus.FORBIDDEN);
        }

        Period period = Period.between(registerDTO.birthDate(), LocalDate.now());
        if(period.getYears() < 15){
            return new ResponseEntity<>("You have to be greater than 18 years old to create an account", HttpStatus.FORBIDDEN);
        }

        if(registerDTO.password().length() < 6){
            return new ResponseEntity<>("The password needs at least 6 characters", HttpStatus.FORBIDDEN);
        }

        Patient patient = new Patient(registerDTO.firstName(), registerDTO.lastName(), registerDTO.email(), registerDTO.password(), registerDTO.genre(), registerDTO.birthDate());

        patientRepository.save(patient);

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }






}
