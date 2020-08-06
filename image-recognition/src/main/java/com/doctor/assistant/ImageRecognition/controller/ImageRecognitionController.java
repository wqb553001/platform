package com.doctor.assistant.ImageRecognition.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doctor.assistant.ImageRecognition.dao.InvoiceMainDaoI;
import com.doctor.assistant.ImageRecognition.entity.InvoiceMain;
import com.doctor.assistant.ImageRecognition.service.ImageRecognitionService;
import com.doctor.assistant.ImageRecognition.utils.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Example;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.LocalTime;
import java.util.*;

@RestController
@RefreshScope
@RequestMapping({"/image"})
public class ImageRecognitionController {
    final static Logger log = LoggerFactory.getLogger(ImageRecognitionController.class);
    @Autowired
    private ImageRecognitionService imageRecognitionService;
    @Value(value="${tokenUrl}") // "access_token":"24.d967bbf7c5ad6c77cfdd614d3ef352a2.2592000.1578992008.282335-17963898"
    private String tokenUrl;// 返回信息：{"refresh_token":"25.257f0dbe0061e321c130e61992dd1159.315360000.1891760008.282335-17963898","expires_in":2592000,"session_key":"9mzdX+OCakh4wx60ybtuGXoSLksEhS7vnd\/DwS2GSmSRbwssN9tSWfueQw81cQVvL9YZ5tL5BPCbw8MnMu\/nN5GJ2WO\/cg==","access_token":"24.d967bbf7c5ad6c77cfdd614d3ef352a2.2592000.1578992008.282335-17963898","scope":"public vis-ocr_ocr brain_ocr_scope brain_ocr_general brain_ocr_general_basic vis-ocr_business_license brain_ocr_webimage brain_all_scope brain_ocr_idcard brain_ocr_driving_license brain_ocr_vehicle_license vis-ocr_plate_number brain_solution brain_ocr_plate_number brain_ocr_accurate brain_ocr_accurate_basic brain_ocr_receipt brain_ocr_business_license brain_solution_iocr brain_qrcode brain_ocr_handwriting brain_ocr_passport brain_ocr_vat_invoice brain_numbers brain_ocr_business_card brain_ocr_train_ticket brain_ocr_taxi_receipt vis-ocr_household_register vis-ocr_vis-classify_birth_certificate vis-ocr_\u53f0\u6e7e\u901a\u884c\u8bc1 vis-ocr_\u6e2f\u6fb3\u901a\u884c\u8bc1 vis-ocr_\u673a\u52a8\u8f66\u68c0\u9a8c\u5408\u683c\u8bc1\u8bc6\u522b vis-ocr_\u8f66\u8f86vin\u7801\u8bc6\u522b vis-ocr_\u5b9a\u989d\u53d1\u7968\u8bc6\u522b vis-ocr_\u4fdd\u5355\u8bc6\u522b brain_ocr_vin brain_ocr_quota_invoice brain_ocr_birth_certificate brain_ocr_household_register brain_ocr_HK_Macau_pass brain_ocr_taiwan_pass brain_ocr_vehicle_certificate brain_ocr_insurance_doc wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test\u6743\u9650 vis-classify_flower lpq_\u5f00\u653e cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi fake_face_detect_\u5f00\u653eScope vis-ocr_\u865a\u62df\u4eba\u7269\u52a9\u7406 idl-video_\u865a\u62df\u4eba\u7269\u52a9\u7406","session_secret":"762ae94eb8b8fdc5de6e516415d64e03"}

