package com.person.blog.entity;

/**
 * 博客的种类类
 */
public class BlogType {
    private Integer id;
    private String typeName;
    private Integer isUse;

    public BlogType() {
    }

    @Override
    public String toString() {
        return "BlogType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", isUse=" + isUse +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public BlogType(Integer id, String typeName, Integer isUse) {
        this.id = id;
        this.typeName = typeName;
        this.isUse = isUse;
    }
}
