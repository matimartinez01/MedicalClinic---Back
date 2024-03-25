package ClinicMindhub.Medical.Clinic;

import ClinicMindhub.Medical.Clinic.models.Patient;
import ClinicMindhub.Medical.Clinic.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MedicalClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalClinicApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PatientRepository patientRepository) {
		return args -> {

			Patient prueba1 = new Patient("Guillermo", "Pérez", "guilleperez@gmail.com", "guille1234", "male", LocalDate.of(1990, 1, 1));

			System.out.println(prueba1);
		};





	}

}
