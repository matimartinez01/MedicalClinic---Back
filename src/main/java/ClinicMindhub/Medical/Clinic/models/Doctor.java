package ClinicMindhub.Medical.Clinic.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName, lastName, genre;

    @Enumerated(EnumType.STRING)
    private MedicalSpeciality speciality;


    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();

    private List <LocalDateTime> schedule = new ArrayList<>();

    public Doctor() {

    }

    public Doctor(String firstName, String lastName, String genre, MedicalSpeciality speciality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.genre = genre;
        this.speciality = speciality;
    }

    public Long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public MedicalSpeciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(MedicalSpeciality speciality) {
        this.speciality = speciality;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }


    public void addAppointment (Appointment appointment){
        appointment.setDoctor(this);
        appointments.add(appointment);
    }

    public List<LocalDateTime> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<LocalDateTime> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", genre='" + genre + '\'' +
                ", speciality=" + speciality +
                ", appointments=" + appointments +
                '}';
    }
}
