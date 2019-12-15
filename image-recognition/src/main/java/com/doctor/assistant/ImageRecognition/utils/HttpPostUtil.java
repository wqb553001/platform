package com.doctor.assistant.ImageRecognition.utils;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpPostUtil {

    public static HttpEntity doGet(String url, Map<String, String> headerMap, Map<String, String> paramMap){
        /**
         // 1. 创建 HttpClient 请求
         // 2. 组装 Form 表单 附 请求参数
         // 3. 发起请求
         // 4. 读取返回结果
         // 5. 关闭请求
         **/

        // 1. 创建HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 2. 组装 Form 表单 附 请求参数
        if(paramMap != null && !paramMap.isEmpty()){
            url +="?";
            for (Map.Entry<String, String> paramEntry : paramMap.entrySet()) {
                NameValuePair param = new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue());
                System.out.println("paramEntry = " + paramEntry);
                url  += paramEntry.getKey()+"="+paramEntry.getValue() + "&";
            }
            url = url.substring(0, url.length() - 1);
        }
//        HttpGet httpGet = new HttpGet(url);
        HttpPost httpGet = new HttpPost(url);
        System.out.println("doGet() 中 url = " + url);
        if(headerMap != null && !headerMap.isEmpty()){
            for (Map.Entry<String, String> headerEntry : headerMap.entrySet()) {
                httpGet.setHeader(headerEntry.getKey(), headerEntry.getValue());
                System.out.println("headerEntry = " + headerEntry);
            }
        }

        return call(httpClient, httpGet);
    }

    public static HttpEntity doPost(String url, Map<String, String> headerMap, Map<String, String> paramMap){
        /**
         // 1. 创建 HttpClient 请求
         // 2. 组装 Form 表单 附 请求参数
         // 3. 发起请求
         // 4. 读取返回结果
         // 5. 关闭请求
         **/

        // 1. 创建HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if(headerMap != null && !headerMap.isEmpty()){
            for (Map.Entry<String, String> headerEntry : headerMap.entrySet()) {
                httpPost.setHeader(headerEntry.getKey(), headerEntry.getValue());
                System.out.println("headerEntry = " + headerEntry);
            }
        }

        // 2. 组装 Form 表单 附 请求参数
        UrlEncodedFormEntity formEntity = null;
        List<NameValuePair> paramList = new ArrayList<>();
        if(paramMap != null && !paramMap.isEmpty()){
            for (Map.Entry<String, String> paramEntry : paramMap.entrySet()) {
                NameValuePair param = new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue());
                System.out.println("paramEntry = " + paramEntry);
                paramList.add(param);
            }
        }
        formEntity = new UrlEncodedFormEntity(paramList, Charsets.UTF_8);
        httpPost.setEntity(formEntity);
        return call(httpClient, httpPost);
    }

    private static HttpEntity call(CloseableHttpClient httpClient, HttpRequestBase http){

        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec("aip.baidubce.com").build();
        http.setConfig(requestConfig);

        // 3. 发起请求
        CloseableHttpResponse httpResponse = null;
        HttpEntity responseEntity = null;
        try {
            httpResponse = httpClient.execute(http);

            // 4. 读取返回结果
            if(httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200){
                responseEntity = httpResponse.getEntity();
                String content = responseEntity.toString();
                System.out.println("responseContent : " + content);
                String resData = EntityUtils.toString(responseEntity);
                System.out.println("返回信息："+resData);
            }
            String responseStr = httpResponse.toString();
            System.out.println("httpResponse : " + responseStr);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5. 关闭请求
            try {
                if(httpResponse != null){
                    httpResponse.close();
                }
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return responseEntity;
    }
}
