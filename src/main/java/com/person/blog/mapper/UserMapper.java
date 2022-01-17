package com.person.blog.mapper;

import com.person.blog.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    @Results({
            @Result(column = "userID", property = "userID"),
            @Result(column = "userName", property = "userName"),
            @Result(column = "headUrl", property = "headUrl"),
            @Result(column = "phoneNumber", property = "phoneNumber"),
            @Result(column = "password", property = "password"),
            @Result(column = "realName", property = "realName"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "address", property = "address"),
            @Result(column = "email", property = "email"),
            @Result(column = "qq", property = "qq"),
            @Result(column = "role", property = "role"),
            @Result(column = "isUse", property = "isUse"),
            @Result(column = "registrationTime", property = "registrationTime")
    })

    //查询所有用户列表
    @Select("select * from user_info order by registrationTime desc")
    List<User> getUserList();


    //根据id查询用户
    @Select("select * from user_info where userID=#{id}")
    User getUserById(@Param("id") Integer id);

    //根据手机查询用户
    @Select("select * from user_info where phoneNumber=#{phoneNumber}")
    User getUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    //注册
    @Insert("insert into user_info(userName,headUrl,phoneNumber,password,role,isUse,registrationTime) values(#{userName},#{headUrl},#{phoneNumber},#{password},1,0,#{registrationTime})")
    int singUp(User user);

    //更新我的资料
    @Update("update user_info set userName=#{userName},headUrl=#{headUrl},realName=#{realName},sex=#{sex},address=#{address},email=#{email},qq=#{qq} where userID=#{userID}")
    int updateMyMsg(User user);

    //修改密码
    @Update("update user_info set password=#{password} where userID=#{userID}")
    int updatePassword(User user);

    //注销账号
    @Update("update user_info set isUse=1 where userID=#{userID}")
    int cancelUser(int userID);

    //启用账号
    @Update("update user_info set isUse=0 where userID=#{userID}")
    int qiUser(int userID);

    //删除用户
    @Delete("delete from user_info where userID=#{userID}")
    int deleteUser(int userID);
}
