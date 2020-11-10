package com.doctor.assistant.experiment.czy.MQ;

import com.alibaba.fastjson.JSONObject;
import com.doctor.assistant.experiment.czy.config.RabbitMQConfig;
import com.doctor.assistant.experiment.czy.entity.EvalInputMessage;
import com.doctor.assistant.experiment.czy.entity.EvalInputMessageKpiRef;
import com.doctor.assistant.experiment.czy.entity.EvalKpi;
import com.doctor.assistant.experiment.czy.repository.EvalInputMessageKpiRefRepository;
import com.doctor.assistant.experiment.czy.repository.EvalInputMessageRepository;
import com.doctor.assistant.experiment.czy.repository.EvalKpiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 数据接入线程
 */
@Component
@RabbitListener(queues = RabbitMQConfig.newDataInput)
public class DataInputReceiver {
    private static final Logger logger = LoggerFactory.getLogger(DataInputReceiver.class);
    @Autowired
    Sender sender;
    @Autowired
    EvalKpiRepository evalKpiRepository;
    @Autowired
    EvalInputMessageRepository evalInputMessageRepository;
    @Autowired
    EvalInputMessageKpiRefRepository evalInputMessageKpiRefRepository;

    @RabbitHandler
    public void process(String message) {
        logger.info("数据接入线程：接收通知 === {}-Receiver:{}",RabbitMQConfig.newDataInput, message);
        List<EvalInputMessage> evalInputMessages = evalInputMessageRepository.findByMessageName(message);
        if (!CollectionUtils.isEmpty(evalInputMessages)){
            for (EvalInputMessage eim : evalInputMessages) {
               Integer messageId = eim.getMessageId();
               if (Objects.nonNull(message)){
                   this.sendMessageToCalculate(evalInputMessageKpiRefRepository.findAllByMessageId(messageId));
               }
            }
        }
    }

    private void sendMessageToCalculate(List<EvalInputMessageKpiRef> evalInputMessageKpiRefs){
        if(!CollectionUtils.isEmpty(evalInputMessageKpiRefs)){
            evalInputMessageKpiRefs.stream().forEach(ref -> {
                Integer activeFlag = ref.getActiveFlag();
                if(activeFlag != null && activeFlag == 1){ // activeFlag = 1 表示有效
                    Integer kpiId = ref.getKpiId();
                    EvalKpi evalKpi = evalKpiRepository.findById(kpiId).get();
                    String evalKpiJson = JSONObject.toJSONString(evalKpi);
                    logger.info("数据接入线程：经检查，数据有效，通知运算中心。kpiId:{}，完整数据内容：{}", kpiId, evalKpiJson);
                    sender.send(RabbitMQConfig.calculate, evalKpiJson);
                }
            });
        }

    }
}
