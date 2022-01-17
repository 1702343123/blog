package com.person.blog.serviceImpl;

import com.person.blog.entity.Comment;
import com.person.blog.mapper.CommentMapper;
import com.person.blog.util.MsgConst;
import com.person.blog.util.ResponseResult;
import com.person.blog.util.StatusConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CommentServiceImpl {
    @Autowired
    private CommentMapper commentMapper;

    //删除评论
    public ResponseResult deleteComment(Integer id){
        int i=commentMapper.deleteComment(id);
        if (i==1){
            return new ResponseResult(StatusConst.SUCCESS, "删除成功");
        }else{
            return ResponseResult.error(StatusConst.ERROR,MsgConst.FAIL);
        }
    }

    //添加评论
    public ResponseResult addComment(Integer userID,Integer blogID,String content){
        Comment comment=new Comment();
        comment.setUserID(userID);
        comment.setBlogID(blogID);
        comment.setContent(content);
        comment.setReplyTime(new Timestamp(System.currentTimeMillis()));
        int index=commentMapper.insertComment(comment);
        if (index==1){
            return new ResponseResult(StatusConst.SUCCESS,"评论成功");
        }else{
            return ResponseResult.error(StatusConst.ERROR,MsgConst.FAIL);
        }
    }
}
