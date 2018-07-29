package com.example.weekly_03_test.utils;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpUtils {

    //单例  懒汉模式
    private static  OkHttpClient client = null;

    private OkHttpUtils() {
    }
    //获取client实例
    public static OkHttpClient getInstance() {

        if(client==null){
            //线程同步方法,同步本类
            synchronized (OkHttpUtils.class){

                if(client==null){
                    client = new OkHttpClient();
                }
            }
        }
        return client;
    }

    //写get 异步请求,传入回调
    public static void DOGet(String path, Callback callback){

        //辅助类
        Request request = new Request.Builder()
                .url(path)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    //写Post 异步请求 上传map类型 字符串

   public static void DOPost(String path, Map<String,String> map,Callback callback){

        //post请求先写表单请求体  ,map 集合一定要写泛型
       FormBody.Builder builder = new FormBody.Builder();
       for(String key : map.keySet()){
           builder.add(key,map.get(key));
       }
       //创建辅助类
       Request request = new Request.Builder()

               .url(path)
               .post(builder.build())
               .build();
       Call call = getInstance().newCall(request);
       call.enqueue(callback);
   }

   //post 请求 使用json格式上传
  public static void DOPostJson(String path,String json,Callback callback){

     //创建请求体
      RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);

      //创建辅助类
      Request request = new Request.Builder()
              .url(path)
              .post(requestBody)
              .build();
      Call call = getInstance().newCall(request);
      call.enqueue(callback);
  }


}
