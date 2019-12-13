//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mh.FlatForm.entity;

public class ParamBean {
    String fileDirs;
    String mavenSrc;
    String uploadFile;

    public ParamBean() {
    }

    public String getFileDirs() {
        return this.fileDirs;
    }

    public String getMavenSrc() {
        return this.mavenSrc;
    }

    public String getUploadFile() {
        return this.uploadFile;
    }

    public void setFileDirs(final String fileDirs) {
        this.fileDirs = fileDirs;
    }

    public void setMavenSrc(final String mavenSrc) {
        this.mavenSrc = mavenSrc;
    }

    public void setUploadFile(final String uploadFile) {
        this.uploadFile = uploadFile;
    }


    public String toString() {
        return "ParamBean(fileDirs=" + this.getFileDirs() + ", mavenSrc=" + this.getMavenSrc() + ", uploadFile=" + this.getUploadFile() + ")";
    }
}
