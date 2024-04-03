package ClinicMindhub.Medical.Clinic.controllers;

import ClinicMindhub.Medical.Clinic.SecurityServices.JwtUtilService;
import ClinicMindhub.Medical.Clinic.dto.*;
import ClinicMindhub.Medical.Clinic.dto.RegisterDTO;
import ClinicMindhub.Medical.Clinic.models.Admin;
import ClinicMindhub.Medical.Clinic.models.Doctor;
import ClinicMindhub.Medical.Clinic.models.Patient;
import ClinicMindhub.Medical.Clinic.repositories.AdminRepository;
import ClinicMindhub.Medical.Clinic.repositories.AppointmentRepository;
import ClinicMindhub.Medical.Clinic.repositories.DoctorRepository;
import ClinicMindhub.Medical.Clinic.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtilService jwtUtilService;

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPatients(){

        List<PatientDTO> patients = patientRepository.findAll().stream().map(PatientDTO::new).toList();

        return new ResponseEntity <>(patients, HttpStatus.OK);
    }

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

        if(registerDTO.email().contains("@doctor")){
            return new ResponseEntity<>("You can't use that email", HttpStatus.FORBIDDEN);
        }

        if(registerDTO.email().contains("@admin")){
            return new ResponseEntity<>("You can't use that email", HttpStatus.FORBIDDEN);
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

        Patient patient = new Patient(registerDTO.firstName(), registerDTO.lastName(), registerDTO.email(), passwordEncoder.encode(registerDTO.password()), registerDTO.genre(), registerDTO.birthDate());

        patientRepository.save(patient);

        PatientDTO patientDTO = new PatientDTO(patient);

        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDTO loginDTO) {
        try {
            if(loginDTO.email().contains("@doctor")){
                Doctor doctor = doctorRepository.findByEmail(loginDTO.email());

                if (doctor == null) {
                    return new ResponseEntity<>("The entered email or password is not valid", HttpStatus.FORBIDDEN);
                }

                if(!passwordEncoder.matches(loginDTO.password(), doctor.getPassword())) {
                    return new ResponseEntity<>("The entered email or password is not valid", HttpStatus.FORBIDDEN);
                }

                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
                final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
                final String jwt = jwtUtilService.generateToken(userDetails);
                return ResponseEntity.ok(jwt);
            }

            if(loginDTO.email().contains("@admin")){
                Admin admin = adminRepository.findByEmail(loginDTO.email());

                if (admin == null) {
                    return new ResponseEntity<>("The entered email or password is not valid", HttpStatus.FORBIDDEN);
                }

                if(!passwordEncoder.matches(loginDTO.password(), admin.getPassword())) {
                    return new ResponseEntity<>("The entered email or password is not valid", HttpStatus.FORBIDDEN);
                }

                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
                final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
                final String jwt = jwtUtilService.generateToken(userDetails);
                return ResponseEntity.ok(jwt);
            }

            Patient patient = patientRepository.findByEmail(loginDTO.email());

            if (patient == null) {
                return new ResponseEntity<>("The entered email or password is not valid", HttpStatus.FORBIDDEN);
            }

            if(!passwordEncoder.matches(loginDTO.password(), patient.getPassword())) {
                return new ResponseEntity<>("The entered email or password is not valid", HttpStatus.FORBIDDEN);
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
            final String jwt = jwtUtilService.generateToken(userDetails);
            return ResponseEntity.ok(jwt);

        }
        catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<?> getPatient(){
        String patientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = patientRepository.findByEmail(patientEmail);

        return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAppointment")
    public ResponseEntity<?> deleteAppointment(@RequestBody DeleteAppointmentDTO deleteAppointmentDTO){
        appointmentRepository.deleteById(deleteAppointmentDTO.id());
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
