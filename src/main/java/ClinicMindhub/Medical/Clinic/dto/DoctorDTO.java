package ClinicMindhub.Medical.Clinic.dto;

import ClinicMindhub.Medical.Clinic.models.Appointment;
import ClinicMindhub.Medical.Clinic.models.Doctor;
import ClinicMindhub.Medical.Clinic.models.MedicalSpeciality;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorDTO {
    private Long id;
    private String firstName, lastName, genre;
    private MedicalSpeciality speciality;
    private List<AppointmentDTO> appointments = new ArrayList<>();
    private List <LocalDateTime> schedule = new ArrayList<>();

    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.genre = doctor.getGenre();
        this.speciality = doctor.getSpeciality();
        for (Appointment appointment : doctor.getAppointments()) {
            this.appointments.add(new AppointmentDTO(appointment));
        }
        this.schedule = doctor.getSchedule();
    }
}
