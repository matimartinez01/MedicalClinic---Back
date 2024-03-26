package ClinicMindhub.Medical.Clinic.services.implService;

import ClinicMindhub.Medical.Clinic.dto.DoctorDTO;
import ClinicMindhub.Medical.Clinic.models.Doctor;
import ClinicMindhub.Medical.Clinic.repositories.DoctorRepository;
import ClinicMindhub.Medical.Clinic.services.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private DoctorRepository doctorRepository;
    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public List<DoctorDTO> getAllDoctorsDTO() {
        return getAllDoctors().stream().map(DoctorDTO::new).toList();
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Override
    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}
