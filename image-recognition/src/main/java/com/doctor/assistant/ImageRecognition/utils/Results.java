package com.doctor.assistant.ImageRecognition.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Results {
    public static int OK = 200;
    private int state = 200;
    private Object result;
    private String message = "SUCCESS";
    public static int empty = ResultStatus.EMPTY.statusKey;

    static enum ResultStatus{
        SUCCESS(200, "SUCCESS"), EMPTY(501, "EMPTY");
        @Getter @Setter
        private int statusKey;
        @Getter @Setter
        private String statusValue;
        ResultStatus(int statusKey, String statusValue){
            this.statusKey = statusKey;
            this.statusValue = statusValue;
        }
    }

    public static Results OK(){
        return new Results(200);
    }

    private static class Single {
        private static Results install = new Results();
    }

    public static Results getInstall(){
        return Single.install;
    }

    private Results() {
    }

    public Results(int state) {
        this.state = state;
        this.result = null;
    }

    public Results(int state, Object result, String message) {
        this.state = state;
        this.result = result;
        this.message = message;
    }

    public Results(int state, Object result) {
        this.state = state;
        this.result = result;
    }

    public Results(int state, String message) {
        this.state = state;
        this.message = message;
    }
}
