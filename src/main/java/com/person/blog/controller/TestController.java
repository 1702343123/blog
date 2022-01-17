package com.person.blog.controller;

import com.person.blog.util.Client;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "0.测试")
@Client
public class TestController {
    @ApiOperation(value = "首页")
    @GetMapping(value = "/")
    public String hello() {
        return "hello world";
    }
}
