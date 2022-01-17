package com.person.blog;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BlogApplicationTests {


    @Test
    void contextLoads() {

//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
//                "LTAI4Fz7JpXmVVZzSgzwDTyN",
//                "BwlLu7idBkoxvMihvxnTR9OF89VtzQ");
//        /** use STS Token
//         DefaultProfile profile = DefaultProfile.getProfile(
//         "<your-region-id>",           // The region ID
//         "<your-access-key-id>",       // The AccessKey ID of the RAM account
//         "<your-access-key-secret>",   // The AccessKey Secret of the RAM account
//         "<your-sts-token>");          // STS Token
//         **/
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain("dysmsapi.aliyuncs.com");
//        request.setSysVersion("2017-05-25");
//        request.setSysAction("SendSms");
//        request.putQueryParameter("SignName", "简阅");
//        request.putQueryParameter("TemplateCode", "SMS_162735930");
//        request.putQueryParameter("PhoneNumbers", "18852009633");
//        //构建一个短信验证码
//        Map<String,Object> map  = new HashMap<>();
//        map.put("code","1234");
//        //将定义好的验证码放入进去
//        //短信验证码
//        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
    }

}
