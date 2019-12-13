//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mh.FlatForm.entity;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

public class ParentBean {
    private String name;
    @Resource
    private int age;
    @Autowired
    private TestBean testBean;

    public ParentBean() {
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public TestBean getTestBean() {
        return this.testBean;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public void setTestBean(final TestBean testBean) {
        this.testBean = testBean;
    }

    public String toString() {
        return "ParentBean(name=" + this.getName() + ", age=" + this.getAge() + ", testBean=" + this.getTestBean() + ")";
    }
}