    @Value(value="${access_token}") // "access_token":"24.d967bbf7c5ad6c77cfdd614d3ef352a2.2592000.1578992008.282335-17963898"
    private String accessToken;// 返回信息：{"refresh_token":"25.257f0dbe0061e321c130e61992dd1159.315360000.1891760008.282335-17963898","expires_in":2592000,"session_key":"9mzdX+OCakh4wx60ybtuGXoSLksEhS7vnd\/DwS2GSmSRbwssN9tSWfueQw81cQVvL9YZ5tL5BPCbw8MnMu\/nN5GJ2WO\/cg==","access_token":"24.d967bbf7c5ad6c77cfdd614d3ef352a2.2592000.1578992008.282335-17963898","scope":"public vis-ocr_ocr brain_ocr_scope brain_ocr_general brain_ocr_general_basic vis-ocr_business_license brain_ocr_webimage brain_all_scope brain_ocr_idcard brain_ocr_driving_license brain_ocr_vehicle_license vis-ocr_plate_number brain_solution brain_ocr_plate_number brain_ocr_accurate brain_ocr_accurate_basic brain_ocr_receipt brain_ocr_business_license brain_solution_iocr brain_qrcode brain_ocr_handwriting brain_ocr_passport brain_ocr_vat_invoice brain_numbers brain_ocr_business_card brain_ocr_train_ticket brain_ocr_taxi_receipt vis-ocr_household_register vis-ocr_vis-classify_birth_certificate vis-ocr_\u53f0\u6e7e\u901a\u884c\u8bc1 vis-ocr_\u6e2f\u6fb3\u901a\u884c\u8bc1 vis-ocr_\u673a\u52a8\u8f66\u68c0\u9a8c\u5408\u683c\u8bc1\u8bc6\u522b vis-ocr_\u8f66\u8f86vin\u7801\u8bc6\u522b vis-ocr_\u5b9a\u989d\u53d1\u7968\u8bc6\u522b vis-ocr_\u4fdd\u5355\u8bc6\u522b brain_ocr_vin brain_ocr_quota_invoice brain_ocr_birth_certificate brain_ocr_household_register brain_ocr_HK_Macau_pass brain_ocr_taiwan_pass brain_ocr_vehicle_certificate brain_ocr_insurance_doc wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test\u6743\u9650 vis-classify_flower lpq_\u5f00\u653e cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi fake_face_detect_\u5f00\u653eScope vis-ocr_\u865a\u62df\u4eba\u7269\u52a9\u7406 idl-video_\u865a\u62df\u4eba\u7269\u52a9\u7406","session_secret":"762ae94eb8b8fdc5de6e516415d64e03"}

    @Value("${zzs_BaiduApiURL}")
    private String zzsBaiduApiURL;
    @Value("${zzs_APIKey}")
    private String zzsAPIKey;
    @Value("${zzs_SecretKey}")
    private String zzsSecretKey;

    @Value("${useIOCR_BaiduApiURL}")
    private String useIOCRBaiduApiURL;
    @Value("${useIOCR_APIKey}")
    private String useIOCRAPIKey;
    @Value("${useIOCR_SecretKey}")
    private String useIOCRSecretKey;

    @Autowired
    InvoiceMainDaoI invoiceMainDao;

    @RequestMapping({"/zzsBaiduApiURL"})
    public String zzsBaiduApiURL(){
        System.out.println("访问了 /image/zzsBaiduApiURL");
        return zzsBaiduApiURL;
    }

    @RequestMapping({"/accessToken"})
    public String accessToken(){
        System.out.println("访问了 /image/accessToken");
        return accessToken;
    }

    @RequestMapping({"/refresh"})
    public String refresh(){
        System.out.println("访问了 /image/refresh");
        this.imageRecognitionService.refresh();
        return "SUCCESS!!!";
    }

    @RequestMapping({"/utilToken"})
    @ResponseBody
    public String index(){
        System.out.println("访问了 /image/utilToken");
        return this.imageRecognitionService.getAccessToken();
    }

    @RequestMapping("/token")
    @ResponseBody
    public String getAccessToKen(){
        System.out.println("访问 /image/token");
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("grant_type", "client_credentials");// grant_type： 必须参数，固定为client_credentials；

        paramMap.put("client_id", zzsAPIKey);// client_id： 必须参数，应用的API Key；
        paramMap.put("client_secret", zzsSecretKey); // client_secret： 必须参数，应用的Secret Key；

//        paramMap.put("client_id", useIOCRAPIKey);// client_id： 必须参数，应用的API Key；
//        paramMap.put("client_secret", useIOCRSecretKey);// client_secret： 必须参数，应用的Secret Key；

        System.out.println(paramMap.toString());
        log.info("tokenUrl:" + tokenUrl);
        this.imageRecognitionService.getAccessToken(tokenUrl, null, paramMap);
        return "SUCCESS !!!";
    }

