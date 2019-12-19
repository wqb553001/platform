package com.doctor.assistant.ImageRecognition.utils;

import lombok.Data;

@Data
public class Results {
    private int status = 200;
    private Object result;
    private String message = "SUCCESS";

    public static Results OK = new Results(200);

    public Results() {
    }

    public Results(int status) {
        this.status = status;
        this.result = null;
    }

    public Results(int status, Object result, String message) {
        this.status = status;
        this.result = result;
        this.message = message;
    }

    public Results(int status, Object result) {
        this.status = status;
        this.result = result;
    }

    public Results(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
