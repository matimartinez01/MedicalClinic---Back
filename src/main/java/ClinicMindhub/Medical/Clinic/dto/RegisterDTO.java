package ClinicMindhub.Medical.Clinic.dto;

import ClinicMindhub.Medical.Clinic.models.Genre;

import java.time.LocalDate;

public record RegisterDTO(String firstName, String lastName, String email, String password, Genre genre, LocalDate birthDate) {
}
