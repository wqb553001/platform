package com.doctor.assistant.ImageRecognition.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doctor.assistant.ImageRecognition.dao.BaiduOcrDao;
import com.doctor.assistant.ImageRecognition.dao.ElementDao;
import com.doctor.assistant.ImageRecognition.dao.InvoiceDao;
import com.doctor.assistant.ImageRecognition.dao.InvoiceMainDaoI;
import com.doctor.assistant.ImageRecognition.entity.BaiduOcr;
import com.doctor.assistant.ImageRecognition.entity.Element;
import com.doctor.assistant.ImageRecognition.entity.Invoice;
import com.doctor.assistant.ImageRecognition.entity.InvoiceMain;
import com.doctor.assistant.ImageRecognition.utils.HttpPostUtil;
import com.doctor.assistant.ImageRecognition.utils.Results;
import com.doctor.assistant.ImageRecognition.utils.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BinaryOperator;

@RefreshScope
@Service
public class ImageRecognitionService {

    @Value("${zzs_APIKey}")
    private String zzsAPIKey;
    @Value("${zzs_SecretKey}")
    private String zzsSecretKey;
    @Value(value="${access_token}") // "access_token":"24.d967bbf7c5ad6c77cfdd614d3ef352a2.2592000.1578992008.282335-17963898"
    private String accessToken;    // 返回信息：{"refresh_token":"25.257f0dbe0061e321c130e61992dd1159.315360000.1891760008.282335-17963898","expires_in":2592000,"session_key":"9mzdX+OCakh4wx60ybtuGXoSLksEhS7vnd\/DwS2GSmSRbwssN9tSWfueQw81cQVvL9YZ5tL5BPCbw8MnMu\/nN5GJ2WO\/cg==","access_token":"24.d967bbf7c5ad6c77cfdd614d3ef352a2.2592000.1578992008.282335-17963898","scope":"public vis-ocr_ocr brain_ocr_scope brain_ocr_general brain_ocr_general_basic vis-ocr_business_license brain_ocr_webimage brain_all_scope brain_ocr_idcard brain_ocr_driving_license brain_ocr_vehicle_license vis-ocr_plate_number brain_solution brain_ocr_plate_number brain_ocr_accurate brain_ocr_accurate_basic brain_ocr_receipt brain_ocr_business_license brain_solution_iocr brain_qrcode brain_ocr_handwriting brain_ocr_passport brain_ocr_vat_invoice brain_numbers brain_ocr_business_card brain_ocr_train_ticket brain_ocr_taxi_receipt vis-ocr_household_register vis-ocr_vis-classify_birth_certificate vis-ocr_\u53f0\u6e7e\u901a\u884c\u8bc1 vis-ocr_\u6e2f\u6fb3\u901a\u884c\u8bc1 vis-ocr_\u673a\u52a8\u8f66\u68c0\u9a8c\u5408\u683c\u8bc1\u8bc6\u522b vis-ocr_\u8f66\u8f86vin\u7801\u8bc6\u522b vis-ocr_\u5b9a\u989d\u53d1\u7968\u8bc6\u522b vis-ocr_\u4fdd\u5355\u8bc6\u522b brain_ocr_vin brain_ocr_quota_invoice brain_ocr_birth_certificate brain_ocr_household_register brain_ocr_HK_Macau_pass brain_ocr_taiwan_pass brain_ocr_vehicle_certificate brain_ocr_insurance_doc wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test\u6743\u9650 vis-classify_flower lpq_\u5f00\u653e cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi fake_face_detect_\u5f00\u653eScope vis-ocr_\u865a\u62df\u4eba\u7269\u52a9\u7406 idl-video_\u865a\u62df\u4eba\u7269\u52a9\u7406","session_secret":"762ae94eb8b8fdc5de6e516415d64e03"}
    @Value(value="${tokenUrl}") // "access_token":"24.d967bbf7c5ad6c77cfdd614d3ef352a2.2592000.1578992008.282335-17963898"
    private String tokenUrl;
    @Value(value="${refresh_url}")
    private String refreshUrl;

    @Autowired
    private BaiduOcrDao baiduOcrDao;
    @Autowired
    InvoiceMainDaoI invoiceMainDao;
    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    ElementDao elementDao;

    public void getAccessToken(String url, Map<String, String> headerMap, Map<String, String> paramMap){
        HttpPostUtil.doGet(url, headerMap, paramMap);
    }

    public String getAccessToken(){
        return accessToken;
    }

