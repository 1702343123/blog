package com.person.blog.mapper;

import com.person.blog.entity.Img;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ImgMapper {
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "blogID", property = "blogID"),
            @Result(column = "imgUrl", property = "imgUrl")
    })

    //遍历所有图片
    @Select("select * from img")
    List<Img> getImgList();

    //根据blogID查询图片
    @Select("select * from img where blogID=#{blogID}")
    List<Img> getImgByBlogID(Integer blogID);

    //添加图片
    @Insert("insert into img(blogID,imgUrl) values(#{blogID},#{imgUrl})")
    int insertBlogImg(Img img);
}
