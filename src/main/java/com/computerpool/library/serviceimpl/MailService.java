package com.computerpool.library.serviceimpl;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.computerpool.library.entity.Student;

import lombok.AllArgsConstructor;
@Component
@AllArgsConstructor
public class MailService {

    
    private JavaMailSender mailSender;
    
    public  String sendConfirmationMail(Student student){

        String studentEmail=student.getEmail();
        String subject="Account confirmation mail";
        String confirmationURL="http://localhost:8080/confirmation?activationCode="+student.getStudentDetails().getActivationToken();

        SimpleMailMessage email=new SimpleMailMessage();
        
        email.setTo(studentEmail);
        email.setSubject(subject);
        email.setText(confirmationURL);
        email.setFrom("gouthaman@stark.com");
        mailSender.send(email);

        return "Mail sent successfully";
        
        
}

 public  String sendPasswordResetMail(Student student){

        String studentEmail=student.getEmail();
        String subject="Password Reset mail";
        String confirmationURL="http://localhost:8080/passwordReset?resetCode="+student.getStudentDetails().getActivationToken();

        SimpleMailMessage email=new SimpleMailMessage();
        
        email.setTo(studentEmail);
        email.setSubject(subject);
        email.setText(confirmationURL);
        email.setFrom("gouthaman@stark.com");
        mailSender.send(email);

        return "Mail sent successfully";
        
        
}


}

