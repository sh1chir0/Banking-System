package ua.sh1chiro.bank_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sh1chiro 02.03.2023
 */
@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping("/verification")
    public String verification(){
        return "/bank/errors/verification";
    }
    @GetMapping("/verification-true")
    public String verificationTrue(){
        return "/bank/errors/verificationTrue";
    }


}
