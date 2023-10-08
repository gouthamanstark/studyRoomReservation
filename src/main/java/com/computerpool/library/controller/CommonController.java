package com.computerpool.library.controller;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.computerpool.library.entity.Student;
import com.computerpool.library.service.CommonServices;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class CommonController {

    private CommonServices commonServices;


    @GetMapping("/login")
    public String loginHandler(Model theModel){

        return "common/login";
        
    }

    @GetMapping("/confirmation")
    public String verifyEmail(@RequestParam("activationCode") String activationCode,RedirectAttributes attributes){

        attributes.addAttribute("resetCode", activationCode);
        return "redirect:/passwordReset";
    }

    @GetMapping("/passwordResetForm")
    public String showFormForPasswordReset(){
       
        return "common/password-reset-form";

    }


    @PostMapping("/passwordResetConfirmation")
    @ResponseBody
    public Boolean passwordResetConfirmation(@RequestParam("email") String email){
       
       Boolean response=commonServices.passwordResetConfirmation(email);
       return response;
    }

    @GetMapping("/passwordReset")
    public String showPasswordResetForm(@RequestParam("resetCode") String activationToken,Model theModel){
        Optional<Student> student=commonServices.getStudentInfoByActivationToken(activationToken);
        if(student.isPresent()){
            theModel.addAttribute("token", activationToken);
            return "common/password-form";
        }
        return "common/password-form";
    }

    @PostMapping("/passwordReset")
    public String changeNewPassword(Model theModel,@RequestParam("password") String password,@RequestParam("token") String token){
        String response=commonServices.accountActivation(token,password);
        theModel.addAttribute("status", response);

        return "redirect:/login";
    }

   
    
}
