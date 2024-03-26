package ClinicMindhub.Medical.Clinic.services.implService;

import ClinicMindhub.Medical.Clinic.dto.PatientDTO;
import ClinicMindhub.Medical.Clinic.models.Patient;
import ClinicMindhub.Medical.Clinic.repositories.PatientRepository;
import ClinicMindhub.Medical.Clinic.services.PatientService;

import java.util.List;

public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;
    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<PatientDTO> getAllPatientsDTO() {
        return getAllPatients().stream().map(PatientDTO::new).toList();
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }
}
