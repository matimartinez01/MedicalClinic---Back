package ClinicMindhub.Medical.Clinic.dto;

import java.time.LocalDate;

public record AppointmentRequestDTOAdmin(LocalDate date, Integer time, String emailDoctor, String emailPatient) {
}
