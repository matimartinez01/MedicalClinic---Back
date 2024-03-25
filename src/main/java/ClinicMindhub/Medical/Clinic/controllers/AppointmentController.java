package ClinicMindhub.Medical.Clinic.controllers;

import ClinicMindhub.Medical.Clinic.dto.AppointmentDTO;
import ClinicMindhub.Medical.Clinic.dto.AppointmentRequestDTO;
import ClinicMindhub.Medical.Clinic.dto.DoctorDTO;
import ClinicMindhub.Medical.Clinic.models.Appointment;
import ClinicMindhub.Medical.Clinic.models.Doctor;
import ClinicMindhub.Medical.Clinic.models.Patient;
import ClinicMindhub.Medical.Clinic.repositories.AppointmentRepository;
import ClinicMindhub.Medical.Clinic.repositories.DoctorRepository;
import ClinicMindhub.Medical.Clinic.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;


    @PostMapping("/")
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentRequestDTO appointmentDTO){

        Doctor doctor = doctorRepository.findByFirstNameAndLastName(appointmentDTO.firstName(), appointmentDTO.lastName());
        Patient patient = patientRepository.findById(appointmentDTO.patientId()).orElse(null);


        if(appointmentDTO.firstName().isBlank()){
            return new ResponseEntity<>("You have to complete the field firstName", HttpStatus.FORBIDDEN);
        }

        if(appointmentDTO.lastName().isBlank()){
            return new ResponseEntity<>("You have to complete the field lastName", HttpStatus.FORBIDDEN);
        }

        if(appointmentDTO.time() == null){
            return new ResponseEntity<>("You have to complete the field time", HttpStatus.FORBIDDEN);
        }

        if(appointmentDTO.date() == null){
            return new ResponseEntity<>("You have to complete the field date", HttpStatus.FORBIDDEN);
        }

        if(doctor == null){
            return new ResponseEntity<>("There isn't any doctor with that name", HttpStatus.FORBIDDEN);
        }

        if(patient == null){
            return new ResponseEntity<>("There isn't any patient with that id", HttpStatus.FORBIDDEN);
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
                    appointmentDTO.time() + "hs with the doctor " + appointmentDTO.firstName() + " " + appointmentDTO.lastName(), HttpStatus.OK);
        }



        Appointment appointment = new Appointment(appointmentDTO.date(), appointmentDTO.time());

        doctor.addAppointment(appointment);
        patient.addAppointment(appointment);

        patientRepository.save(patient);
        doctorRepository.save(doctor);
        appointmentRepository.save(appointment);



        return new ResponseEntity<>(new DoctorDTO(doctor), HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<?> getAppointment(){
        List<AppointmentDTO> appointments = appointmentRepository.findAll().stream().map(AppointmentDTO::new).toList();

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

}
