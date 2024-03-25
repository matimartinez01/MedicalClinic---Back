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

    private String firstName, lastName, genre, email, password;

    @Enumerated(EnumType.STRING)
    private MedicalSpeciality speciality;


    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();

    private List <String> workDays = new ArrayList<>();

    private List <Integer> hours = new ArrayList<>();

    public Doctor() {

    }

    public Doctor(String firstName, String lastName, String genre, MedicalSpeciality speciality, List<String> workDays, List<Integer> hours, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.genre = genre;
        this.speciality = speciality;
        this.workDays = workDays;
        this.hours = hours;
        this.email = email;
        this.password = password;
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

    public List<String> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(List<String> workDays) {
        this.workDays = workDays;
    }

    public List<Integer> getHours() {
        return hours;
    }

    public void setHours(List<Integer> hours) {
        this.hours = hours;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
