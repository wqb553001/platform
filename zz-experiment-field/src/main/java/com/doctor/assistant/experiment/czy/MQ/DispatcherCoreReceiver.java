package com.doctor.assistant.experiment.czy.MQ;

import com.alibaba.fastjson.JSONObject;
import com.doctor.assistant.experiment.czy.config.RabbitMQConfig;
import com.doctor.assistant.experiment.czy.entity.EvalKpi;
import com.doctor.assistant.experiment.czy.entity.EvalKpiSimValue;
import com.doctor.assistant.experiment.czy.feignclient.FeignCalculateService;
import com.doctor.assistant.experiment.czy.feignclient.IFeignCalculateClient;
import com.doctor.assistant.experiment.czy.repository.EvalKpiRepository;
import com.doctor.assistant.experiment.czy.repository.EvalKpiSimValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/dispatchCore")
@RabbitListener(queues = RabbitMQConfig.calculate)
public class DispatcherCoreReceiver {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherCoreReceiver.class);
    @Autowired
    Sender sender;
    @Autowired
    EvalKpiRepository evalKpiRepository;
    @Autowired
    FeignCalculateService feignCalculateService;
    @Autowired
    EvalKpiSimValueRepository evalKpiSimValueRepository;
    @Autowired
    IFeignCalculateClient feignCalculateClient;
    @Autowired
    private RedisTemplate<String, String> template;

    @RabbitHandler
    public void process(String message) throws URISyntaxException {
        logger.info("分发中心：接收到通知 === {} -Receiver : {}", RabbitMQConfig.calculate, message);
        this.afterHandler(feignCalculate(JSONObject.parseObject(message, EvalKpi.class)));
    }

    private String feignCalculate(EvalKpi evalKpi) throws URISyntaxException{
        String calculateServiceUrl = evalKpi.getCalculateServiceUrl();
        String calculateInputJson = evalKpi.getCalculateInputJson();
        logger.info("分发中心：匹配算子，calculateServiceUrl : {}，calculateInputJson ：{}", calculateServiceUrl, calculateInputJson);
//         方式（1）
//        URI uri = new URI(calculateServiceUrl);
//        return feignCalculateService.calculate(calculateServiceUrl, calculateInputJson);
//
//        方式（2）
//        ValueOperations<String, String> redis = template.opsForValue();
//        String serviceHostAddr = redis.get(calculateServiceUrl);
//        …………
        return feignCalculateClient.calculate(calculateInputJson);
    }

    private void afterHandler(String calculateResult) throws URISyntaxException {
        EvalKpiSimValue evalKpiSimValue = JSONObject.parseObject(calculateResult, EvalKpiSimValue.class);
        if(evalKpiSimValue != null){
            evalKpiSimValueRepository.save(evalKpiSimValue); // 以 kpiId 为id, 有记录则 update，无记录则 insert
        }
        Integer kpiId = evalKpiSimValue.getKpiId();
        EvalKpi evalKpi = evalKpiRepository.findById(kpiId).get();
        Integer parentId = evalKpi.getParentId();
        if(parentId != 0 && evalKpi.getLevel() != 0){
            logger.info("分发中心：继续处理 父节点 parentId：{}", parentId);
            evalKpi = evalKpiRepository.findById(parentId).get();
            if(evalKpi != null) {
                this.feignCalculate(evalKpi);
                return;
            }
            logger.error("分发中心：根据父节点id parentId ：{}，未找到相应的记录。kpiId : {}", parentId, kpiId);
        }
    }

    @RequestMapping("/dispatchCoreCallBack/{calculateResult}")
    public void callBack(@PathVariable String calculateResult) throws URISyntaxException{
        this.afterHandler(calculateResult);
    }
}
