package com.person.blog.entity;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * 关注类
 */
public class Follow {
    private Integer id;
    private Integer follower;
    private Integer followed;
    private Timestamp followTime;

    private User user_er;
    private User user_ed;
    private String time;

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", follower=" + follower +
                ", followed=" + followed +
                ", followTime=" + followTime +
                ", user_er=" + user_er +
                ", user_ed=" + user_ed +
                ", time='" + time + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public Integer getFollowed() {
        return followed;
    }

    public void setFollowed(Integer followed) {
        this.followed = followed;
    }

    public Timestamp getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Timestamp followTime) {
        this.followTime = followTime;
    }

    public User getUser_er() {
        return user_er;
    }

    public void setUser_er(User user_er) {
        this.user_er = user_er;
    }

    public User getUser_ed() {
        return user_ed;
    }

    public void setUser_ed(User user_ed) {
        this.user_ed = user_ed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Follow() {
    }
}
