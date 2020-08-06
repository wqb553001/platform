package com.doctor.assistant.ImageRecognition.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doctor.assistant.ImageRecognition.MQ.Sender;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BinaryOperator;

@RefreshScope
@Service
public class ImageRecognitionService {
    final static Logger log = LoggerFactory.getLogger(ImageRecognitionService.class);

    @Value("${zzs_BaiduApiURL}")
    private String zzsBaiduApiURL;

    @Value("${zzs_APIKey}")
    private String zzsAPIKey;
    @Value("${zzs_SecretKey}")
    private String zzsSecretKey;
    @Value(value="${access_token}")
    private String accessToken;
    @Value(value="${tokenUrl}")
    private String tokenUrl;
    @Value(value="${refresh_url}")
    private String refreshUrl;

    @Autowired
    private Sender sender;

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
    public String setAccessToken(String access_token){
        return this.accessToken = access_token;
    }

    public List<InvoiceMain> recogeImage(HttpServletRequest request, MultipartFile[] images){

        long timeBefore = LocalTime.now().toNanoOfDay();
        long timeSecondBefore = LocalTime.now().toSecondOfDay();
        List<String> list = new ArrayList<String>();
        String encodeFileStr = null;
        StringBuilder stringBuilder = null;
        List<InvoiceMain> invoiceMains = new ArrayList<>();
        InvoiceMain invoiceMain = null;

        if (images != null && images.length > 0 && !images[0].isEmpty()) {
            for (int i = 0; i < images.length; i++) {
                stringBuilder = new StringBuilder();
                MultipartFile file = images[i];
                final Base64.Encoder encoder = Base64.getEncoder();
                try {
                    encodeFileStr = encoder.encodeToString(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    stemp(false, i, timeSecondBefore, timeBefore);
                    continue;
                }
                // 保存文件
                list = saveFile(request, file, list);
                Map<String,String> headerMap = new HashMap<>();
                headerMap.put("Content-Type", "application/x-www-form-urlencoded");
                // image	是	string	-	图像数据，
                // base64编码后进行urlencode，
                // 要求base64编码和urlencode后大小不超过4M，最短边至少15px，最长边最大4096px,
                // 支持jpg/jpeg/png/bmp格式
                Map<String,String> paramMap = new HashMap<>();
                paramMap.put("image", encodeFileStr);
                // accuracy	否	string	normal/high
                // normal（默认配置）对应普通精度模型，识别速度较快，在四要素的准确率上和 high 模型保持一致，
                // high对应高精度识别模型，相应的时延会增加，因为超时导致失败的情况也会增加（错误码282000）
                paramMap.put("accuracy","normal");
                // type	否	string	normal/roll	进行识别的增值税发票类型，默认为 normal，可缺省
                //- normal：可识别增值税普票、专票、电子发票
                //- roll：可识别增值税卷票
                paramMap.put("type","normal");
                paramMap.put("access_token",accessToken);
//                System.out.println("zzsBaiduApiURL:" + zzsBaiduApiURL);
                stringBuilder.append("\r\n");
//                stringBuilder.append();
                invoiceMain = this.callBaiduImageRecognition(zzsBaiduApiURL, headerMap, paramMap);
                invoiceMains.add(invoiceMain);
                stemp(true, i, timeSecondBefore, timeBefore);
            }
        }

        return invoiceMains;
    }

    private void stemp(boolean OK_NO, int i, long timeSecondBefore, long timeBefore){
        if(!OK_NO) System.out.println("本张图片处理失败！");
        long timeMid = LocalTime.now().toNanoOfDay();
        long timeSecondMid = LocalTime.now().toSecondOfDay();
        System.out.println("至第"+(i+1)+"张:");
        System.out.println("用时秒数:"+(timeSecondMid - timeSecondBefore));
        System.out.println("用时毫秒：" +(timeMid - timeBefore));
    }

    private List<String> saveFile(HttpServletRequest request,
                                  MultipartFile file, List<String> list) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 保存的文件路径(如果用的是Tomcat服务器，文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
                // )
                String originalFilename = file.getOriginalFilename();
                System.out.println("originalFilename : " + originalFilename);
                String filePath = request.getSession().getServletContext()
                        .getRealPath("/")
                        + "upload\\" + file.getOriginalFilename();
                System.out.println("originalFilename : " + originalFilename);
                list.add(originalFilename);
                File saveDir = new File(filePath);
                if (!saveDir.getParentFile().exists())
                    saveDir.getParentFile().mkdirs();

                // 转存文件
                file.transferTo(saveDir);
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
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
                flag --;
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

    public InvoiceMain insertInvoiceMain(InvoiceMain invoiceMain) {
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
//        Results results = Results.OK();
//        results.setResult(invoiceMain);
        return invoiceMain;
    }

    public InvoiceMain getInvoiceMain(String invoiceMainId) {
//        Results results = new Results(Results.empty);
        InvoiceMain invoiceMain = new InvoiceMain();
        if(StringUtils.isEmpty(invoiceMainId)) return null;
        Optional<InvoiceMain> invoiceMainOptional = invoiceMainDao.findById(invoiceMainId);
        if(invoiceMainOptional.isPresent()){
            invoiceMain = invoiceMainOptional.get();
            if(invoiceMain != null){
                Invoice invoice = invoiceMain.getWordsResult();
                this.queryAndSetElementByInvoice(invoice);
            }
//            results.setState(Results.OK);
//            results.setResult(invoiceMain);
        }
        return invoiceMain;
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

    public void senderMQ(String context){
        sender.send();
        if(StringUtils.isBlank(context)) {
            context = "testMQ for WQB";
        }
        sender.sendMessage(context);
    }
}
