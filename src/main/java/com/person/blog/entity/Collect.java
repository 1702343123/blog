package com.person.blog.entity;

import java.sql.Timestamp;

/**
 * 收藏类
 */
public class Collect {
    private Integer id;
    private Integer userID;
    private Integer blogID;
    private Timestamp collectTime;

    private String time;
    private User user;
    private Blog blog;

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", userID=" + userID +
                ", blogID=" + blogID +
                ", collectTime=" + collectTime +
                ", time='" + time + '\'' +
                ", user=" + user +
                ", blog=" + blog +
                '}';
    }

    public Collect() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getBlogID() {
        return blogID;
    }

    public void setBlogID(Integer blogID) {
        this.blogID = blogID;
    }

    public Timestamp getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Timestamp collectTime) {
        this.collectTime = collectTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
