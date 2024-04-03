package ClinicMindhub.Medical.Clinic.dto;

import ClinicMindhub.Medical.Clinic.models.Admin;
import ClinicMindhub.Medical.Clinic.models.Doctor;
import ClinicMindhub.Medical.Clinic.models.Genre;
import ClinicMindhub.Medical.Clinic.models.MedicalSpeciality;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class AdminDTO {

    private Long id;
    private String firstName, lastName;

    private String email;


    public AdminDTO(Admin admin) {
        this.id = admin.getId();
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
        this.email = admin.getEmail();
    }


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "DoctorDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email +
                '}';
    }
}
