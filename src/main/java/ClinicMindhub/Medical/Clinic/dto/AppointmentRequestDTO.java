package ClinicMindhub.Medical.Clinic.dto;

import java.time.LocalDate;

public record AppointmentRequestDTO(LocalDate date, Integer time, String emailDoctor) {

}
