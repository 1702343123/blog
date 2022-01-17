package com.person.blog.service;

import com.person.blog.entity.User;
import com.person.blog.entity.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<User> selectAll();
    User getUserById(Integer user_er,Integer user_ed);
    User getUserByPhoneNumber(String phoneNumber);
//    登录
    int signIn(UserDTO userDTO);
//    注册
    int signUp(UserDTO userDTO);
}
