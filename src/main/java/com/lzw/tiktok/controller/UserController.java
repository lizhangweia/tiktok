package com.lzw.tiktok.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.lzw.tiktok.pojo.User;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    //手机号验证码
    int verifycode = 0;

    //手机号密码登录
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Long phonenum,String passwor){
        User user = new User();
        Gson gson = new Gson();
        user.setPhonenum(phonenum);
        return gson.toJson(user);
    }

    //手机号验证码登录
    @RequestMapping(value = "/getverify",method = RequestMethod.GET)
    public boolean getverify(Long phonenum){
        StringBuffer stringBuffer=new StringBuffer();
        for (int x=0;x<=5;x++) {
            int random = (int) (Math.random() * (10 - 1));
            stringBuffer.append(random);
        }
        String string = stringBuffer.toString();
        int i = Integer.parseInt(string);
        verifycode = i;
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fdrvy8moSdZ5sx6kJgj", "IkIgfop2XWzi5Kan2UTHtk5aBhTL0N");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", String.valueOf(phonenum));
        request.putQueryParameter("SignName", "划水");
        request.putQueryParameter("TemplateCode", "SMS_180352004");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+i+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }

    //输入手机短信收到验证码认证
    @RequestMapping(value = "/verify",method = RequestMethod.GET)
    public boolean verify(int code){
        if(verifycode == code)
            return true;
        return false;
    }
}