    @RequestMapping("/uploadImages")
    @ResponseBody
    public String filesUpload(MultipartFile[] images,
                                   HttpServletRequest request, HttpServletResponse response) {
        Map<String,List<InvoiceMain>> invoiceMainsMap = new HashMap<>();
        System.out.println("访问 /image/uploadImages");
        long timeBefore = LocalTime.now().toNanoOfDay();
        List<InvoiceMain> invoiceMains = this.imageRecognitionService.recogeImage(request, images);
        long timeEnd = LocalTime.now().toNanoOfDay();
        log.info("总用时：" +(timeEnd - timeBefore));
        log.info("存入后返回：" + JSON.toJSON(invoiceMains));
        invoiceMainsMap.put("invoiceMains", invoiceMains);
        return JSONObject.toJSONString(invoiceMainsMap);
    }

    @RequestMapping("/saveInvoiceMain")
    @ResponseBody
    public String saveInvoiceMain(InvoiceMain invoiceMain,
                                   HttpServletRequest request, HttpServletResponse response, Model model) {
        System.out.println("访问 /image/saveInvoiceMain");
        return this.ObjectToJSON(this.imageRecognitionService.insertInvoiceMain(invoiceMain));//跳转的页面
    }


    @RequestMapping("/invoiceMain/{invoiceMainId}")
    @ResponseBody
    public String getInvoiceMain(@PathVariable(value = "invoiceMainId") String invoiceMainId,
                                   HttpServletRequest request, HttpServletResponse response, Model model) {
        System.out.println("访问 /image/invoiceMain/"+invoiceMainId);
        return this.ObjectToJSON(imageRecognitionService.getInvoiceMain(invoiceMainId));
    }

    @RequestMapping("/flushImage")
    @ResponseBody
    public String flushImage(InvoiceMain invoiceMain,
                             HttpServletRequest request, HttpServletResponse response){
        System.out.println("访问 /image/flushImage");
        this.imageRecognitionService.insertInvoiceMain(invoiceMain);
        return this.ObjectToJSON(invoiceMain);
    }

    @RequestMapping("/nextOneCheckImage")
    @ResponseBody
    public String nextOneCheckImage(HttpServletRequest request, HttpServletResponse response){
        List<InvoiceMain> invoiceMains = new ArrayList<>();
        System.out.println("访问 /image/flushImage");
        InvoiceMain invoiceMain = new InvoiceMain();
        invoiceMain.setChecked(0);
        Optional<InvoiceMain> mainOptional = this.invoiceMainDao.findOne(Example.of(invoiceMain));
        if(mainOptional.isPresent()){
            invoiceMain = mainOptional.get();
            invoiceMains.add(invoiceMain);
        }
//        else{
//            // 空值填充
//            invoiceMain.setWordsResult(new Invoice());
//        }
        return this.ObjectToJSON(invoiceMains);
    }

    private String ObjectToJSON(List<InvoiceMain> invoiceMains){
        Map<String,List<InvoiceMain>> invoiceMainsMap = new HashMap<>();
        invoiceMainsMap.put("invoiceMains", invoiceMains);
        return JSONObject.toJSONString(invoiceMainsMap);
    }
    private String ObjectToJSON(InvoiceMain invoiceMain){
        List<InvoiceMain> invoiceMains = new ArrayList<>();
        invoiceMains.add(invoiceMain);
        return this.ObjectToJSON(invoiceMains);
    }

//    @RequestMapping("/testMQ")
//    @ResponseBody
    @RequestMapping("/testMQ")
    @ResponseBody
    public String testMQ(){
        String context = "2020年4月14日 WQB testMQ";
        System.out.println("日志：=========== "+context);
        imageRecognitionService.senderMQ(context);
        return "OK";
    }
}
