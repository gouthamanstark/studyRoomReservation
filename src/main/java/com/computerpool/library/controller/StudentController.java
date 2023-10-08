package com.computerpool.library.controller;

import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.computerpool.library.entity.CurrentReservations;
import com.computerpool.library.entity.Student;
import com.computerpool.library.service.CommonServices;
import com.computerpool.library.service.StudentService;
import lombok.AllArgsConstructor;




@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {
    
   
    private CommonServices commonServices;
    private StudentService studentServices;


    @GetMapping("/home")
    public String showStudentHomePage(Model theModel) {

        int studentID=commonServices.getLoggedInUserDetail();
        theModel.addAttribute("user_name", studentID);
        theModel.addAttribute("reservation", commonServices.getStudentReservations(studentID,1,10));
        return "students/index";
    }

    
    @GetMapping("/profile")
    public String getStudentProfile(Model theModel){

        int studentID=commonServices.getLoggedInUserDetail();
        theModel.addAttribute("user_name", studentID);
        theModel.addAttribute("student", commonServices.getUserInfoByID(studentID));
        return "students/profile";

    }
    
   
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(Model theModel){

        int studentId=commonServices.getLoggedInUserDetail();
        theModel.addAttribute("student", commonServices.getUserInfoByID(studentId));
        return "common/student-details-form";
    }



    @GetMapping("/changePassword")
    public String showChangePasswordForm(Model theModel){

        int studentId=commonServices.getLoggedInUserDetail();
        theModel.addAttribute("user_name", studentId);
        return "students/form";
    }

    @PostMapping("/changePassword")
    public String changeNewPassword(Model theModel,@RequestParam("password") String password){

        int studentId=commonServices.getLoggedInUserDetail();
        commonServices.changePassword(studentId, password);
        return "redirect:/student/login";
    }


    @GetMapping("/showFormForReservation")
    public String showFormForReservation(Model theModel){

        int studentId=commonServices.getLoggedInUserDetail();
        CurrentReservations reservation=new CurrentReservations();
        Student student=new Student();
        student.setStudentId(studentId);
        reservation.setStudent(student);

        theModel.addAttribute("user_name", studentId);
        theModel.addAttribute("reservation", reservation);
        return "students/reservation";
    
    }

    @PostMapping("/reserve")
    @ResponseBody
    public String reserveSlotForStudent(@ModelAttribute("reservation") CurrentReservations reservation){

       return studentServices.reserveStudyRoom(reservation);

    }

    @GetMapping("/showFormForReservationUpdate")
    public String showFormForReservationUpdate(@RequestParam("reservationID") int reservationID,Model theModel){

        
        theModel.addAttribute("reservation", studentServices.getReservationByID(reservationID));
        return "students/reservation-calendar";
    }


    @GetMapping("/cancelReservation")
    public String cancelReservation(@RequestParam("reservationID")long reservationID){

        studentServices.cancelReservationByID(reservationID);
        return "redirect:/student/home/";

      
    }

    @GetMapping("/checkRoomAvailability")
    @ResponseBody
    public List<Integer> checkRoomAvailability(@RequestParam String startTime,@RequestParam String endTime,Date reservedDate){

        List<Integer> availableRooms=studentServices.checkRoomAvailability(reservedDate,startTime,endTime);
        return availableRooms;
    }
}



