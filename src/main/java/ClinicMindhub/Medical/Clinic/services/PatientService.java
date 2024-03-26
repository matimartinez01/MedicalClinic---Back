package ClinicMindhub.Medical.Clinic.services;

import ClinicMindhub.Medical.Clinic.dto.PatientDTO;
import ClinicMindhub.Medical.Clinic.models.Patient;

import java.util.List;

public interface PatientService {
    public List<Patient> getAllPatients();
    public List<PatientDTO> getAllPatientsDTO();

    public Patient getPatientById(Long id);

    public Patient getPatientByEmail(String email);
    public Patient savePatient(Patient patient);
}
