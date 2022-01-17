package com.person.blog.serviceImpl;

import com.person.blog.entity.*;
import com.person.blog.entity.dto.PageDTO;
import com.person.blog.mapper.*;
import com.person.blog.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

@Service
public class BlogServiceImpl {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private ImgMapper imgMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogTypeMapper blogTypeMapper;
    @Autowired
    private CollectionMapper collectionMapper;

    //管理员获取全部Blog
    public ResponseResult getBlogList(Integer currPage, Integer pageSize) {
        List<Blog> blogs = blogMapper.getBlogLit();
        Integer first=(currPage-1)*pageSize;
        Integer last=currPage*pageSize;
        int size = blogs.size();
        if (last>size){
            last=size;
        }
        List resultList = blogs.subList(first, last);
        List<Blog> blogList = resultList;
        Iterator<Blog> iterator=blogList.iterator();
        while (iterator.hasNext()){
            Blog blog=iterator.next();
            User autor=userMapper.getUserById(blog.getUserID());
            blog.setAutor(autor);
            Timestamp createTime = blog.getCreateTime();
            if (createTime != null) {
                blog.setTime(StringUtil.getDateString(createTime));
            }
            //获取博客的评论数
            List<Comment> comments=commentMapper.getCommentByBlog(blog.getBlogID());
            blog.setCommentAmount(comments.size());
            //获得博客种类
            BlogType type=blogTypeMapper.getTypeById(blog.getId());
            if (type.getTypeName()!=null){
                blog.setSort(type.getTypeName());
            }
            //收藏了该博客人数
            List<Collect> collects=collectionMapper.getCollectByBlog(blog.getBlogID());
            Iterator<Collect> iterator2=collects.iterator();
            while (iterator2.hasNext()){
                Collect collect2=iterator2.next();
                User user=userMapper.getUserById(collect2.getUserID());
                if (user.getIsUse()==1){
                    iterator2.remove();
                }
            }
            blog.setCollectCount(collects.size());
            List<Img> imgs=imgMapper.getImgByBlogID(blog.getBlogID());
            blog.setImgs(imgs);
        }
        return ResponseResult.succ(blogList, blogs.size());
    }

    //查询所有博客列表
    public ResponseResult getAllBlog(Integer currPage, Integer pageSize) {
        List<Blog> blogs = blogMapper.getBlogLit();
        Integer first=(currPage-1)*pageSize;
        Integer last=currPage*pageSize;
        int size = blogs.size();
        if (last>size){
            last=size;
        }
        List resultList = blogs.subList(first, last);
        List<Blog> blogList = resultList;
        Iterator<Blog> iterator=blogList.iterator();
        while (iterator.hasNext()){
            Blog blog=iterator.next();
            User autor=userMapper.getUserById(blog.getUserID());
            if (autor.getIsUse()==0){
                Timestamp createTime = blog.getCreateTime();
                if (createTime != null) {
                    blog.setTime(StringUtil.getDateString(createTime));
                }
                //获取博客的评论数
                List<Comment> comments=commentMapper.getCommentByBlog(blog.getBlogID());
                blog.setCommentAmount(comments.size());
                //获得博客种类
                BlogType type=blogTypeMapper.getTypeById(blog.getId());
                if (type.getTypeName()!=null){
                    blog.setSort(type.getTypeName());
                }
                //收藏了该博客人数
                List<Collect> collects=collectionMapper.getCollectByBlog(blog.getBlogID());
                Iterator<Collect> iterator2=collects.iterator();
                while (iterator2.hasNext()){
                    Collect collect2=iterator2.next();
                    User user=userMapper.getUserById(collect2.getUserID());
                    if (user.getIsUse()==1){
                        iterator2.remove();
                    }
                }
                blog.setCollectCount(collects.size());
                List<Img> imgs=imgMapper.getImgByBlogID(blog.getBlogID());
                String imgUrl="";
                if (imgs.size()!=0){
                    imgUrl=imgs.get(0).getImgUrl();
                }
                blog.setImgs(imgs);
                blog.setImgUrl(imgUrl);
            }else{
                iterator.remove();
            }
        }
        return ResponseResult.succ(blogList, blogs.size());
    }

