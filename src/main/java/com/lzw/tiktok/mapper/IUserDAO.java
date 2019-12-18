package com.lzw.tiktok.mapper;

import com.lzw.tiktok.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IUserDAO {
    @Select("select * from user where phonenum=#{phonenum} and password=#{password}")
    public User getUser(User user);
}
