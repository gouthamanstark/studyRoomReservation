package com.computerpool.library.service;

import java.util.List;
import java.util.Optional;

import com.computerpool.library.entity.CurrentReservations;
import com.computerpool.library.entity.Student;

public interface CommonServices {
    
    public Student getUserInfoByID(int universityEnrollmentNumber);
    public Student updateStudent(Student student);
    public Student getStudentInfoByEmail(String email);
     public Student getStudentInfoBynfcTagUUID();
    public Optional<Student> getStudentInfoByActivationToken(String activationToken);
    String accountActivation(String activationToken,String password);
    public Boolean passwordResetConfirmation(String email);
    String encryptPassword(String password);
    public String changePassword(int studentId,String password);
    public List<CurrentReservations> getStudentReservations(int studentId,int pageNumber,int pageSize);
    public int getLoggedInUserDetail();

}
