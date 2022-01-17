package com.person.blog.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 评论类
 */
public class Comment implements Serializable {
    private Integer id;
    private String content;
    private Integer blogID;
    private Integer userID;
    private Timestamp replyTime;

    private String time;
    private User user;
    private String userName;
    private String headUrl;
    private Blog blog;

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", blogID=" + blogID +
                ", userID=" + userID +
                ", replyTime=" + replyTime +
                ", time='" + time + '\'' +
                ", user=" + user +
                ", userName='" + userName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", blog=" + blog +
                '}';
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getBlogID() {
        return blogID;
    }

    public void setBlogID(Integer blogID) {
        this.blogID = blogID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Timestamp getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Timestamp replyTime) {
        this.replyTime = replyTime;
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
}
