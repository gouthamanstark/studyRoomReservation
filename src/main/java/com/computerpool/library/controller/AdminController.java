package com.computerpool.library.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.computerpool.library.entity.Student;
import com.computerpool.library.nfcReader.NfcReader;
import com.computerpool.library.service.AdminService;
import com.computerpool.library.service.CommonServices;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
   
    private AdminService adminService;
    private CommonServices commonServices;
   
    
    @GetMapping("/home")
    public String getAdminHomePage(Model theModel){

        int adminId=commonServices.getLoggedInUserDetail();
        int maxSlots=690;
        Date currentDate=Date.valueOf(LocalDate.now());
        int reservedSlots=adminService.getNumberOfReservedSlotsForToday();

        theModel.addAttribute("reservation", adminService.getReservationsByDate(currentDate));
        theModel.addAttribute("numberOfStudents", adminService.getNumberOfStudentAccounts());
        theModel.addAttribute("user_name", adminId);
        theModel.addAttribute("numberOfBookedSlotToday",reservedSlots);
        theModel.addAttribute("numberOfFreeSlotToday",maxSlots-reservedSlots );
        return "/admin/index";
        
    }

    @GetMapping("/profile")
    public String getAdminProfile(Model theModel){

        int adminId=commonServices.getLoggedInUserDetail();
        theModel.addAttribute("user_name", adminId);
        theModel.addAttribute("student", commonServices.getUserInfoByID(adminId));
        return "admin/profile";

    }

    @PostMapping("/reserveStudent")
    public String registerNewStudent(Model theModel,@ModelAttribute("student") Student student){
        
       Student response= adminService.registerNewStudent(student);
        if(response.getStudentDetails().getNfcTagUUID()==null){

             int adminId=commonServices.getLoggedInUserDetail();
             theModel.addAttribute("user_name", adminId);
             theModel.addAttribute("nfcError",true);
             theModel.addAttribute("student", response);
             return "admin/student-registration-form";

        }
        else{
            return "redirect:/admin/listStudents";
        }
        
    }


    @PostMapping("/updateStudent")
    public String updateStudentInfo(Model theModel,@ModelAttribute("student") Student student){
       
        commonServices.updateStudent(student);
        return "redirect:/admin/listStudents";
    }


    @GetMapping("/listStudents")
    public String getStudentsList(Model theModel){
        int adminId=commonServices.getLoggedInUserDetail();
        theModel.addAttribute("user_name", adminId);
        
        List<Student> listOfStudents= adminService.getStudentsList();
        theModel.addAttribute("employees",listOfStudents);
        return "admin/list-students";
    }


    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        int adminId=commonServices.getLoggedInUserDetail();
        theModel.addAttribute("user_name", adminId);
        
        Student studentDetails=new Student();
        theModel.addAttribute("student", studentDetails);
        return "admin/student-registration-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("universityID") int universityEnrollmentNumber,Model theModel){
        int adminId=commonServices.getLoggedInUserDetail();
        theModel.addAttribute("user_name", adminId);
        theModel.addAttribute("student", commonServices.getUserInfoByID(universityEnrollmentNumber));
        return "admin/student-update-form";
    }

    @GetMapping("/delete")
    public String deleteStudentById(Model theModel,@RequestParam("universityID") int universityEnrollmentNumber){
        adminService.deleteStudent(universityEnrollmentNumber);

        return "redirect:/admin/listStudents";

    }

   
    
  /*  @GetMapping("/student/details")
    @ResponseBody
    public List<CurrentReservations> getReservationInfoBySearchTerm(@RequestParam("searchTerm") Object searchTerm){
        
        return adminService.getInfoBySearchTerm(searchTerm.toString());
    }
*/


@GetMapping("/studentReservations")
public String getStudentReservations(@RequestParam("studentId") int studentId,
                                     @RequestParam(value="page",defaultValue = "1",required = false) int pageNumber,
                                     @RequestParam(value="size",defaultValue = "5",required = false) int pageSize,Model theModel){

    theModel.addAttribute("reservation",commonServices.getStudentReservations(studentId,pageNumber,pageSize));
    return "/admin/student-reservation-list";
}


@GetMapping("/changeAccountStatus")
public String blockAndUnBlockStudentAccount(@RequestParam("studentId") int studentID){

    adminService.blockAndUnBlockStudentAccount(studentID);
    return "redirect:/admin/listStudents";
}


@GetMapping("/changePassword")
public String showChangePasswordForm(Model theModel){

    int adminId=commonServices.getLoggedInUserDetail();
    theModel.addAttribute("user_name", adminId);   
    return "admin/form";
}


@PostMapping("/changePassword")
public String changeNewPassword(Model theModel,@RequestParam("password") String password,HttpSession session){
    
    int studentId=commonServices.getLoggedInUserDetail();
    commonServices.changePassword(studentId, password);
    session.invalidate();  
    return "redirect:/login?passwordChanged=true";
}


@PostMapping("/generateReport")
public String generateReport(@RequestParam("reportDate") Date reportDate){
    
    adminService.generatePdfReport(reportDate);
    return "redirect:/admin/home";
    }


@GetMapping("/checkNFCDeviceStatus")
@ResponseBody
public Boolean getNFCDeviceStatus(){

    Boolean status=NfcReader.checkNFCReaderStatus();
    return status;
    }
    
}


