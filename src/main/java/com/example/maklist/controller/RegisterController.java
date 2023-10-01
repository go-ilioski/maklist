package com.example.maklist.controller;


import com.example.maklist.dto.LoginDto;
import com.example.maklist.dto.RegisterUserDto;
import com.example.maklist.repository.ProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final ProcedureRepository procedureRepository;

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String email,
                           @RequestParam String address,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam Integer phoneNumber,
                           Model model){


        if (email==null || email.isEmpty()  || password==null || password.isEmpty()) {
            model.addAttribute("hasError",true);
            model.addAttribute("errors","Invalid email or password!");
            return "register";
        }
        if(!password.equals(repeatedPassword)){
            model.addAttribute("hasError",true);
            model.addAttribute("errors","Passwords must match!");
            return "register";
        }
        LoginDto log = new LoginDto();
        log.setEmail(email);
        log.setPassword(password);
        if(procedureRepository.loginCheck(log)){
            model.addAttribute("hasError",true);
            model.addAttribute("errors","Email already exists! Choose another one!");
            return "register";
        }


        RegisterUserDto register = new RegisterUserDto();
        register.setEmail(email);
        register.setName(name);
        register.setSurname(surname);
        register.setAddress(address);
        register.setPassword(password);
        register.setPhoneNumber(new BigDecimal(phoneNumber));

        procedureRepository.registerUser(register);
        return "redirect:/login";

//        try{
//            this.userService.register(username, password, repeatedPassword, name, surname, role);
//            return "redirect:/login";
//        }
//        catch (InvalidArgumentsException | PasswordsDoNotMatchException ex){
//
//            return "redirect:/register?error=" + ex.getMessage();
//        }
    }
}
