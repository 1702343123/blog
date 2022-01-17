package com.person.blog.entity;

import java.sql.Timestamp;
import java.util.List;

/**
 * 博客类
 */
public class Blog {
    private Integer blogID;//博客id
    private Integer userID;//用户id
    private String blogName;//博客标题
    private Integer id;//种类id
    private String content;//内容
    private Timestamp createTime;//创建时间
    private String time;

    private List imgs;

    private List<Comment> comments;
    private int commentAmount;

    private String sort;

    private Integer isCollect;//是否被登录用户收藏：1(收藏),0(未收藏)
    private Integer collectCount;//有多少人收藏

    private Img img;

    private String imgUrl;

    private User autor;

    public Blog() {
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blogID=" + blogID +
                ", userID=" + userID +
                ", blogName='" + blogName + '\'' +
                ", id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", time='" + time + '\'' +
                ", imgs=" + imgs +
                ", comments=" + comments +
                ", commentAmount=" + commentAmount +
                ", sort='" + sort + '\'' +
                ", isCollect=" + isCollect +
                ", collectCount=" + collectCount +
                ", img=" + img +
                ", imgUrl='" + imgUrl + '\'' +
                ", autor=" + autor +
                '}';
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getCommentAmount() {
        return commentAmount;
    }

    public void setCommentAmount(int commentAmount) {
        this.commentAmount = commentAmount;
    }

    public List getImgs() {
        return imgs;
    }

    public void setImgs(List imgs) {
        this.imgs = imgs;
    }

    public Integer getBlogID() {
        return blogID;
    }

    public void setBlogID(Integer blogID) {
        this.blogID = blogID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp create) {
        this.createTime = create;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
