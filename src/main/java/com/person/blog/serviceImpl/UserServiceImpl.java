package com.person.blog.serviceImpl;

import com.person.blog.entity.*;
import com.person.blog.entity.dto.PageDTO;
import com.person.blog.entity.dto.UserCode;
import com.person.blog.entity.dto.UserDTO;
import com.person.blog.mapper.*;
import com.person.blog.service.UserService;
import com.person.blog.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Qualifier("redisTemplate")//实例化
    private RedisTemplate<Object, Object> rts;

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private BlogTypeMapper blogTypeMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<User> selectAll() {
        List<User> users=userMapper.getUserList();
        return users;
    }

    public ResponseResult getAllUser(Integer currPage, Integer pageSize) {
        List<User> users = userMapper.getUserList();
        Integer first=(currPage-1)*pageSize;
        Integer last=currPage*pageSize;
        int size = users.size();
        if (last>size){
            last=size;
        }
        List resultList = users.subList(first, last);
        List<User> userList = resultList;
        for (User user : userList) {
            Timestamp registrationTime = user.getRegistrationTime();
            if (registrationTime != null) {
                user.setTime(StringUtil.getDateString(registrationTime));
            }
            //显示关注自己的人数
            List<Follow> follows=followMapper.getFollowedByUser(user.getUserID());
            Iterator<Follow> iterator=follows.iterator();
            while (iterator.hasNext()){
                Follow follow=iterator.next();
                User usered=userMapper.getUserById(follow.getFollower());
                if (usered.getIsUse()==1){
                    iterator.remove();
                }
            }
            user.setFollowCount(follows.size());
            List<Blog> blogs=blogMapper.getBlogListByUser(user.getUserID());
            user.setBlogCount(blogs.size());
        }
        return ResponseResult.succ(userList, users.size());
    }

    @Override
    public User getUserById(Integer user_er,Integer user_ed) {
        User user=userMapper.getUserById(user_er);
        Timestamp registrationTime = user.getRegistrationTime();
        if (registrationTime != null) {
            user.setTime(StringUtil.getDateString(registrationTime));
        }
        //显示关注自己的人数
        List<Follow> follows=followMapper.getFollowedByUser(user_er);
        Iterator<Follow> iterator=follows.iterator();
        while (iterator.hasNext()){
            Follow follow=iterator.next();
            User usered=userMapper.getUserById(follow.getFollower());
            if (usered.getIsUse()==1){
                iterator.remove();
            }
        }
        user.setFollowCount(follows.size());
        //判断登录用户是否关注
        if (user_er!=user_ed){
            List<Follow> followList=followMapper.getIsFollowed(user_er,user_ed);
            if (followList.size()==0){
                user.setIsFollow(0);
            }else {
                user.setIsFollow(1);
            }
        }else{
            user.setIsFollow(0);
        }
        return user;
    }

    public ResponseResult getUserMsg(Integer user_er,Integer user_ed) {
        User user = userMapper.getUserById(user_er);
        if (user != null) {
            Timestamp regitsterTime = user.getRegistrationTime();
            String time = StringUtil.getDateString(regitsterTime);
            user.setTime(time);
            //显示关注自己的人数
            List<Follow> follows=followMapper.getFollowedByUser(user_er);
            Iterator<Follow> iterator=follows.iterator();
            while (iterator.hasNext()){
                Follow follow=iterator.next();
                User usered=userMapper.getUserById(follow.getFollower());
                if (usered.getIsUse()==1){
                    iterator.remove();
                }
            }
            user.setFollowCount(follows.size());
            //判断是否被登录用户关注
            if (user_er!=user_ed){
                List<Follow> followList=followMapper.getIsFollowed(user_er,user_ed);
                if (followList.size()==0){
                    user.setIsFollow(0);
                }else {
                    user.setIsFollow(1);
                }
            }else{
                user.setIsFollow(0);
            }
            return ResponseResult.success(user);
        } else
            return ResponseResult.error(StatusConst.ERROR, MsgConst.FAIL);
    }


    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userMapper.getUserByPhoneNumber(phoneNumber);
    }

    @Override
    public int signIn(UserDTO userDTO) {
        User user = userMapper.getUserByPhoneNumber(userDTO.getPhoneNumber());
        if (user != null&&user.getIsUse()
                ==0) {
                if (user.getPassword().equals(userDTO.getPassword())) {
//                 登录成功
//                 userMapper.addScore(user.getId(),10);
                    return StatusConst.SUCCESS;
                } else {
//                 密码不正确
                    return StatusConst.PASSWORD_ERROR;
                }
        } else {
//         手机号不存在
            return StatusConst.USER_MOBILE_NOT_FOUND;
        }
    }

    @Override
    public int signUp(UserDTO userDTO) {
        return 0;
    }

    //登录
    public ResponseResult userSignIn(UserDTO userDTO) {
        if (userDTO.getPhoneNumber() == null) {
            return new ResponseResult(StatusConst.PHONE_NULL_ERROR, MsgConst.PHONE_NUMBER_NULL);
        } else if (!RegexUtil.phoneNumberRegex(userDTO.getPhoneNumber())) {
            return new ResponseResult(StatusConst.PHONE_VALIDATOR_ERROR_,
                    MsgConst.PHONE_NUMBER_VALIDATOR);
        } else if (userDTO.getPassword() == null) {
            return new ResponseResult(StatusConst.PASSWORD_NULL,
                    MsgConst.PASSWORD_NULL_);
        } else if (!RegexUtil.passRegex(userDTO.getPassword())) {
            return new ResponseResult(StatusConst.PASSWORD_VALIDATOR, MsgConst.PASSWORD_VALIDATOR);
        } else {
            int status = signIn(userDTO);
            if (status == StatusConst.SUCCESS) {
                User user = userMapper.getUserByPhoneNumber(userDTO.getPhoneNumber());
                return ResponseResult.success(user);
            } else {
                if (status == StatusConst.USER_MOBILE_NOT_FOUND) {
                    //                手机号不存在
                    return ResponseResult.error(status, MsgConst.USER_MOBILE_NO_FOUND);
                } else if (status == StatusConst.USER_STATUS_ERROR) {
                    //                手机号状态异常
                    return ResponseResult.error(status, MsgConst.USER_STATUS_ERROR);
                } else {
//                密码错误
                    return ResponseResult.error(status, MsgConst.PASSWORD_ERROR);
                }
            }

        }
    }

    //注册
    public ResponseResult signUp(String phoneNumber,String password,String headUrl,String userName) {
        User user=userMapper.getUserByPhoneNumber(phoneNumber);
        if (user==null||user.getIsUse()==1){
            if (RegexUtil.passRegex(password)){
                user = new User();
                user.setUserName(userName);
                user.setHeadUrl(headUrl);
                user.setPhoneNumber(phoneNumber);
                user.setPassword(password);
                user.setRegistrationTime(new Timestamp(System.currentTimeMillis()));
                int index = userMapper.singUp(user);
                if (index == 1) {
                    System.out.println(user.toString());
                    return ResponseResult.success();
                } else
                    return ResponseResult.error(StatusConst.ERROR, MsgConst.FAIL);
            }else{
                return new ResponseResult(StatusConst.PASSWORD_VALIDATOR, MsgConst.PASSWORD_VALIDATOR);
            }
        }else{
            return ResponseResult.error(StatusConst.MOBILE_EXIST, MsgConst.MOBILE_EXIST);
        }
    }

    //修改密码
    public int updateMyPwd(User user) {
        int index = userMapper.updatePassword(user);
        System.out.println(index + "=============");
        if (index == 1) {
            return StatusConst.SUCCESS;
        }
        System.out.println("业务层操作失败");
        return StatusConst.ERROR;
    }
    public ResponseResult updatePassword(Integer userID,String password){
        if (userID==null){
            return ResponseResult.error(StatusConst.ERROR,MsgConst.ID_NULL);
        }else{
            if (RegexUtil.passRegex(password)){
                User user=new User();
                user.setUserID(userID);
                user.setPassword(password);
                if (updateMyPwd(user) == StatusConst.SUCCESS) {
                    System.out.println("控制层操作成功");
                    return ResponseResult.success();
                } else {
                    System.out.println("控制层操作失败");
                    return ResponseResult.error(StatusConst.ERROR, MsgConst.FAIL);
                }
            }else{
                return new ResponseResult(StatusConst.PASSWORD_VALIDATOR, MsgConst.PASSWORD_VALIDATOR);
            }
        }
    }

    //            更新我的资料
    public int updateMyDocument(User user) {
        int index = userMapper.updateMyMsg(user);
        System.out.println(index + "=============");
        if (index == 1) {
            return StatusConst.SUCCESS;
        }
        System.out.println("业务层操作失败");
        return StatusConst.ERROR;
    }
    public ResponseResult changeMsg(Integer userID,String userName,String headUrl,String realName,String sex,String address,String email,String qq ) {
        if (userID == null)
            return ResponseResult.error(StatusConst.ERROR, "id不能为空");
        User user = new User();
        user.setUserID(userID);
        user.setUserName(userName);
        user.setHeadUrl(headUrl);
        user.setRealName(realName);
        user.setSex(sex);
        user.setAddress(address);
        user.setEmail(email);
        user.setQq(qq);
        if (updateMyDocument(user) == StatusConst.SUCCESS) {
            System.out.println("控制层操作成功");
            return ResponseResult.success(user);
        } else {
            System.out.println("控制层操作失败");
            return ResponseResult.error(StatusConst.ERROR, MsgConst.FAIL);
        }
    }

    //注销账号
    public ResponseResult cancelUser(Integer userID){
        if (userID==null){
            return ResponseResult.error(StatusConst.ERROR,MsgConst.ID_NULL);
        }else{
            User user=userMapper.getUserById(userID);
            if (user.getIsUse()==0){
                userMapper.cancelUser(userID);
            }else{
                userMapper.qiUser(userID);
            }
            return ResponseResult.success();
        }
    }

    //删除账号
    public ResponseResult deleteUser(Integer userID){
        if (userID==null){
            return ResponseResult.error(StatusConst.ERROR,MsgConst.ID_NULL);
        }else{
            userMapper.deleteUser(userID);
            return ResponseResult.success();
        }
    }

    //验证验证码
    public int plogin(String phoneNumber, String pcode){
        String code = (String) rts.opsForValue().get(phoneNumber);
        System.out.println(code + "----+++++++++++++++++");
        if (code == null) {
            return StatusConst.VERIFYCODE_ERROR;

        } else {
            if (code.equals(pcode)) {
//                验证码正确
                return StatusConst.SUCCESS;
            } else {
//                验证码错误
                System.out.println("验证码错误");
                return StatusConst.VERIFYCODE_ERROR;
            }
        }
    }

    //    验证码验证
    public ResponseResult matchVerifySignUp(UserCode userCode){
        if (userCode.getPhoneNumber() == null) {
            return new ResponseResult(StatusConst.PHONE_NULL_ERROR, MsgConst.PHONE_NUMBER_NULL);

        } else if (!RegexUtil.phoneNumberRegex(userCode.getPhoneNumber())) {
            return new ResponseResult(StatusConst.PHONE_VALIDATOR_ERROR_,
                    MsgConst.PHONE_NUMBER_VALIDATOR);
        } else if (userCode.getCode() == null) {

            return new ResponseResult(StatusConst.VERIFYCODE_NUll, MsgConst.VERIFYCODE_NULL_ERROR);
        } else {
            User users = userMapper.getUserByPhoneNumber(userCode.getPhoneNumber());
            if (users == null||users.getIsUse()==1) {
                int status = plogin(userCode.getPhoneNumber(), userCode.getCode());
                if (status == StatusConst.SUCCESS) {
//            验证码和手机号匹配得上,返回手机号和验证码
                    return ResponseResult.success(userCode);
                } else {
//         验证码错误或失效
                    return ResponseResult.error(StatusConst.VERIFYCODE_ERROR,
                            MsgConst.VERIFYCODE_ERROR);
                }
            } else {
//               手机号码已经被注册
                return ResponseResult.error(StatusConst.MOBILE_EXIST, MsgConst.MOBILE_EXIST);
            }
        }
    }

    //发送并保存验证码
    public int saveCode(String phoneNumber) {

        String newCode = NewCodeUtil.getNewCode();
//            发送
        int send = SMSUtil.send(phoneNumber, newCode);
        if (send == StatusConst.SUCCESS) {
//            保存
            rts.opsForValue().set(phoneNumber, newCode, 15, TimeUnit.DAYS);
            System.out.println(newCode + "+++++++++++++");
            return StatusConst.SUCCESS;
        }
        return StatusConst.VERIFYCODE_ERROR;
    }

    public ResponseResult sendVerify(UserCode userCode) {
        //        发送验证码并保存到redis
        System.out.println(userCode.getPhoneNumber() + "===================");
        if (userCode.getPhoneNumber() == null) {
            return new ResponseResult(StatusConst.PHONE_NULL_ERROR, MsgConst.PHONE_NUMBER_NULL);
        } else if (!RegexUtil.phoneNumberRegex(userCode.getPhoneNumber())) {
            return new ResponseResult(StatusConst.PHONE_VALIDATOR_ERROR_,
                    MsgConst.PHONE_NUMBER_VALIDATOR);
        } else {
            User user = userMapper.getUserByPhoneNumber(userCode.getPhoneNumber());
            if (user != null&&user.getIsUse()==0) {
                return ResponseResult.error(StatusConst.MOBILE_EXIST, MsgConst.MOBILE_EXIST);
            } else {
                int status = saveCode(userCode.getPhoneNumber());
                if (status == StatusConst.SUCCESS)
                    return ResponseResult.success();//            验证码发送成功
                else
                    return ResponseResult.error(StatusConst.ERROR, MsgConst.FAIL);
            }
        }
    }

    //根据userID查询收藏列表
    public ResponseResult getCollectListByUser(Integer userID,Integer currPage, Integer pageSize) {
        if (userID == null) {
            return ResponseResult.error(StatusConst.ERROR, MsgConst.ID_NULL);
        }
        List<Collect> collects = collectionMapper.getCollectionById(userID);
        if (collects == null) {
            return ResponseResult.success();
        }
        Integer first=(currPage-1)*pageSize;
        Integer last=currPage*pageSize;
        int size = collects.size();
        if (last>size){
            last=size;
        }
        List resultList = collects.subList(first, last);
        List<Collect> collectList=resultList;
        for (Collect collect : collectList) {
            Blog blog=blogMapper.getBlogByID(collect.getBlogID());

            //获取博客的评论数
            List<Comment> comments=commentMapper.getCommentByBlog(blog.getBlogID());
            blog.setCommentAmount(comments.size());
            //获得博客种类
            BlogType type=blogTypeMapper.getTypeById(blog.getId());
            if (type.getTypeName()!=null){
                blog.setSort(type.getTypeName());
            }
            //收藏了该博客人数
            List<Collect> collects2=collectionMapper.getCollectByBlog(blog.getBlogID());
            Iterator<Collect> iterator=collects2.iterator();
            while (iterator.hasNext()){
                Collect collect2=iterator.next();
                User user=userMapper.getUserById(collect2.getUserID());
                if (user.getIsUse()==1){
                    iterator.remove();
                }
            }
            blog.setCollectCount(collects2.size());

            collect.setBlog(blog);
            Timestamp collectTime=collect.getCollectTime();
            if (collectTime!=null){
                collect.setTime(StringUtil.getDateString(collectTime));
            }
        }
        return ResponseResult.succ(collectList, collects.size());
    }

    //根据userID查询关注列表
    public ResponseResult getFollowByUser(Integer userID,Integer currPage, Integer pageSize) {
        if (userID == null) {
            return ResponseResult.error(StatusConst.ERROR, MsgConst.ID_NULL);
        }
        List<Follow> follows = followMapper.getFollowByUser(userID);
        if (follows == null) {
            return ResponseResult.success();
        }
        Integer first=(currPage-1)*pageSize;
        Integer last=currPage*pageSize;
        int size = follows.size();
        if (last>size){
            last=size;
        }
        List resultList = follows.subList(first, last);
        List<Follow> followList=resultList;
//        for (Follow follow: followList) {
//            User user_ed=userMapper.getUserById(follow.getFollowed());
//            follow.setUser_ed(user_ed);
//            Timestamp followTime=follow.getFollowTime();
//            if (followTime!=null){
//                follow.setTime(StringUtil.getDateString(followTime));
//            }
//        }
        Iterator<Follow> iterator=followList.iterator();
        while (iterator.hasNext()){
            Follow follow=iterator.next();
            User user_ed=userMapper.getUserById(follow.getFollowed());

            List<Follow> follows2=followMapper.getFollowedByUser(user_ed.getUserID());
            Iterator<Follow> iterator2=follows2.iterator();
            while (iterator2.hasNext()){
                Follow follow2=iterator2.next();
                User usered=userMapper.getUserById(follow2.getFollower());
                if (usered.getIsUse()==1){
                    iterator2.remove();
                }
            }
            user_ed.setFollowCount(follows2.size());

            List<Blog> blogs=blogMapper.getBlogListByUser(user_ed.getUserID());
            user_ed.setBlogCount(blogs.size());

            follow.setUser_ed(user_ed);
            Timestamp followTime=follow.getFollowTime();
            if (followTime!=null){
                follow.setTime(StringUtil.getDateString(followTime));
            }
            if (user_ed.getIsUse()==1){
                iterator.remove();
            }
        }
        return ResponseResult.succ(followList, follows.size());
    }

    //收藏
    public ResponseResult addCollect(Integer userID,Integer blogID){
        Collect collect=new Collect();
        collect.setUserID(userID);
        collect.setBlogID(blogID);
        collect.setCollectTime(new Timestamp(System.currentTimeMillis()));
        if(collectionMapper.addCollect(collect)==1){
            return new ResponseResult(StatusConst.SUCCESS, "收藏成功");
        }else {
            return ResponseResult.error(StatusConst.ERROR, "收藏失败");
        }
    }

    //取消收藏
    public ResponseResult deleteCollect(Integer userID,Integer blogID){
        int index=collectionMapper.deleteCollect(userID,blogID);
        if (index==1){
            return new ResponseResult(StatusConst.SUCCESS,"取消收藏成功");
        }else{
            return ResponseResult.error(StatusConst.ERROR, "取消收藏失败");
        }
    }

    //关注
    public ResponseResult addFollow(Integer follower,Integer followed){
        Follow follow=new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);
        follow.setFollowTime(new Timestamp(System.currentTimeMillis()));
        int index=followMapper.addFollow(follow);
        if (index==1){
            return new ResponseResult(StatusConst.SUCCESS, "关注成功");
        }else{
            return ResponseResult.error(StatusConst.ERROR, "关注失败");
        }
    }

    //取消关注
    public ResponseResult deleteFollow(Integer follower,Integer followed){
        int index=followMapper.deleteFollow(follower,followed);
        if (index==1){
            return new ResponseResult(StatusConst.SUCCESS,"取消关注成功");
        }else{
            return ResponseResult.error(StatusConst.ERROR, "取消关注失败");
        }
    }

    //获取关注数前五的用户
    public ResponseResult topFollow( Integer loginID){
        List<Follow> followList=followMapper.topFollow(loginID);
        for(Follow follow:followList){
            User user=userMapper.getUserById(follow.getFollowed());

            Timestamp regitsterTime = user.getRegistrationTime();
            String time = StringUtil.getDateString(regitsterTime);
            user.setTime(time);
            Timestamp followTime = follow.getFollowTime();
            String follow_time = StringUtil.getDateString(followTime);
            follow.setTime(follow_time);
            //显示关注自己的人数
            List<Follow> follows=followMapper.getFollowedByUser(follow.getFollowed());
            Iterator<Follow> iterator=follows.iterator();
            while (iterator.hasNext()){
                Follow follow2=iterator.next();
                User usered=userMapper.getUserById(follow2.getFollower());
                if (usered.getIsUse()==1){
                    iterator.remove();
                }
            }
            user.setFollowCount(follows.size());
            //判断登录用户是否关注
            if (loginID!=null){
                if (follow.getFollowed()==loginID){
                    user.setIsFollow(2);
                }else{
                    List<Follow> follows2=followMapper.getIsFollowed(follow.getFollowed(),loginID);
                    if (follows2.size()==0){
                        user.setIsFollow(0);
                    }else {
                        user.setIsFollow(1);
                    }
                }
            }else{
                user.setIsFollow(0);
            }

            follow.setUser_ed(user);
        }
        return ResponseResult.succ(followList,followList.size());
    }

    //获取全部评论
    public ResponseResult getAllComment(Integer currPage, Integer pageSize) {
        List<Comment> comments=commentMapper.getAllComment();
        Integer first=(currPage-1)*pageSize;
        Integer last=currPage*pageSize;
        int size = comments.size();
        if (last>size){
            last=size;
        }
        List resultList = comments.subList(first, last);
        List<Comment> commentList = resultList;
        Iterator<Comment> iterator=commentList.iterator();
        while (iterator.hasNext()){
            Comment comment=iterator.next();
            comment.setUser(userMapper.getUserById(comment.getUserID()));
            comment.setBlog(blogMapper.getBlogByID(comment.getBlogID()));
            Timestamp replyTime = comment.getReplyTime();
            String time = StringUtil.getDateString(replyTime);
            comment.setTime(time);
        }
        return ResponseResult.succ(commentList, comments.size());
    }
}