    @Autowired
    private FollowMapper followMapper;
    //根据blogID查询详情
    public ResponseResult getBlogByID(Integer blogID ,Integer userID) {
        Blog blog= blogMapper.getBlogByID(blogID);
        User autor=userMapper.getUserById(blog.getUserID());
        //判断登录用户是否作者
        if (userID!=null){
            if (userID!=autor.getUserID()){
                List<Follow> followList=followMapper.getIsFollowed(autor.getUserID(),userID);
                if (followList.size()==0){
                    autor.setIsFollow(0);
                }else{
                    autor.setIsFollow(1);
                }
            }else{
                autor.setIsFollow(2);
            }
        }else{
            autor.setIsFollow(0);
        }
        blog.setAutor(autor);
        List<Img> imgs=imgMapper.getImgByBlogID(blog.getBlogID());
        String imgUrl="";
        if (imgs.size()!=0){
            imgUrl=imgs.get(0).getImgUrl();
        }
        blog.setImgs(imgs);
        blog.setImgUrl(imgUrl);
        blog.setComments(commentMapper.getCommentByBlog(blog.getBlogID()));
        Timestamp createTime = blog.getCreateTime();
        if (createTime != null) {
            blog.setTime(StringUtil.getDateString(createTime));
        }
        //获取博客的评论
        List<Comment> comments=blog.getComments();
        blog.setCommentAmount(comments.size());
        if (comments!=null){
            for (Comment comment:comments){
                Timestamp replyTime=comment.getReplyTime();
                if (replyTime!=null)
                    comment.setTime(StringUtil.getDateString(replyTime));
                //获取评论的信息
                User user=userMapper.getUserById(comment.getUserID());
                comment.setUserName(user.getUserName());
                comment.setHeadUrl(user.getHeadUrl());
            }
        }
        //获得博客种类
        BlogType type=blogTypeMapper.getTypeById(blog.getId());
        if (type.getTypeName()!=null){
            blog.setSort(type.getTypeName());
        }
        //判断登录用户是否收藏
        if (blog.getUserID()==userID){
            blog.setIsCollect(2);
        }else{
            List<Collect> collectList=collectionMapper.getIsCollect(blogID,userID);
            if (collectList.size()==0){
                blog.setIsCollect(0);
            }else{
                blog.setIsCollect(1);
            }
        }
        //收藏了该博客人数
        List<Collect> collects=collectionMapper.getCollectByBlog(blogID);
        Iterator<Collect> iterator=collects.iterator();
        while (iterator.hasNext()){
            Collect collect=iterator.next();
            User user=userMapper.getUserById(collect.getUserID());
            if (user.getIsUse()==1){
                iterator.remove();
            }
        }
        blog.setCollectCount(collects.size());
        return ResponseResult.success(blog);
    }

    //根据种类ID查询博客列表
    public ResponseResult getBlogListByType(Integer id,Integer currPage, Integer pageSize) {
        List<Blog> blogs=null;
        if(id==0){
            blogs=blogMapper.getBlogLit();
        }else{
            blogs = blogMapper.getBlogListByType(id);
        }
        Integer first=(currPage-1)*pageSize;
        Integer last=currPage*pageSize;
        int size = blogs.size();
        if (last>size){
            last=size;
        }
        List resultList = blogs.subList(first, last);
        List<Blog> blogList = resultList;
        Iterator<Blog> iterator=blogList.iterator();
        while (iterator.hasNext()){
            Blog blog=iterator.next();
            User author=userMapper.getUserById(blog.getUserID());
            if (author.getIsUse()==0){
                List<Img> imgs=imgMapper.getImgByBlogID(blog.getBlogID());
                String imgUrl="";
                if (imgs.size()!=0){
                    imgUrl=imgs.get(0).getImgUrl();
                }
                blog.setImgs(imgs);
                blog.setImgUrl(imgUrl);
                Timestamp createTime = blog.getCreateTime();
                if (createTime != null) {
                    blog.setTime(StringUtil.getDateString(createTime));
                }
                //获取博客的评论数
                List<Comment> comments=commentMapper.getCommentByBlog(blog.getBlogID());
                blog.setCommentAmount(comments.size());
                //获得博客种类
                BlogType type=blogTypeMapper.getTypeById(blog.getId());
                if (type.getTypeName()!=null){
                    blog.setSort(type.getTypeName());
                }
                //收藏了该博客人数
                List<Collect> collects=collectionMapper.getCollectByBlog(blog.getBlogID());
                Iterator<Collect> iterator2=collects.iterator();
                while (iterator2.hasNext()){
                    Collect collect2=iterator2.next();
                    User user=userMapper.getUserById(collect2.getUserID());
                    if (user.getIsUse()==1){
                        iterator2.remove();
                    }
                }
                blog.setCollectCount(collects.size());
            }else{
                iterator.remove();
            }
        }
        return ResponseResult.succ(blogList, blogs.size());
    }

