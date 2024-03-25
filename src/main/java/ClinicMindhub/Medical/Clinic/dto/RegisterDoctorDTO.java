package ClinicMindhub.Medical.Clinic.dto;

import ClinicMindhub.Medical.Clinic.models.Genre;
import ClinicMindhub.Medical.Clinic.models.MedicalSpeciality;

import java.util.List;

public record RegisterDoctorDTO(String firstName, String lastName, String email, String password, Genre genre, MedicalSpeciality speciality, List<String> workDays, List<Integer> hours) {

}
