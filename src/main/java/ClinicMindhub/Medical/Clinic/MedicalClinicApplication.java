package ClinicMindhub.Medical.Clinic;


import ClinicMindhub.Medical.Clinic.dto.DoctorDTO;
import ClinicMindhub.Medical.Clinic.models.*;
import ClinicMindhub.Medical.Clinic.repositories.AdminRepository;
import ClinicMindhub.Medical.Clinic.repositories.AppointmentRepository;
import ClinicMindhub.Medical.Clinic.repositories.DoctorRepository;
import ClinicMindhub.Medical.Clinic.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MedicalClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalClinicApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(PatientRepository patientRepository, AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, AdminRepository adminRepository) {
		return args -> {

			Patient prueba1 = new Patient("Guillermo", "Pérez", "guilleperez@gmail.com", passwordEncoder.encode("guille1234"), Genre.MALE, LocalDate.of(1990, 1, 1));
			Doctor doctor = new Doctor("Juan", "Rodriguez", Genre.MALE, MedicalSpeciality.CARDIOLOGIST, List.of("MONDAY", "WEDNESDAY", "FRIDAY"), List.of(8, 9, 10, 11, 12), "juan@doctor.com", passwordEncoder.encode("Juan123"));
			Appointment appointment = new Appointment(LocalDate.now(), LocalDateTime.now().getHour());
			Admin admin = new Admin("Matias", "Martinez", "mati@admin.com", passwordEncoder.encode("Mati123"));

			prueba1.addAppointment(appointment);
			doctor.addAppointment(appointment);

			patientRepository.save(prueba1);
			doctorRepository.save(doctor);
			appointmentRepository.save(appointment);
			adminRepository.save(admin);

			System.out.println(LocalDate.now().getDayOfWeek());

			System.out.println(new DoctorDTO(doctor));




		};





	}

}
