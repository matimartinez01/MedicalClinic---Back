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

			Patient prueba1 = new Patient("Will", "Smith", "willsmith@gmail.com", passwordEncoder.encode("will1234"), Genre.MALE, LocalDate.of(1990, 1, 1));
			Patient prueba2 = new Patient("Rose", "Stones", "rosestones@gmail.com", passwordEncoder.encode("marianagomez1234"), Genre.FEMALE, LocalDate.of(1990, 1, 1));
			Patient prueba3 = new Patient("Christian", "Browns", "christianb@gmail.com", passwordEncoder.encode("cristian1234"), Genre.MALE, LocalDate.of(1990, 1, 1));
			Patient prueba4 = new Patient("Charles", "Lincolns", "charlesl@gmail.com", passwordEncoder.encode("cristian1234"), Genre.MALE, LocalDate.of(1990, 1, 1));
			Patient prueba5 = new Patient("Robert", "Paterson", "robertpaterson@gmail.com", passwordEncoder.encode("cristian1234"), Genre.MALE, LocalDate.of(1990, 1, 1));


			Doctor doctor = new Doctor("Juan", "Rodríguez", Genre.MALE, MedicalSpeciality.CARDIOLOGIST, List.of("MONDAY", "WEDNESDAY", "FRIDAY"), List.of(8, 9, 10, 16, 17, 18, 19), "juan@doctor.com", passwordEncoder.encode("Juan123"));
			Doctor doctor1 = new Doctor("Peter", "Lee", Genre.MALE, MedicalSpeciality.NEUROLOGIST, List.of("MONDAY", "WEDNESDAY", "FRIDAY"), List.of(8, 9, 10, 11), "peter@doctor.com", passwordEncoder.encode("Peter123"));
			Doctor doctor2 = new Doctor("Hanaa", "Moore", Genre.FEMALE, MedicalSpeciality.GYNECOLOGIST, List.of("TUESDAY", "THURSDAY", "SATURDAY"), List.of(13, 15, 16, 17, 18), "ana@doctor.com", passwordEncoder.encode("Ana123"));
			Doctor doctor3 = new Doctor("Grace", "Smith", Genre.MALE, MedicalSpeciality.PEDIATRICIAN, List.of("MONDAY", "THURSDAY"), List.of(10, 11, 12, 15, 19), "grace@doctor.com", passwordEncoder.encode("Carlos123"));
			Doctor doctor4 = new Doctor("Wayne", "Rooney", Genre.MALE, MedicalSpeciality.TRAUMATOLOGIST, List.of("MONDAY", "TUESDAY", "THURSDAY", "WEDNESDAY", "FRIDAY"), List.of(9, 10, 11, 12, 15, 16),"wayne@doctor.com",passwordEncoder.encode("Alex123"));
			Doctor doctor5 = new Doctor("Alex", "Morgan", Genre.FEMALE, MedicalSpeciality.PULMONOLOGIST, List.of("THURSDAY", "WEDNESDAY", "FRIDAY"), List.of(14, 15, 16, 17, 18),"alex@doctor.com",passwordEncoder.encode("Alex123"));
			Doctor doctor6 = new Doctor("Cole", "Palmer", Genre.MALE, MedicalSpeciality.GASTROENTEROLOGIST, List.of("THURSDAY", "FRIDAY"), List.of(8,9,10,11,12,13),"cole@doctor.com",passwordEncoder.encode("Cole123"));


			Admin admin = new Admin("Matias", "Martinez", "mati@admin.com", passwordEncoder.encode("Mati123"));


			Appointment appointment = new Appointment(LocalDate.now(), LocalDateTime.now().minusHours(1).getHour());
			Appointment appointment2 = new Appointment(LocalDate.now().plusDays(2), LocalDateTime.now().plusHours(3).getHour());
			Appointment appointment3 = new Appointment(LocalDate.now().minusDays(3), LocalDateTime.now().plusHours(3).getHour());
			Appointment appointment4 = new Appointment(LocalDate.now(), LocalDateTime.now().plusHours(3).getHour());


			prueba1.addAppointment(appointment);
			prueba2.addAppointment(appointment2);
			prueba3.addAppointment(appointment3);
			prueba4.addAppointment(appointment4);

			doctor.addAppointment(appointment);
			doctor1.addAppointment(appointment2);
			doctor2.addAppointment(appointment3);
			doctor3.addAppointment(appointment4);


			patientRepository.save(prueba1);
			patientRepository.save(prueba2);
			patientRepository.save(prueba3);
			patientRepository.save(prueba4);
			patientRepository.save(prueba5);


			doctorRepository.save(doctor);
			doctorRepository.save(doctor1);
			doctorRepository.save(doctor2);
			doctorRepository.save(doctor3);
			doctorRepository.save(doctor4);
			doctorRepository.save(doctor5);
			doctorRepository.save(doctor6);


			appointmentRepository.save(appointment);
			appointmentRepository.save(appointment2);
			appointmentRepository.save(appointment3);
			appointmentRepository.save(appointment4);


			adminRepository.save(admin);

			System.out.println(LocalDate.now().getDayOfWeek());

			System.out.println(new DoctorDTO(doctor));




		};





	}

}
