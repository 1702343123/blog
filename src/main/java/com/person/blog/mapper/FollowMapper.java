package com.person.blog.mapper;

import com.person.blog.entity.Follow;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FollowMapper {
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "follower", property = "follower"),
            @Result(column = "followed", property = "followed"),
            @Result(column = "followTime", property = "followTime")
    })

    @Select("select * from follow")
    List<Follow> getFollow();

    //根据userID查询自己的关注列表
    @Select("select * from follow where follower=#{userID} and followed is not null order by followTime desc")
    List<Follow> getFollowByUser(Integer userID);

    //根据userID查询关注了我的列表
    @Select("select * from follow where followed=#{userID} and follower is not null order by followTime desc")
    List<Follow> getFollowedByUser(Integer userID);

    //是否关注
    @Select("select * from follow where followed=#{user_ed} and follower=#{user_er}")
    List<Follow> getIsFollowed(Integer user_ed,Integer user_er);

    //关注
    @Insert("insert into follow(follower,followed,followTime) values (#{follower},#{followed},#{followTime})")
    int addFollow(Follow follow);

    //取消关注
    @Delete("delete from follow where follower=#{follower} and followed=#{followed}")
    int deleteFollow(Integer follower,Integer followed);

    //获取关注数前五的用户
    @Select("select * from follow group by followed order by count(follower) desc limit 5")
    List<Follow> topFollow(Integer loginID);
}
