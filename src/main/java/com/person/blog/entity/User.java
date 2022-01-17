package com.person.blog.entity;

import java.sql.Time;
import java.sql.Timestamp;

public class User {
    private Integer userID;
    private String userName;//用户昵称
    private String headUrl;
    private String phoneNumber;
    private String password;
    private String realName;
    private String sex;
    private String address;
    private String email;
    private String qq;
    private Integer role;//角色
    private Integer isUse;//是否可用
    private Timestamp registrationTime;

    private Integer currPage;
    private Integer pageSize;

    private String time;

    private Integer isFollow;//是否被登录用户关注：1(已关注),0(未关注),2(特殊情况)
    private Integer followCount;//被多少人关注

    private Integer blogCount;//博客数

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public User() {
    }

    public Integer getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Integer isFollow) {
        this.isFollow = isFollow;
    }

    public User(Integer userID, String userName, String headUrl, String phoneNumber, String password, String realName, String sex, String address, String email, String qq, Integer role, Integer isUse, Timestamp registsterTimp) {
        this.userID = userID;
        this.userName = userName;
        this.headUrl = headUrl;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.realName = realName;
        this.sex = sex;
        this.address = address;
        this.email = email;
        this.qq = qq;
        this.role = role;
        this.isUse = isUse;
        this.registrationTime = registsterTimp;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Timestamp getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Timestamp registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", role=" + role +
                ", isUse=" + isUse +
                ", registrationTime=" + registrationTime +
                ", currPage=" + currPage +
                ", pageSize=" + pageSize +
                ", time='" + time + '\'' +
                ", isFollow=" + isFollow +
                ", followCount=" + followCount +
                ", blogCount=" + blogCount +
                '}';
    }
}
