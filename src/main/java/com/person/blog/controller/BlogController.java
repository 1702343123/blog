package com.person.blog.controller;

import com.person.blog.entity.Blog;
import com.person.blog.mapper.BlogMapper;
import com.person.blog.serviceImpl.BlogServiceImpl;
import com.person.blog.util.Client;
import com.person.blog.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.ValueExp;

@RestController
@Api(tags = "2.博客模块")
@RequestMapping(value = "/api/blog")
@Client
public class BlogController {
    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @ApiOperation(value = "管理员查询所有博客")
    @GetMapping(value = "getBlogList")
    public ResponseResult getBlogList(@RequestParam(defaultValue = "1") Integer currPage,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return blogServiceImpl.getBlogList(currPage, pageSize);
    }

    @ApiOperation(value = "查询所有博客")
    @GetMapping(value = "getAllBlog")
    public ResponseResult getAllBlog(@RequestParam(defaultValue = "1") Integer currPage,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return blogServiceImpl.getAllBlog(currPage, pageSize);
    }

    @ApiOperation(value = "根据blogID查询博客详情")
    @GetMapping(value = "getBlogByID")
    public ResponseResult getBlogByID(Integer blogID,Integer userID){
        return blogServiceImpl.getBlogByID(blogID,userID);
    }

    @ApiOperation(value = "根据种类ID查询博客列表")
    @GetMapping(value = "getBlogListByType")
    public ResponseResult getBlogListByType(Integer typeID,Integer currPage,Integer pageSize){
        return blogServiceImpl.getBlogListByType(typeID,currPage,pageSize);
    }

    @ApiOperation(value = "根据userID查询博客列表")
    @GetMapping(value = "/getBlogListByUser")
    public ResponseResult getBlogListByUser(Integer userID,Integer currPage,Integer pageSize){
        return blogServiceImpl.getBlogListByUser(userID,currPage,pageSize);
    }

    @ApiOperation(value = "根据blogID删除博客")
    @DeleteMapping(value = "deleteBlog")
    public ResponseResult deleteBlog(Integer blogID){
        return blogServiceImpl.deleteBlog(blogID);
    }

    @ApiOperation(value = "发表博客")
    @PostMapping(value = "createBlog")
    public ResponseResult createBlog(Integer userID,String blogName,Integer id,String content,String[] imgs){
        return blogServiceImpl.createBlog(userID,blogName,id,content,imgs);
    }

    /**
     * 获取收藏数前五的blog
     */
    @ApiOperation(value = "收藏数前五的blog")
    @GetMapping(value = "/topCollect")
    public ResponseResult topCollect(){
        return blogServiceImpl.topCollect();
    }

    /**
     * 获取所有种类列表
     */
    @ApiOperation(value = "获取所有种类列表")
    @GetMapping(value = "/getTypeList")
    public ResponseResult getTypeList(){
        return blogServiceImpl.getTypeList();
    }
}
