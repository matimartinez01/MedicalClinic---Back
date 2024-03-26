package ClinicMindhub.Medical.Clinic.controllers;

import ClinicMindhub.Medical.Clinic.dto.AppointmentDTO;
import ClinicMindhub.Medical.Clinic.dto.AppointmentRequestDTO;
import ClinicMindhub.Medical.Clinic.dto.AppointmentRequestDTOAdmin;
import ClinicMindhub.Medical.Clinic.dto.RegisterAdminDTO;
import ClinicMindhub.Medical.Clinic.models.Admin;
import ClinicMindhub.Medical.Clinic.models.Appointment;
import ClinicMindhub.Medical.Clinic.models.Doctor;
import ClinicMindhub.Medical.Clinic.models.Patient;
import ClinicMindhub.Medical.Clinic.repositories.AdminRepository;
import ClinicMindhub.Medical.Clinic.repositories.AppointmentRepository;
import ClinicMindhub.Medical.Clinic.repositories.DoctorRepository;
import ClinicMindhub.Medical.Clinic.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @PostMapping("/register")
    public ResponseEntity<?> addAdmin(@RequestBody RegisterAdminDTO registerAdminDTO){

        if(registerAdminDTO.email().isBlank()) {
            return new ResponseEntity<>("You have to complete the field email", HttpStatus.FORBIDDEN);
        }

        if(registerAdminDTO.firstName().isBlank()) {
            return new ResponseEntity<>("You have to complete the field firstName", HttpStatus.FORBIDDEN);
        }

        if(registerAdminDTO.lastName().isBlank()) {
            return new ResponseEntity<>("You have to complete the field lastName", HttpStatus.FORBIDDEN);
        }

        if(registerAdminDTO.password().isBlank()) {
            return new ResponseEntity<>("You have to complete the field password", HttpStatus.FORBIDDEN);
        }

        if(!registerAdminDTO.email().contains("@admin")){
            return new ResponseEntity<>("The email has to contain @admin", HttpStatus.FORBIDDEN);
        }

        if(adminRepository.findByEmail(registerAdminDTO.email()) != null){
            return new ResponseEntity<>("The email is already registered", HttpStatus.FORBIDDEN);
        }

        if(registerAdminDTO.password().length() < 6){
            return new ResponseEntity<>("The password needs be at least 6 characters length", HttpStatus.FORBIDDEN);
        }

        Admin admin = new Admin(registerAdminDTO.firstName(), registerAdminDTO.lastName(), registerAdminDTO.email(), registerAdminDTO.password());

        adminRepository.save(admin);

        return new ResponseEntity<>(admin, HttpStatus.CREATED);
    }

    @PostMapping("/appointment")
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentRequestDTOAdmin appointmentDTO){

        Doctor doctor = doctorRepository.findByEmail(appointmentDTO.emailDoctor());
        Patient patient = patientRepository.findByEmail(appointmentDTO.emailPatient());


        if(appointmentDTO.emailDoctor().isBlank()){
            return new ResponseEntity<>("You have to complete the field firstName", HttpStatus.FORBIDDEN);
        }

        if(appointmentDTO.emailPatient().isBlank()){
            return new ResponseEntity<>("You have to complete the field lastName", HttpStatus.FORBIDDEN);
        }

        if(appointmentDTO.time() == null){
            return new ResponseEntity<>("You have to complete the field time", HttpStatus.FORBIDDEN);
        }

        if(appointmentDTO.date() == null){
            return new ResponseEntity<>("You have to complete the field date", HttpStatus.FORBIDDEN);
        }

        if(doctor == null){
            return new ResponseEntity<>("There isn't any doctor with that email", HttpStatus.FORBIDDEN);
        }

        if(patient == null){
            return new ResponseEntity<>("There isn't any patient with that email", HttpStatus.FORBIDDEN);
        }

        if(appointmentDTO.date().isBefore(LocalDate.now())){
            return new ResponseEntity<>("You can't request an appointment in a past date", HttpStatus.FORBIDDEN);
        }

        if(appointmentDTO.date().equals(LocalDate.now()) && appointmentDTO.time() <= LocalDateTime.now().getHour()){
            return new ResponseEntity<>("You can't request an appointment in a past time???", HttpStatus.FORBIDDEN);
        }

        boolean workAtThisTime = doctor.getHours().contains(appointmentDTO.time());
        boolean workOnThisDay = doctor.getWorkDays().contains(appointmentDTO.date().getDayOfWeek().toString());

        if (!workOnThisDay){
            return new ResponseEntity<>("The doctor doesn't work on " + appointmentDTO.date().getDayOfWeek(), HttpStatus.FORBIDDEN);
        }

        if (!workAtThisTime){
            return new ResponseEntity<>("The doctor doesn't work at " + appointmentDTO.time(), HttpStatus.FORBIDDEN);
        }

        if(appointmentRepository.existsByDoctorAndDateAndTime(doctor, appointmentDTO.date(), appointmentDTO.time())){
            return new ResponseEntity<>("There isn't appointment available in " + appointmentDTO.date() + " at " +
                    appointmentDTO.time() + "hs with the doctor " + doctor.getFirstName() + " " + doctor.getLastName(), HttpStatus.OK);
        }



        Appointment appointment = new Appointment(appointmentDTO.date(), appointmentDTO.time());

        doctor.addAppointment(appointment);
        patient.addAppointment(appointment);

        patientRepository.save(patient);
        doctorRepository.save(doctor);
        appointmentRepository.save(appointment);



        return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.OK);
    }



    @GetMapping("/all")
    public ResponseEntity<?> getAdmins(){
        List<Admin> admins = adminRepository.findAll();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }


}
