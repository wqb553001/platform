package com.doctor.assistant.commonserver.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Results<T> {
    public static int OK = 200;
    private int state = 200;
    private boolean isOK = true;
    private T result = null;
//    private Object data;
//    private List<T> results = null;
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
        if(this.state != OK) this.isOK = false;
    }

    public Results(int state, T result, String message) {
        this.state = state;
        if(this.state != OK) this.isOK = false;
        this.result = result;
        this.message = message;
    }

    public Results(int state, T result) {
        this.state = state;
        if(this.state != OK) this.isOK = false;
        this.result = result;
    }

//    public Results(int state, List<T> results, String message) {
//        this.state = state;
//        if(this.state != OK) this.isOK = false;
//        this.results = results;
//        this.message = message;
//    }
//
//    public Results(int state, List<T> results) {
//        this.state = state;
//        if(this.state != OK) this.isOK = false;
//        this.results = results;
//    }

    public Results(int state, String message) {
        this.state = state;
        if(this.state != OK) this.isOK = false;
        this.message = message;
    }

}
