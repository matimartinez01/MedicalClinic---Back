package ClinicMindhub.Medical.Clinic.services;

import ClinicMindhub.Medical.Clinic.dto.DoctorDTO;
import ClinicMindhub.Medical.Clinic.models.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> getAllDoctors();
    List<DoctorDTO> getAllDoctorsDTO();
    Doctor getDoctorById(Long id);
    Doctor getDoctorByEmail(String email);
    Doctor saveDoctor(Doctor doctor);


}
