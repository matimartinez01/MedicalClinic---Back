package ClinicMindhub.Medical.Clinic.SecurityServices;

import ClinicMindhub.Medical.Clinic.repositories.AdminRepository;
import ClinicMindhub.Medical.Clinic.repositories.DoctorRepository;
import ClinicMindhub.Medical.Clinic.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplements implements UserDetailsService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var patient = patientRepository.findByEmail(username);
        var doctor = doctorRepository.findByEmail(username);
        var admin = adminRepository.findByEmail(username);
        if (patient == null && doctor == null && admin == null){
            throw new UsernameNotFoundException(username);
        }
        if (doctor != null && patient == null && admin == null){
            return User
                    .withUsername(username)
                    .password(doctor.getPassword())
                    .roles("DOCTOR")
                    .build();
        }
        if(admin != null && patient == null && doctor == null){
            return User
                    .withUsername(username)
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();
        }
        return User
                .withUsername(username)
                .password(patient.getPassword())
                .roles("PATIENT")
                .build();
    }

}
