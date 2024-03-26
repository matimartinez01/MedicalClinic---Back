package ClinicMindhub.Medical.Clinic.controllers;


import ClinicMindhub.Medical.Clinic.SecurityServices.JwtUtilService;
import ClinicMindhub.Medical.Clinic.dto.DoctorDTO;
import ClinicMindhub.Medical.Clinic.dto.LoginDTO;
import ClinicMindhub.Medical.Clinic.dto.RegisterDTO;
import ClinicMindhub.Medical.Clinic.dto.RegisterDoctorDTO;
import ClinicMindhub.Medical.Clinic.models.Doctor;
import ClinicMindhub.Medical.Clinic.models.Patient;
import ClinicMindhub.Medical.Clinic.repositories.DoctorRepository;
import ClinicMindhub.Medical.Clinic.services.DoctorService;
import jakarta.persistence.GeneratedValue;
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

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtilService jwtUtilService;

    @RequestMapping("/all")
    public List<DoctorDTO> getAll() {
        return doctorRepository.findAll().stream().map(DoctorDTO::new).toList();
    }

    @RequestMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(Long id) {
        Doctor doctor = doctorService.getDoctorById(id);

        if (doctor == null) {
            return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
        }

        DoctorDTO doctorDTO = new DoctorDTO(doctor);


        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
    }

/*    @RequestMapping("/current")
    public Doctor getCurrentDoctor() {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Doctor doctor = doctorService.getDoctorByEmail(userMail);
        return ResponseEntity.ok(new DoctorDTO(doctor));
    }*/

    @PostMapping("/register")
    public ResponseEntity<?> addPatient(@RequestBody RegisterDoctorDTO registerDoctorDTO){

        List<String> days = List.of("MONDAY", "TUESDAY", "THURSDAY", "WEDNESDAY", "FRIDAY", "SATURDAY", "SUNDAY");

        List<Boolean> daysTrue = registerDoctorDTO.workDays().stream().map(days::contains).toList();

        if (registerDoctorDTO.firstName().isBlank() || registerDoctorDTO.lastName().isBlank() ||
                registerDoctorDTO.email().isBlank() || registerDoctorDTO.password().isBlank()){
            return new ResponseEntity<>("You cant register with empty fields ", HttpStatus.FORBIDDEN);
        }

        if (registerDoctorDTO.genre() == null){
            return new ResponseEntity<>("You have to complete the field genre", HttpStatus.FORBIDDEN);
        }

        if(!registerDoctorDTO.email().contains("@doctor")){
            return new ResponseEntity<>("You have to enter an email", HttpStatus.FORBIDDEN);
        }

        if(doctorRepository.findByEmail(registerDoctorDTO.email()) != null){
            return new ResponseEntity<>("Already exist a doctor with that email", HttpStatus.FORBIDDEN);
        }

        if(registerDoctorDTO.password().length() < 6){
            return new ResponseEntity<>("The password needs be at least 6 characters long", HttpStatus.FORBIDDEN);
        }

        if (registerDoctorDTO.speciality() == null){
            return new ResponseEntity<>("You have to complete the field speciality", HttpStatus.FORBIDDEN);
        }

        if (registerDoctorDTO.workDays().isEmpty()){
            return new ResponseEntity<>("You have to complete the field workDays", HttpStatus.FORBIDDEN);
        }

        //Validar que sean dias de la semana

        if(daysTrue.contains(false)){
            return new ResponseEntity<>("You have to complete the field work days with days", HttpStatus.FORBIDDEN);

        }

        if (registerDoctorDTO.hours().isEmpty()){
            return new ResponseEntity<>("You have to complete the field workHours", HttpStatus.FORBIDDEN);
        }

        Doctor doctor = new Doctor(registerDoctorDTO.firstName(), registerDoctorDTO.lastName(), registerDoctorDTO.genre(),
                registerDoctorDTO.speciality(), registerDoctorDTO.workDays(), registerDoctorDTO.hours(), registerDoctorDTO.email(),
                passwordEncoder.encode(registerDoctorDTO.password()));

        doctorRepository.save(doctor);

        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDTO loginDTO) {
        try {
            Doctor doctor = doctorRepository.findByEmail(loginDTO.email());

            if (doctor == null) {
                return new ResponseEntity<>("The entered email is not valid", HttpStatus.FORBIDDEN);
            }

            if(!passwordEncoder.matches(loginDTO.password(), doctor.getPassword())) {
                return new ResponseEntity<>("The password entered is not valid", HttpStatus.FORBIDDEN);
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
    public ResponseEntity<?> getDoctor(){
        String doctorEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Doctor doctor = doctorRepository.findByEmail(doctorEmail);
        return new ResponseEntity<>(new DoctorDTO(doctor), HttpStatus.OK);
    }


}
