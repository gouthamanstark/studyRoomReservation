package com.computerpool.library.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.computerpool.library.entity.CurrentReservations;
import com.computerpool.library.entity.Student;
import com.computerpool.library.nfcReader.NfcReader;
import com.computerpool.library.repository.ReservationRepo;
import com.computerpool.library.repository.StudentRepo;
import com.computerpool.library.service.CommonServices;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class CommonServicesImpl implements CommonServices{

   
    private StudentRepo studentRepository;
    private MailService mailService;
    private ReservationRepo reservationRepository;

   

    @Autowired
    public CommonServicesImpl(StudentRepo studentRepository,MailService mailService,ReservationRepo reservationRepository){
        this.studentRepository=studentRepository;
        this.mailService=mailService;
        this.reservationRepository=reservationRepository;
       

    }

    @Override
    public Student getUserInfoByID(int universityEnrollmentNumber) {

       Optional<Student> response =studentRepository.findById(universityEnrollmentNumber);

        if(response.isPresent()){
            return response.get();
            
        }
        else{
            return response.get();
        }
    }



    @Override
    public Student updateStudent(Student updatedStudent) {

        Student existingStudent= studentRepository.findById(updatedStudent.getStudentId()).get();
        
        updatedStudent.getStudentDetails().setActivationToken(existingStudent.getStudentDetails().getActivationToken());
        updatedStudent.getStudentDetails().setPassword(existingStudent.getStudentDetails().getPassword());
        updatedStudent.getStudentDetails().setEnabled(existingStudent.getStudentDetails().getEnabled());      
        updatedStudent.getStudentDetails().setNfcTagUUID(existingStudent.getStudentDetails().getNfcTagUUID());
        
        studentRepository.save(updatedStudent);
        return updatedStudent;
    }

    @Override
    public Student getStudentInfoByEmail(String email) {

         Optional<Student> response =studentRepository.findByEmail(email);
         if(response.isPresent()){
            return response.get();
         }
        return response.get();
        
    }
     

    @Override
    public Student getStudentInfoBynfcTagUUID() {

        String nfcTagUUID=NfcReader.nfcReader();
        Optional<Student> response =studentRepository.findBynfcTagUUID(nfcTagUUID);

        if(response.isPresent()){
            return response.get();
            
        }
        else{
            return response.get();
        }
    }

 @Override
    public List<CurrentReservations> getStudentReservations(int studentId,int pageNumber,int pageSize) {
        
        Pageable pageable=PageRequest.of(pageNumber-1, pageSize);
        Page<CurrentReservations> reservations= reservationRepository.findByStudentId(studentId,pageable);
        
        return reservations.getContent();
        }

    @Override
    public Optional<Student> getStudentInfoByActivationToken(String activationToken) {
        Optional<Student> response =studentRepository.findByactivationToken(activationToken);

        return response;
    }
 
    @Override
    public String accountActivation(String activationToken,String password) {

        Optional<Student> student=studentRepository.findByactivationToken(activationToken);

        if(student.isPresent()){
            Student details=student.get();
            details.getStudentDetails().setEnabled(true);
            details.getStudentDetails().setActivationToken("");
            password=encryptPassword(password);
            details.getStudentDetails().setPassword(password);
            studentRepository.save(details);
            
            return "Password reset successful ! Please try to login !";
             
        }
        else{
            return "Invalid token provided ! Account confirmation failed !";
            
        }
        
    } 

    public Boolean passwordResetConfirmation(String email){

        Optional<Student> student=studentRepository.findByEmail(email);
        if(student.isPresent()){

            Student details=student.get();
            details.getStudentDetails().setActivationToken(RandomNumberGenerator.generateRandomActivationCode());
            details.getStudentDetails().setEnabled(false);
            mailService.sendPasswordResetMail(details);
            studentRepository.save(details);

            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public String changePassword(int studentId,String password) {

        Optional<Student> student=studentRepository.findById(studentId);

        if(student.isPresent()){
            Student details=student.get();
            password=encryptPassword(password);
            details.getStudentDetails().setPassword(password);
            studentRepository.save(details);
            
            return "Password reset successful ! Please try to login !";
             
        }
        else{
            return "Invalid token provided ! Account confirmation failed !";
            
        }

     }

      @Override
    public int getLoggedInUserDetail() {
        
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        int studentId=Integer.parseInt(authentication.getName());
        return studentId;
    
    }


   
    
}
