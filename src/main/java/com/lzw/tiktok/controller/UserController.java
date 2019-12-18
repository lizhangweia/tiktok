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
import com.lzw.tiktok.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired(required = false)
    private UserService userService;
    //手机号验证码
    int verifycode = 0;

    //手机号密码登录
    @RequestMapping(value = "/login")
    public boolean login(long phonenum, String password){
        User user = new User();
        user.setPhonenum(phonenum);
        user.setPassword(password);
//        System.out.println(user.getPhonenum()+user.getPassword());
        User usr = userService.loginValidate(user);
        if (usr!=null){
            return true;
        }
        else {
            return false;
        }
    }

    //手机号验证码登录
    @RequestMapping(value = "/getverify")
    public boolean getverify(long phonenum){
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
            System.out.println(response.getData().split("\"")[11]);
            System.out.println(response.getData());
            if (response.getData().split("\"")[3].equals("OK")) {
                System.out.println("发送成功！");
                return true;
            } else {
                System.out.println("发送失败！");
                return false;
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }

    //输入手机短信收到验证码认证
    @RequestMapping(value = "/verify")
    public boolean verify(int code){
        if(verifycode == code)
            return true;//验证码正确
        return false;
    }
}
