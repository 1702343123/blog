package com.person.blog.controller;

import com.aliyun.oss.OSSClient;
import com.person.blog.util.Client;
import com.person.blog.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@RestController
@Api(tags = "A.上传图片")
@RequestMapping(value = "/api")
@Client
public class InsertImgController {
//    @Autowired
//    ImgMapper imgMapper;

    @ApiOperation(value = "图片上传至阿里云OSS，返回对象")
    @PostMapping(value = "/img/insetImg")
    public ResponseResult ossUpload(MultipartFile file) {
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI4Fz7JpXmVVZzSgzwDTyN";
        String accessKeySecret = "BwlLu7idBkoxvMihvxnTR9OF89VtzQ";
        String bucketName = "jianyueapp";
        String filedir = "avatar/";
        //uuid生成主文件名
        String prefix = UUID.randomUUID().toString();
        String prefix2=prefix+".jpg";
        File tempFile = null;
        try {
            //创建临时文件
            tempFile = File.createTempFile(prefix2, prefix2);
            // MultipartFile to File
            file.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, filedir + prefix2, tempFile);
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, filedir + prefix2, expiration);
        ossClient.shutdown();
        String str = url.toString();
//        截取
        String substring = str.substring(0, str.indexOf("?Expires="));
//        替换
        String s = substring.replace("save-pan.oss-cn-hangzhou.aliyuncs.com", "img2.panbingwen.cn");
        return ResponseResult.success(s);
    }
}