    public InvoiceMain callBaiduImageRecognition(String url, Map<String, String> headerMap, Map<String, String> paramMap){
        int flag = 5;
        String resData = HttpPostUtil.doPost(url, headerMap, paramMap);
        if(resData.contains("error_code")){
            // 刷新 Token
            String freshAccessToken = this.refreshToken(resData);

            while (resData.contains("error_code") && flag > 0){
                paramMap.put("access_token", freshAccessToken);
                resData = HttpPostUtil.doPost(url, headerMap, paramMap);
                System.out.println("第"+(-flag+6)+"次 处理");
            }
        }

        InvoiceMain invoiceMain = this.explainJsonToEntity(resData);
        if(invoiceMain != null) {
            if(invoiceMain.getLogId() != null)
                this.insertInvoiceMain(invoiceMain);
        }else{
            invoiceMain = new InvoiceMain();
        }
        return invoiceMain;
    }

    public String examineImage(InvoiceMain invoiceMain){
        return HttpPostUtil.doPost("url", null, null);
    }

    public InvoiceMain explainJsonToEntity(String jsonStr){
        InvoiceMain invoiceMain = JSON.toJavaObject(JSON.parseObject(jsonStr), InvoiceMain.class);
        if(invoiceMain == null) return null;
        Invoice wordsResult = invoiceMain.getWordsResult();
        if(wordsResult == null) return invoiceMain;
        wordsResult.setItems(this.countItems(wordsResult));
        return invoiceMain;
    }

    public Results insertInvoiceMain(InvoiceMain invoiceMain) {
        String commodityTaxRates = UUIDUtil.getUUID();
        String commodityTaxs = UUIDUtil.getUUID();
        String commodityAmounts = UUIDUtil.getUUID();
        String commodityNums = UUIDUtil.getUUID();
        String commodityUnits = UUIDUtil.getUUID();
        String commodityPrices = UUIDUtil.getUUID();
        String commodityNames = UUIDUtil.getUUID();
        String commodityTypes = UUIDUtil.getUUID();
        Invoice wordsResult = invoiceMain.getWordsResult();

        wordsResult = wordsResult.setCommodityTaxRates(commodityTaxRates)
                .setCommodityTaxs(commodityTaxs)
                .setCommodityAmounts(commodityAmounts)
                .setCommodityNums(commodityNums)
                .setCommodityUnits(commodityUnits)
                .setCommodityPrices(commodityPrices)
                .setCommodityNames(commodityNames)
                .setCommodityTypes(commodityTypes);
        wordsResult.setId(UUIDUtil.getUUID());
        this.invoiceDao.saveAndFlush(wordsResult);

        invoiceMain.setId(UUIDUtil.getUUID());
        // 保存 票据主体信息
        this.invoiceMainDao.saveAndFlush(invoiceMain);

        List<Element> resultElements = new ArrayList<>(14);
        // 分别保存 元数据
        List<Element> commodityTaxRate = wordsResult.getCommodityTaxRate();
        List<Element> commodityTax = wordsResult.getCommodityTax();
        List<Element> commodityAmount = wordsResult.getCommodityAmount();
        List<Element> commodityNum = wordsResult.getCommodityNum();
        List<Element> commodityUnit = wordsResult.getCommodityUnit();
        List<Element> commodityPrice = wordsResult.getCommodityPrice();
        List<Element> commodityName = wordsResult.getCommodityName();
        List<Element> commodityType = wordsResult.getCommodityType();

        this.setElements(resultElements, commodityTaxRate, commodityTaxRates);
        this.setElements(resultElements, commodityTax, commodityTaxs);
        this.setElements(resultElements, commodityAmount, commodityAmounts);
        this.setElements(resultElements, commodityNum, commodityNums);
        this.setElements(resultElements, commodityUnit, commodityUnits);
        this.setElements(resultElements, commodityPrice, commodityPrices);
        this.setElements(resultElements, commodityName, commodityNames);
        this.setElements(resultElements, commodityType, commodityTypes);
        if(!resultElements.isEmpty()) this.elementDao.batchInsertElementList(resultElements);
        Results results = Results.OK();
        results.setResult(invoiceMain);
        return results;
    }

    public Results getInvoiceMain(String invoiceMainId) {
        Results results = new Results(Results.empty);
        InvoiceMain invoiceMain = new InvoiceMain();
        if(StringUtils.isEmpty(invoiceMainId)) return results;
        Optional<InvoiceMain> invoiceMainOptional = invoiceMainDao.findById(invoiceMainId);
        if(invoiceMainOptional.isPresent()){
            invoiceMain = invoiceMainOptional.get();
            if(invoiceMain != null){
                Invoice invoice = invoiceMain.getWordsResult();
                this.queryAndSetElementByInvoice(invoice);
            }
            results.setState(Results.OK);
            results.setResult(invoiceMain);
        }
        return results;
    }

