package com.example.ubergo.controller;

import com.example.ubergo.DTO.GeneralMessageDTO;
import com.example.ubergo.entity.User;
import com.example.ubergo.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public GeneralMessageDTO createAUser(@RequestBody User user){

        return authService.createAUser(user);
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public GeneralMessageDTO login(@RequestParam(value="phone") String mobileNumber,@RequestParam(value="token") String token){

        return authService.login(mobileNumber,token);
    }

    @RequestMapping(value = "/user/{uid}",method = RequestMethod.PUT)
    public GeneralMessageDTO updateUser(@PathVariable int uid,@RequestBody User user){
        return authService.updateUser(uid,user);
    }

}
