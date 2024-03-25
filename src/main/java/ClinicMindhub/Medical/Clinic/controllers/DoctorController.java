package ClinicMindhub.Medical.Clinic.controllers;


import ClinicMindhub.Medical.Clinic.dto.DoctorDTO;
import ClinicMindhub.Medical.Clinic.models.Doctor;
import ClinicMindhub.Medical.Clinic.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @RequestMapping("/all")
    public List<DoctorDTO> getAll() {
        return doctorService.getAllDoctorsDTO();
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
}
