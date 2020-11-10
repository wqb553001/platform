package com.doctor.assistant.experiment;

import com.doctor.assistant.experiment.czy.feignclient.IFeignCalculateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExperimentApplication {
    private IFeignCalculateClient feignCalculateClient;

    @Autowired
    private void setFeignCalculateClient(IFeignCalculateClient feignCalculateClient){
        this.feignCalculateClient = feignCalculateClient;
    }
    public static void main(String[] args) {
        SpringApplication.run(ExperimentApplication.class, args);
    }

}
