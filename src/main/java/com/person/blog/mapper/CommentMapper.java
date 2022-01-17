package com.person.blog.mapper;

import com.person.blog.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommentMapper {
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "content", property = "content"),
            @Result(column = "blogID", property = "blogID"),
            @Result(column = "userID", property = "userID"),
            @Result(column = "replyTime", property = "replyTime")
    })

    //根据userID查询评论列表
    @Select("select * from comment where userID=#{userID}")
    List<Comment> getCommentByUser(Integer userID);

    //根据blogID查询评论列表
    @Select("select * from comment where blogID=#{blogID} order by replyTime")
    List<Comment> getCommentByBlog(Integer blogID);

    //根据id删除评论
    @Delete("delete from comment where id=#{id}")
    int deleteComment(Integer id);

    //发表评论
    @Insert("insert into comment(content,blogID,userID,replyTime) values(#{content},#{blogID},#{userID},#{replyTime})")
    int insertComment(Comment comment);

    //查询所有评论列表
    @Select("select * from comment order by replyTime desc")
    List<Comment> getAllComment();
}
