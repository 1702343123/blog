package com.person.blog.mapper;

import com.person.blog.entity.Collect;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CollectionMapper {
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "userID", property = "userID"),
            @Result(column = "blogID", property = "blogID"),
            @Result(column = "collectTime", property = "collectTime")
    })

    @Select("select * from collect")
    List<Collect> getCollectionList();

    //根据userID查询自己的收藏列表
    @Select("select * from collect where userID=#{userID} and blogID is not null order by collectTime desc")
    List<Collect> getCollectionById(Integer userID);

    //根据blogID查询收藏列表
    @Select("select * from collect where blogID=#{blogID} and userID is not null order by collectTime desc")
    List<Collect> getCollectByBlog(Integer blogID);

    //根据blogID、userID查询收藏列表
    @Select("select * from collect where blogID=#{blogID} and userID=#{userID}")
    List<Collect> getIsCollect(Integer blogID,Integer userID);

    //收藏
    @Insert("insert into collect(userID,blogID,collectTime) values (#{userID},#{blogID},#{collectTime})")
    int addCollect(Collect collect);

    //取消收藏
    @Delete("delete from collect where userID=#{userID} and blogID=#{blogID}")
    int deleteCollect(Integer userID,Integer blogID);

    //查询收藏数前五的blog
    @Select("select * from collect group by blogID order by count(userID) desc limit 5")
    List<Collect> topCollect();
}
