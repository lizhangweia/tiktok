package com.lzw.tiktok.service.impl;

import com.lzw.tiktok.mapper.IUserDAO;
import com.lzw.tiktok.pojo.User;
import com.lzw.tiktok.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceimpl implements UserService {
    @Autowired(required = false)
    private IUserDAO userDAO;

    @Override
    public User loginValidate(User user) {
        return userDAO.getUser(user);
    }
}
