package com.computerpool.library.service;

import java.sql.Date;
import java.util.List;

import com.computerpool.library.entity.CurrentReservations;
import com.computerpool.library.entity.Student;

public interface AdminService {

    public Student registerNewStudent(Student studentDetails);

    public List<Student> getStudentsList();

    public String deleteStudent(int universityEnrollmentNumber);

    public List<CurrentReservations> getReservationsByDate(Date date);

    public String blockAndUnBlockStudentAccount(int studentId);

    public int getNumberOfStudentAccounts();

    public int getNumberOfReservedSlotsForToday();

    public void generatePdfReport(Date reportDate);



    
    
}
