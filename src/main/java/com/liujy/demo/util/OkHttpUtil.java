package com.liujy.demo.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName OkHttpUtil 一个http工具类
 * @Description
 * @Author jingyun_liu
 * @Date 2019/9/2 14:54
 * @Version V1.0
 **/
@Slf4j
public class OkHttpUtil {
    public static OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 普通的get请求
     * @param url
     * @return
     */
    public String get(String url){

        Request.Builder builder = new Request.Builder()
                .get()
                .url(url);
        // 创建连接客户端
        Request request = builder.build();
        // 创建调用对象
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response.code() == 403) {
                return "403";
            }
            if (response.isSuccessful()) {
                return response.body().toString();
            }
        } catch (IOException e) {
            log.error("http util get error {}", e.getMessage());
        }
        return "timeout";
    }
    /**
     * 普通的post请求
     * @param url
     * @return
     */
    public String post(String url, Map<String,String> body){
        FormBody.Builder builder = new FormBody.Builder();
        if(body!= null ){
            for(String key : body.keySet()){
                if(!StringUtils.isBlank(body.get(key))) {
                    builder.add(key,StringUtils.defaultString(body.get(key)));
                }
            }
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        // 创建调用对象

        Call call = okHttpClient.newBuilder()
                .build()
                .newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("http util post error {}", e);
        }
        return "timeout";
    }

}
