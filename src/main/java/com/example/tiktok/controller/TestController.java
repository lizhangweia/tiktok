package com.example.tiktok.controller;

import com.example.tiktok.pojo.Info;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping //是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径
@RestController //用来返回Json
public class TestController {
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public String getInfo(String phonenum){
        Info info = new Info();
        Gson gson = new Gson();
        info.id = phonenum;
        info.data = "Its name is too complaint...哈哈";
        return gson.toJson(info);
    }
}