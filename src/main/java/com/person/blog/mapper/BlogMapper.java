package com.person.blog.mapper;

import com.person.blog.entity.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BlogMapper {
    @Results({
            @Result(column = "blogID", property = "blogID"),
            @Result(column = "userID", property = "userID"),
            @Result(column = "blogName", property = "blogName"),
            @Result(column = "id", property = "id"),
            @Result(column = "content", property = "content"),
            @Result(column = "createTime", property = "createTime")
    })

    //查询所有博客列表
    @Select("select * from blog order by createTime desc")
    List<Blog> getBlogLit();

    //根据blogID查询详情
    @Select("select * from blog where blogID=#{blogID}")
    Blog getBlogByID(@Param("blogID") Integer blogID);

    //根据种类ID查询博客列表
    @Select("select * from blog where id=#{id} order by createTime desc")
    List<Blog> getBlogListByType(@Param("id") Integer id);

    //根据用户id博客列表
    @Select("select * from blog where userID=#{userID} order by createTime desc")
    List<Blog> getBlogListByUser(@Param("userID") Integer userID);

    //根据blogID删除博客
    @Delete("delete from blog where blogID=#{blogID}")
    int deleteBlog(Integer blogID);

    //创建博客
    @Insert({"insert into blog(userID,blogName,id,content,createTime) values(#{userID},#{blogName},#{id},#{content},#{createTime})"})
    @Options(useGeneratedKeys=true, keyProperty="blogID")
    int createBlog(Blog blog);
}