    //根据userID查询博客列表
    public ResponseResult getBlogListByUser(Integer userID,Integer currPage, Integer pageSize) {
        List<Blog> blogs = blogMapper.getBlogListByUser(userID);
        Integer first=(currPage-1)*pageSize;
        Integer last=currPage*pageSize;
        int size = blogs.size();
        if (last>size){
            last=size;
        }
        List resultList = blogs.subList(first, last);
        List<Blog> blogList = resultList;
        for (Blog blog : blogList) {
            blog.setImgs(imgMapper.getImgByBlogID(blog.getBlogID()));
            Timestamp createTime = blog.getCreateTime();
            if (createTime != null) {
                blog.setTime(StringUtil.getDateString(createTime));
            }
            //获取博客的评论数
            List<Comment> comments=commentMapper.getCommentByBlog(blog.getBlogID());
            blog.setCommentAmount(comments.size());
            //获得博客种类
            BlogType type=blogTypeMapper.getTypeById(blog.getId());
            if (type.getTypeName()!=null){
                blog.setSort(type.getTypeName());
            }
            //收藏了该博客人数
            List<Collect> collects=collectionMapper.getCollectByBlog(blog.getBlogID());
            Iterator<Collect> iterator=collects.iterator();
            while (iterator.hasNext()){
                Collect collect2=iterator.next();
                User user=userMapper.getUserById(collect2.getUserID());
                if (user.getIsUse()==1){
                    iterator.remove();
                }
            }
            blog.setCollectCount(collects.size());
        }
        return ResponseResult.succ(blogList, blogs.size());
    }

    //根据blogID删除博客
    public ResponseResult deleteBlog(Integer blogID){
        if (blogMapper.deleteBlog(blogID)==1){
            return new ResponseResult(StatusConst.SUCCESS, MsgConst.SUCCESS);
        }else{
            return new ResponseResult(StatusConst.ERROR,MsgConst.FAIL);
        }
    }

    //创建博客
    public ResponseResult createBlog(Integer userID,String blogName,Integer id,String content,String[] imgs){
        Blog blog=new Blog();
        blog.setUserID(userID);
        blog.setBlogName(blogName);
        blog.setId(id);
        blog.setContent(content);
        blog.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int i=blogMapper.createBlog(blog);
        if (i==1){
            if (imgs!=null){
                System.out.println("blogID:"+blog.getBlogID());
                for (String image:imgs){
                    Img img=new Img();
                    img.setBlogID(blog.getBlogID());
                    img.setImgUrl(image);
                    imgMapper.insertBlogImg(img);
                    System.out.println(image);
                }
            }
        }
        return ResponseResult.success();
    }

    //获取收藏数前五的blog
    public ResponseResult topCollect(){
        List<Collect> collectList=collectionMapper.topCollect();
        for (Collect collect:collectList){
            Blog blog=blogMapper.getBlogByID(collect.getBlogID());

            Timestamp createTime = blog.getCreateTime();
            if (createTime != null) {
                blog.setTime(StringUtil.getDateString(createTime));
            }
            //获取博客的评论
            List<Comment> comments=commentMapper.getCommentByBlog(blog.getBlogID());
            blog.setCommentAmount(comments.size());
            //获得博客种类
            BlogType type=blogTypeMapper.getTypeById(blog.getId());
            if (type.getTypeName()!=null){
                blog.setSort(type.getTypeName());
            }
            //收藏了该博客人数
            List<Collect> collects=collectionMapper.getCollectByBlog(blog.getBlogID());
            Iterator<Collect> iterator=collects.iterator();
            while (iterator.hasNext()){
                Collect collect2=iterator.next();
                User user=userMapper.getUserById(collect2.getUserID());
                if (user.getIsUse()==1){
                    iterator.remove();
                }
            }
            blog.setCollectCount(collects.size());
            List<Img> imgs=imgMapper.getImgByBlogID(blog.getBlogID());
            String imgUrl="";
            if (imgs.size()!=0){
                imgUrl=imgs.get(0).getImgUrl();
            }
//            blog.setImgs(imgs);
            blog.setImgUrl(imgUrl);
            collect.setBlog(blog);
        }
        return ResponseResult.succ(collectList,collectList.size());
    }

    //获取所有种类列表
    public ResponseResult getTypeList(){
        List<BlogType> typeList=blogTypeMapper.getTybeList();
        return ResponseResult.succ(typeList,typeList.size());
    }
}
