package ClinicMindhub.Medical.Clinic.repositories;

import ClinicMindhub.Medical.Clinic.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByFirstNameAndLastName(String firstName, String lastName);

    Doctor findByEmail(String email);


}
