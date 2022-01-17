package com.person.blog.util;

import com.person.blog.entity.dto.PageDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtil {
    public static Map<Object,Object> pageDemo(Integer currPage, Integer pageSize)
    {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("index",(currPage-1)*pageSize);
        map.put("pageSize",pageSize);
        return map;
    }

    public static PageDTO page(Integer currPage, Integer pageSize, List list)
    {
        Integer first=(currPage-1)*pageSize;
        Integer last=currPage*pageSize;
        int size = list.size();
        if (last>size){
            last=size;
        }
        List resultList = list.subList(first, last);
        PageDTO pageDTO=new PageDTO(first,last,size,resultList);
        return pageDTO;
    }

    public static PageDTO pageListDemo(Integer currPage,Integer pageSize,List list)
    {
        Integer curr = StatusConst.CURRENTPAGE;
        Integer size = list.size();
        if (currPage != null) {
            curr = currPage;
        }
        if (pageSize != null) {
            size = pageSize;
        }
        return PageUtil.page(curr, size, list);
    }
}