    private String refreshToken(String jsonStr){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String resData = jsonStr;
        String accessToken = jsonStr;
        if(jsonObject != null && jsonObject.containsKey("error_code")){
            Object error_code = jsonObject.get("error_code");
            if(error_code.toString().equals("111") || jsonObject.get("error_msg").toString().toLowerCase().contains("token")){
                // token 过期了
                // 1. 重新 获取 token
                resData = this.getToken();
                // 2. 解析 新的token
                accessToken = this.example(resData, "access_token");
                // 3. 将最新的 token 存库
                if(StringUtils.isNotBlank(accessToken)){
                    // 3.1 将 configServer 原记录读取过来
                    BaiduOcr baiduOcr = new BaiduOcr();
                    baiduOcr.setApiKey("access_token");
                    baiduOcr.setEnable(1);// 1: 可用；0：不可用
                    Example<BaiduOcr> example = Example.of(baiduOcr);
                    List<BaiduOcr> all = this.baiduOcrDao.findAll(example);
                    if(all != null && all.size() > 0){
                        baiduOcr = all.get(0);
                    }
                    // 3.2 填充新值
                    baiduOcr.setProfile("dev");
                    baiduOcr.setApiAddr(accessToken);
                    // 3.3 更新到库
                    this.baiduOcrDao.saveAndFlush(baiduOcr);
                }

                // 4. 刷新缓存
                this.refresh();
            }
        }
        return accessToken;
    }

    private String example(String jsonStr, String requestKey){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if(jsonObject != null && jsonObject.containsKey(requestKey)){
            Object error_code = jsonObject.get(requestKey);
            if(error_code != null &&StringUtils.isNotBlank(error_code.toString())){
                return error_code.toString();
            }
        }
        return null;
    }

    private void queryAndSetElementByInvoice(Invoice invoice){
        if(invoice != null){
            String commodityAmounts = invoice.getCommodityAmounts();
            invoice.setCommodityAmount(elementDao.findElementByCommodityId(commodityAmounts));
            invoice.setCommodityName(elementDao.findElementByCommodityId(invoice.getCommodityNames()));
            invoice.setCommodityUnit(elementDao.findElementByCommodityId(invoice.getCommodityUnits()));
            invoice.setCommodityNum(elementDao.findElementByCommodityId(invoice.getCommodityNums()));
            invoice.setCommodityPrice(elementDao.findElementByCommodityId(invoice.getCommodityPrices()));
            invoice.setCommodityTax(elementDao.findElementByCommodityId(invoice.getCommodityTaxs()));
            invoice.setCommodityTaxRate(elementDao.findElementByCommodityId(invoice.getCommodityTaxRates()));
            invoice.setCommodityType(elementDao.findElementByCommodityId(invoice.getCommodityTypes()));
        }
    }

    private int countItems(Invoice wordsResult){
        if(wordsResult == null) return 0;
        int commodityAmountSize = this.getSize(wordsResult.getCommodityAmount()),
                commodityNameSize = this.getSize(wordsResult.getCommodityName()),
                commodityTypeSize = this.getSize(wordsResult.getCommodityType()),
                commodityUnitSize = this.getSize(wordsResult.getCommodityUnit()),
                commodityNumSize = this.getSize(wordsResult.getCommodityNum()),
                commodityTaxSize = this.getSize(wordsResult.getCommodityTax()),
                CommodityPriceSize = this.getSize(wordsResult.getCommodityPrice()),
                commodityTaxRateSize = this.getSize(wordsResult.getCommodityTaxRate());

        List<Integer> list = Arrays.asList(commodityAmountSize, commodityNameSize,
                commodityNumSize, CommodityPriceSize,
                commodityTaxSize, commodityTaxRateSize,
                commodityTypeSize, commodityUnitSize);

        //求其最大值
        return list.stream().reduce(BinaryOperator.maxBy(Integer::compare)).orElseGet(() -> {return 0;});
    }

    private int getSize(List<Element> commoditys){
        if(commoditys == null) return 0;
        return commoditys.size();
    }

    private List<Element> setElements(List<Element> resultElements, List<Element> elements, String commodityId){
        if(!elements.isEmpty()){
            for (Element element : elements) {
                element.setCommodityId(commodityId);
                resultElements.add(element);
            }
        }
        return resultElements;
    }

    private String getToken(){
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("grant_type", "client_credentials");// grant_type： 必须参数，固定为client_credentials；
        paramMap.put("client_id", zzsAPIKey);// client_id： 必须参数，应用的API Key；
        paramMap.put("client_secret", zzsSecretKey); // client_secret： 必须参数，应用的Secret Key；
        System.out.println(paramMap.toString());
        System.out.println("tokenUrl:" + tokenUrl);
        return HttpPostUtil.doGet(tokenUrl, null, paramMap);
    }

    public void refresh() {
        String resData = "";
        // 3. 刷新缓存
        String urlStr = refreshUrl;
        if(StringUtils.isBlank(urlStr)){
            urlStr = "http://localhost:8769/actuator/bus-refresh";
//            urlStr = "http://localhost:8082/actuator/refresh";
        }

        HttpPost httpPost = new HttpPost(urlStr);
        resData = HttpPostUtil.midCall(httpPost);
        System.out.println("刷新缓存: " +urlStr+ "  返回：" + resData);
    }

}
