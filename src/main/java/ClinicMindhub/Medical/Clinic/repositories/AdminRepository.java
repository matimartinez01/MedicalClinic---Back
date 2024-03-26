package ClinicMindhub.Medical.Clinic.repositories;

import ClinicMindhub.Medical.Clinic.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);

}
