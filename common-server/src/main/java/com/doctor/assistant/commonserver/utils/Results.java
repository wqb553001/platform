package com.doctor.assistant.commonserver.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Results<T> implements Serializable {
    public static String SUCCESS = "SUCCESS";
    public static String ERROR = "ERROR";
    private boolean isOK = true;
    private String message = "SUCCESS";
    private T result = null;
    private List<T> results = null;

    public static Results getInstance(){
        return new Results<>();
    }


}
