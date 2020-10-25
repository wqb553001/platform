package com.activitiserver.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public IRule createRule() {
//        //随机
//        new RandomRule();
//        //轮询
//        new RoundRobinRule();
//        //权重
//        new WeightedResponseTimeRule();
//        //最小并发
//        new BestAvailableRule();
//        //区域感知——就近原则
//        new ZoneAvoidanceRule();
        return new RoundRobinRule();
    }
}
