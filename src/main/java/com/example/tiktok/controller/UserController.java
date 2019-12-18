package com.example.tiktok.controller;

import com.example.tiktok.pojo.User;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Long phonenum){
        User user = new User();
        Gson gson = new Gson();
        user.setPhonenum(phonenum);
        return gson.toJson(user);
    }
}
