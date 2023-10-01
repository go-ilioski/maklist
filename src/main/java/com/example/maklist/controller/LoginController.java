package com.example.maklist.controller;

import com.example.maklist.dto.LoginDto;
import com.example.maklist.dto.UserDto;
import com.example.maklist.repository.ProcedureRepository;
import com.example.maklist.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final UserRepository userRepository;
    private final ProcedureRepository procedureRepository;

    @GetMapping
    public String getLoginPage(){
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model){

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail(request.getParameter("email"));
        loginDto.setPassword(request.getParameter("password"));
        Boolean login = procedureRepository.loginCheck(loginDto);
        if(login){
            UserDto userDto = userRepository.findByEmail(loginDto.getEmail());
            //loginDto.setEmail("lmao");
            request.getSession().setAttribute("user",userDto);
            return "redirect:/home";
        }
        else {
            model.addAttribute("hasError",true);
            model.addAttribute("errors","Invalid email or password!");
            return "login";
        }
    }
}