package com.person.blog.mapper;

import com.person.blog.entity.BlogType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BlogTypeMapper {
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "typeName", property = "typeName"),
            @Result(column = "idUse", property = "isUse")
    })

    //查询种类详情
    @Select("select * from blog_type where id=#{id}")
    BlogType getTypeById(Integer id);

    //获取所有种类列表
    @Select("select * from blog_type where isUse=0")
    List<BlogType> getTybeList();
}
