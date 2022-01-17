package com.person.blog.controller;

import com.person.blog.entity.Comment;
import com.person.blog.entity.User;
import com.person.blog.entity.dto.UserCode;
import com.person.blog.entity.dto.UserDTO;
import com.person.blog.mapper.CommentMapper;
import com.person.blog.service.UserService;
import com.person.blog.serviceImpl.CommentServiceImpl;
import com.person.blog.serviceImpl.UserServiceImpl;
import com.person.blog.util.Client;
import com.person.blog.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "1.用户模块")
@RequestMapping(value = "/api/user")
@Client
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @ApiOperation(value = "用户列表")
    @GetMapping(value = "/userList")
    public ResponseResult getAll(){
        List<User> users=userService.selectAll();
        return ResponseResult.success(users);
    }

    @ApiOperation(value = "后台-查询所有用户")
    @GetMapping(value = "getAllUser")
    public ResponseResult getAllUser(@RequestParam(defaultValue = "1") Integer currPage,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return userServiceImpl.getAllUser(currPage, pageSize);
    }

    @ApiOperation(value = "用户详情(id)")
    @GetMapping(value = "/userDetail")
    public ResponseResult getUserById(Integer user_er,Integer user_ed){
        User user=userService.getUserById(user_er,user_ed);
        return ResponseResult.success(user);
    }

    //    根据id查询要添加的用户信息
    @ApiOperation(value = "我的详细资料/其他用户的资料")
    @GetMapping(value = "getUserMsgById")
    public ResponseResult getUserMsgById(Integer user_er,Integer user_ed) {
        return userServiceImpl.getUserMsg(user_er,user_ed);
    }

    @ApiOperation(value = "用户详情(phoneNumber)")
    @GetMapping(value = "/userDetail2{phoneNumber}")
    public ResponseResult getUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber){
        User user=userService.getUserByPhoneNumber(phoneNumber);
        return ResponseResult.success(user);
    }

    /**
     * 登录
     */
    @ApiOperation(value = "登录", notes = "输入手机号和密码进行登录")
    @PostMapping(value = "/signIn")
    public ResponseResult signIn(@RequestBody UserDTO userDTO) {
        return userServiceImpl.userSignIn(userDTO);
    }

    /**
     * 注册
     */
    @ApiOperation(value = "注册", notes = "填写注册信息")
    @PostMapping(value = "signUp")
    public ResponseResult signUp(String phoneNumber,String password,String headUrl,String userName) {
        return userServiceImpl.signUp(phoneNumber,password,headUrl,userName);
    }

    /**
     * 更新我的资料
     */
    @ApiOperation(value = "更新我的资料", notes = "修改我的资料并保存")
    @PostMapping(value = "updateMyMsg")
    public ResponseResult updateMyMsg(Integer userID,String userName,String headUrl,String realName,String sex,String address,String email,String qq) {
        return userServiceImpl.changeMsg(userID,userName,headUrl,realName,sex,address,email,qq);
    }

    /**
     * 修改密码
     */
    @ApiOperation(value = "修改密码")
    @PostMapping(value = "updatePassword")
    public ResponseResult updatePassword(Integer userID,String password){
        return userServiceImpl.updatePassword(userID,password);
    }

    /**
     * 注销账号
     */
    @ApiOperation(value = "注销账号")
    @GetMapping(value = "cancelUser")
    public ResponseResult cancelUser(Integer userID){
        return userServiceImpl.cancelUser(userID);
    }

    /**
     * 删除账号
     */
    @ApiOperation(value = "删除账号")
    @DeleteMapping(value = "deleteUser")
    public ResponseResult deleteUser(Integer userID){
        return userServiceImpl.deleteUser(userID);
    }

    /**
     * 检测短信验证码是否相同
     */
    @ApiOperation(value = "验证验证码", notes = "将手机号和验证码进行匹配")
    @PostMapping(value = "/matchVerify")
    public ResponseResult matchVerify(@RequestBody UserCode userCode) throws Exception {
        return userServiceImpl.matchVerifySignUp(userCode);
    }

    /**
     * 发送验证码
     */
    @ApiOperation(value = "发送验证码", notes = "通过手机号获取验证码")
    @PostMapping(value = "/sendCode")
    public ResponseResult sendCode(@RequestBody UserCode userCode) {
        return userServiceImpl.sendVerify(userCode);
    }

    /**
     * 查询我的收藏
     */
    @ApiOperation(value = "根据userID查询自己的收藏列表")
    @GetMapping(value = "/getCollectListByUser")
    public ResponseResult getCollectListByUser(Integer userID,Integer currPage, Integer pageSize){
        return userServiceImpl.getCollectListByUser(userID,currPage,pageSize);
    }

    /**
     * 查询我的关注
     */
    @ApiOperation(value = "根据userID查询自己的关注列表")
    @GetMapping(value = "/getFollowByUser")
    public ResponseResult getFollowByUser(Integer userID,Integer currPage, Integer pageSize){
        return userServiceImpl.getFollowByUser(userID,currPage,pageSize);
    }

    /**
     * 收藏
     */
    @ApiOperation(value = "收藏")
    @PostMapping(value = "/addCollect")
    public ResponseResult addCollect(Integer userID,Integer blogID){
        return userServiceImpl.addCollect(userID,blogID);
    }

    /**
     * 取消收藏
     */
    @ApiOperation(value = "取消收藏")
    @DeleteMapping(value = "/deleteCollect")
    public ResponseResult deleteCollect(Integer userID,Integer blogID){
        return userServiceImpl.deleteCollect(userID,blogID);
    }

    /**
     * 关注
     */
    @ApiOperation(value = "关注")
    @PostMapping(value = "/addFollow")
    public ResponseResult addFollow(Integer follower,Integer followed){
        return userServiceImpl.addFollow(follower,followed);
    }

    /**
     * 取消关注
     */
    @ApiOperation(value = "取消关注")
    @DeleteMapping(value = "/deleteFollow")
    public ResponseResult deleteFollow(Integer follower,Integer followed){
        return userServiceImpl.deleteFollow(follower,followed);
    }

    /**
     * 评论
     */
    @ApiOperation(value = "评论")
    @PostMapping(value = "/insertComment")
    public ResponseResult insertComment(Integer userID,Integer blogID,String content){
        return commentServiceImpl.addComment(userID,blogID,content);
    }

    /**
     * 删除评论
     */
    @ApiOperation(value = "删除评论")
    @DeleteMapping(value = "/deleteComment")
    public ResponseResult deleteComment(Integer id){
        return commentServiceImpl.deleteComment(id);
    }

    /**
     * 获取关注数前五用户
     */
    @ApiOperation(value = "获取关注数前五的用户")
    @GetMapping(value = "/topFollow")
    public ResponseResult topFollow(Integer loginID){
        return userServiceImpl.topFollow(loginID);
    }

    @ApiOperation(value = "所有评论列表")
    @GetMapping(value = "/getAllComment")
    public ResponseResult getAllComment(@RequestParam(defaultValue = "1") Integer currPage,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return userServiceImpl.getAllComment(currPage, pageSize);
    }
}
