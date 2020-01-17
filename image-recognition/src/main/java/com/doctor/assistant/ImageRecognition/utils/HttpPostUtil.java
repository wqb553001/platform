package com.doctor.assistant.ImageRecognition.utils;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpPostUtil {

//    // 1. 创建HttpClient
//    public static CloseableHttpClient httpClient = getHttpClient();
//
//    private static CloseableHttpClient getHttpClient(){
//        if(httpClient == null){
//            httpClient = HttpClients.createDefault();
//        }
//        return httpClient;
//    }

    // 返回信息：{"refresh_token":"25.257f0dbe0061e321c130e61992dd1159.315360000.1891760008.282335-17963898",
    // "expires_in":2592000,"session_key":"9mzdX+OCakh4wx60ybtuGXoSLksEhS7vnd\/DwS2GSmSRbwssN9tSWfueQw81cQVvL9YZ5tL5BPCbw8MnMu\/nN5GJ2WO\/cg==",
    // "access_token":"24.d967bbf7c5ad6c77cfdd614d3ef352a2.2592000.1578992008.282335-17963898",
    // "scope":"public vis-ocr_ocr brain_ocr_scope brain_ocr_general brain_ocr_general_basic
    // vis-ocr_business_license brain_ocr_webimage brain_all_scope brain_ocr_idcard brain_ocr_driving_license
    // brain_ocr_vehicle_license vis-ocr_plate_number brain_solution brain_ocr_plate_number brain_ocr_accurate
    // brain_ocr_accurate_basic brain_ocr_receipt brain_ocr_business_license brain_solution_iocr brain_qrcode
    // brain_ocr_handwriting brain_ocr_passport brain_ocr_vat_invoice brain_numbers brain_ocr_business_card
    // brain_ocr_train_ticket brain_ocr_taxi_receipt vis-ocr_household_register vis-ocr_vis-classify_birth_certificate
    // vis-ocr_\u53f0\u6e7e\u901a\u884c\u8bc1 vis-ocr_\u6e2f\u6fb3\u901a\u884c\u8bc1
    // vis-ocr_\u673a\u52a8\u8f66\u68c0\u9a8c\u5408\u683c\u8bc1\u8bc6\u522b vis-ocr_\u8f66\u8f86vin\u7801\u8bc6\u522b
    // vis-ocr_\u5b9a\u989d\u53d1\u7968\u8bc6\u522b vis-ocr_\u4fdd\u5355\u8bc6\u522b brain_ocr_vin brain_ocr_quota_invoice
    // brain_ocr_birth_certificate brain_ocr_household_register brain_ocr_HK_Macau_pass brain_ocr_taiwan_pass
    // brain_ocr_vehicle_certificate brain_ocr_insurance_doc wise_adapt lebo_resource_base lightservice_public
    // hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test\u6743\u9650 vis-classify_flower lpq_\u5f00\u653e
    // cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar oauth_tp_app smartapp_smart_game_openapi
    // oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi
    // fake_face_detect_\u5f00\u653eScope vis-ocr_\u865a\u62df\u4eba\u7269\u52a9\u7406 idl-video_\u865a\u62df\u4eba\u7269\u52a9\u7406",
    // "session_secret":"762ae94eb8b8fdc5de6e516415d64e03"}

    public static String doGet(String url, Map<String, String> headerMap, Map<String, String> paramMap){

        // 组装 Form 表单 附 请求参数
        if(paramMap != null && !paramMap.isEmpty()){
            url +="?";
            for (Map.Entry<String, String> paramEntry : paramMap.entrySet()) {
                NameValuePair param = new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue());
//                System.out.println("paramEntry = " + paramEntry);
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

        return call(httpGet);
    }

    public static String doPost(String url, Map<String, String> headerMap, Map<String, String> paramMap){

        HttpPost httpPost = new HttpPost(url);
        if(headerMap != null && !headerMap.isEmpty()){
            for (Map.Entry<String, String> headerEntry : headerMap.entrySet()) {
                httpPost.setHeader(headerEntry.getKey(), headerEntry.getValue());
                System.out.println("headerEntry = " + headerEntry);
            }
        }

        // 组装 Form 表单 附 请求参数
        UrlEncodedFormEntity formEntity = null;
        List<NameValuePair> paramList = new ArrayList<>();
        if(paramMap != null && !paramMap.isEmpty()){
            for (Map.Entry<String, String> paramEntry : paramMap.entrySet()) {
                NameValuePair param = new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue());
//                System.out.println("paramEntry = " + paramEntry);
                paramList.add(param);
            }
        }
        formEntity = new UrlEncodedFormEntity(paramList, Charsets.UTF_8);
        httpPost.setEntity(formEntity);
        return call(httpPost);
    }

    private static String call(HttpRequestBase http){
        RequestConfig requestConfig = RequestConfig.custom()
            .setCookieSpec("aip.baidubce.com").build();
        http.setConfig(requestConfig);
        return midCall(http);
    }

    public static String midCall(HttpRequestBase http){
        /**
         // 1. 创建 HttpClient 请求
         // 2. 组装 Form 表单 附 请求参数
         // 3. 发起请求
         // 4. 读取返回结果
         // 5. 关闭请求
         **/

        // 1. 创建HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        SocketConfig socketConfig = SocketConfig.custom()
//                .setSoKeepAlive(false)
//                .setSoLinger(1)
//                .setSoReuseAddress(true)
////                .setSoTimeout(10000)
//                .setTcpNoDelay(true).build();
//
//        CloseableHttpClient httpClient = HttpClientBuilder.create()
//                .setDefaultSocketConfig(socketConfig).build();
        // 2. 组装 Form 表单 附 请求参数
        String resData = "";
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
                resData = EntityUtils.toString(responseEntity);
                System.out.println("返回信息：" + resData);
            }else{
                System.out.println("httpClient.execute()————getStatusLine().getStatusCode() != 200，请核查！！！！");
            }

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

        return resData;
    }

}
