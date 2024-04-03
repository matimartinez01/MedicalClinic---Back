package ClinicMindhub.Medical.Clinic.controllers;

import ClinicMindhub.Medical.Clinic.dto.FormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/form")
public class EmailController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody FormDTO formDTO) {
        try {
            SimpleMailMessage clinicMessage = new SimpleMailMessage();
            clinicMessage.setTo("equaino.ir@gmail.com");
            clinicMessage.setFrom("quai1921@hotmail.com");
            clinicMessage.setSubject("New message from Serenety Health Center");
            clinicMessage.setText(
                    "Name: " + formDTO.firstName() + " " + formDTO.lastName() + "\n" +
                            "Phone: " + formDTO.phone() + "\n" +
                            "Email: " + formDTO.email() + "\n" +
                            "City: " + formDTO.city() + "\n" +
                            "ZipCode: " + formDTO.zipCode() + "\n" +
                            "Discover: " + formDTO.discover() + "\n" +
                            "Live in Miami: " + formDTO.liveMiami() + "\n" +
                            "Message: " + formDTO.message()
            );

            SimpleMailMessage userMessage = new SimpleMailMessage();
            userMessage.setTo(formDTO.email());
            userMessage.setFrom("quai1921@hotmail.com");
            userMessage.setSubject("Welcome to Serenety Health Center");
            userMessage.setText("Welcome " + formDTO.firstName() + ", we will contact you soon.");


            emailSender.send(clinicMessage);
            emailSender.send(userMessage);

            return "Email succesfully sent";
        } catch (Exception e) {
            return "Error in sending email " + e.getMessage();
        }
    }

}