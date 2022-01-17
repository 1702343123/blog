package com.person.blog.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 短信发送工具类
 */
public class SMSUtil {
    public static int send(String mobile, String newCode) {
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",
                "LTAI4Fz7JpXmVVZzSgzwDTyN",
                "BwlLu7idBkoxvMihvxnTR9OF89VtzQ");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "简阅");
        request.putQueryParameter("TemplateCode", "SMS_162735930");
//        String verifyCode =NewCodeUtil.getNewCode();
        request.putQueryParameter("TemplateParam", "{\"code\":" + newCode + "}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return StatusConst.SUCCESS;
    }
//    public static String send(String mobile) {
//        DefaultProfile profile = DefaultProfile.getProfile(
//                "cn-hangzhou",
//                "LTAIWMVzDKXCrGRn",
//                "LofOSXUQNCU9Bvyk4q1Ep9fAzFUdNV");
//        IAcsClient client = new DefaultAcsClient(profile);
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumbers", mobile);
//        request.putQueryParameter("SignName", "简阅");
//        request.putQueryParameter("TemplateCode", "SMS_162735622");
//        String verifyCode = StringUtil.getVerifyCode();
//        request.putQueryParameter("TemplateParam", "{\"code\":" + verifyCode + "}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//        return verifyCode;
//    }
//
//    public static void main(String[] args) {
//        System.out.println(send("18351895608"));
//    }
}

