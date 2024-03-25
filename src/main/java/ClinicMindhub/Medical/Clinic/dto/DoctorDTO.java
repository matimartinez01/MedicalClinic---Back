package ClinicMindhub.Medical.Clinic.dto;


import ClinicMindhub.Medical.Clinic.models.Appointment;
import ClinicMindhub.Medical.Clinic.models.Doctor;
import ClinicMindhub.Medical.Clinic.models.MedicalSpeciality;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;



    public class DoctorDTO {
        private Long id;
        private String firstName, lastName, genre;
        private MedicalSpeciality speciality;
        private List<AppointmentDTO> appointments = new ArrayList<>();
        private List <String> workDays = new ArrayList<>();

        private List <Integer> hours = new ArrayList<>();


        public DoctorDTO(Doctor doctor) {
            this.id = doctor.getId();
            this.firstName = doctor.getFirstName();
            this.lastName = doctor.getLastName();
            this.genre = doctor.getGenre();
            this.speciality = doctor.getSpeciality();
            this.appointments = doctor.getAppointments().stream().map(AppointmentDTO::new).collect(toList());
            this.workDays = doctor.getWorkDays();
            this.hours = doctor.getHours();
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

        public String getGenre() {
            return genre;
        }

        public MedicalSpeciality getSpeciality() {
            return speciality;
        }

        public List<AppointmentDTO> getAppointments() {
            return appointments;
        }

        public List<String> getWorkDays() {
            return workDays;
        }

        public List<Integer> getHours() {
            return hours;
        }

        @Override
        public String toString() {
            return "DoctorDTO{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", genre='" + genre + '\'' +
                    ", speciality=" + speciality +
                    ", appointments=" + appointments +
                    ", days=" + workDays +
                    ", hours=" + hours +
                    '}';
        }
    }

