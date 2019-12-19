package com.doctor.assistant.ImageRecognition.JspFunction;

import com.doctor.assistant.ImageRecognition.entity.Element;

import java.util.List;

public class IteratorValue {
    public static String rollGetValue(int index, int elementRow,String elementWord, List<Element> elements){
        if(index == elementRow) return elementWord;
        for (Element e : elements) {
            if(e.getRow() == index) return e.getWord();
        }
        return "";
    }
}