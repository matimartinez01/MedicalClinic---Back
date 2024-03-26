package ClinicMindhub.Medical.Clinic.SecurityServices;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var patient = patientRepository.findByEmail(username);
        var doctor = doctorRepository.findByEmail(username);
        if (patient == null && doctor == null){
            throw new UsernameNotFoundException(username);
        }
        if (doctor != null && patient == null){
            return User
                    .withUsername(username)
                    .password(doctor.getPassword())
                    .roles("DOCTOR")
                    .build();
        }
        return User
                .withUsername(username)
                .password(patient.getPassword())
                .roles("PATIENT")
                .build();
    }

}
