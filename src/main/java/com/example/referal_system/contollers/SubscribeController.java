package com.example.referal_system.contollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/subscriber")
public class SubscribeController {
   @GetMapping("/get")
    public String getSub(){
       return "I am subscriber!";
    }

}
