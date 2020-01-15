//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mh.FlatForm.controller;

import com.alibaba.fastjson.JSONObject;
import com.mh.FlatForm.entity.DependencyBean;
import com.mh.FlatForm.entity.ParamBean;
import com.mh.FlatForm.enums.ResultEnum;
import com.mh.FlatForm.service.RepositoryService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping({"/demo"})
public class MakeJarController {

    @Autowired
    private RepositoryService repositoryService;
    private static String USER_PATH = "C:\\Users\\";

    @RequestMapping({"/makeJar"})
    public String makeJar() {
        System.out.println("转到 makeJar.jsp");
        return "make-jar";
    }

    @RequestMapping(
            value = {"/checkStyle"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public String parseRepository(HttpServletRequest request, ParamBean paramBean) {
        System.out.println("请求 /demo/checkStyle");
        JSONObject jsonObject = new JSONObject();
        String result = null;
        List<String> errorList = new ArrayList();
        List<String> successList = new ArrayList();
        String uploadFile = paramBean.getUploadFile().trim();
        System.out.println("uploadFile:" + uploadFile);
        jsonObject.put("result", "请选择jar包所在的路径");
        if (StringUtils.isBlank(uploadFile)) {
            return jsonObject.toJSONString();
        } else {
            String mavenSrc = paramBean.getMavenSrc().trim();
            jsonObject.put("result", "请输入pom包内容");
            if (StringUtils.isBlank(mavenSrc)) {
                return jsonObject.toJSONString();
            } else {
                mavenSrc = mavenSrc.replace("\r", "").replace("\n", "").replace("\t", "");
                String fileDirs = paramBean.getFileDirs().trim();
                System.out.println("fileDirs:" + fileDirs);
                File repositoryFile = null;
                if (StringUtils.isNotBlank(fileDirs)) {
                    repositoryFile = new File(fileDirs);
                    jsonObject.put("result", "输入本地仓库路径不存在，核查一下！");
                    if (!repositoryFile.exists()) {
                        return jsonObject.toJSONString();
                    }
                } else {
                    repositoryFile = this.repositoryService.getAllFileNames(USER_PATH);
                }

                jsonObject.put("result", "本地仓库路径不是默认C:\\User\\你的用户\\.m2\\repository；同时，你没有填写自己的本地仓库路径，请填写！");
                if (repositoryFile == null) {
                    return jsonObject.toJSONString();
                } else {
                    System.out.println("repositoryFile's Path:" + (repositoryFile != null ? repositoryFile.getPath() : ""));
                    Document doc = null;

                    try {
                        ArrayList dependencyBeanList = new ArrayList();

                        try {
                            doc = DocumentHelper.parseText(mavenSrc);
                        } catch (DocumentException e21) {
                            System.out.println("dependencies 补充前：" + mavenSrc);
                            if (!mavenSrc.contains("<dependencies>")) {
                                mavenSrc = "<dependencies>" + mavenSrc;
                            }

                            if (!mavenSrc.contains("</dependencies>")) {
                                mavenSrc = mavenSrc + "</dependencies>";
                            }

                            doc = DocumentHelper.parseText(mavenSrc);
                            System.out.println("dependencies 补充后：" + mavenSrc);
                        }

                        Element rootElt = doc.getRootElement();
                        Iterator dependency = rootElt.elementIterator("dependency");

                        label100:
                        while(true) {
                            DependencyBean dependencyBean;
                            Element groupIdElement;
                            Element artifactIdElement;
                            Element versionElement;
                            while(true) {
                                Element recordEle;
                                while(true) {
                                    while(true) {
                                        if (!dependency.hasNext()) {
                                            break label100;
                                        }

                                        dependencyBean = new DependencyBean();
                                        recordEle = (Element)dependency.next();
                                        groupIdElement = recordEle.element("groupId");
                                        if (groupIdElement == null) {
                                            break;
                                        }

                                        if (!StringUtils.isBlank(groupIdElement.getText())) {
                                            dependencyBean.setGroupId(groupIdElement.getText());
                                            break;
                                        }
                                    }

                                    artifactIdElement = recordEle.element("artifactId");
                                    if (artifactIdElement == null) {
                                        break;
                                    }

                                    if (!StringUtils.isBlank(artifactIdElement.getText())) {
                                        dependencyBean.setArtifactId(artifactIdElement.getText());
                                        break;
                                    }
                                }

                                versionElement = recordEle.element("version");
                                if (versionElement == null) {
                                    break;
                                }

                                if (!StringUtils.isBlank(versionElement.getText())) {
                                    dependencyBean.setVersion(versionElement.getText());
                                    break;
                                }
                            }

                            boolean flag = this.repositoryService.existsJarForLocalRepository(repositoryFile, dependencyBean);
                            if (flag) {
                                result = (errorList == null ? "1" : errorList.size() + 1) + "、" + repositoryFile.getPath() + "\\" + String.join("\\", groupIdElement.getText().split("\\.")) + "\\" + artifactIdElement.getText() + "\\" + versionElement.getText() + "\\" + artifactIdElement.getText() + "-" + versionElement.getText() + ".jar 包已存在！  ";
                                System.out.println(result);
                                errorList.add("\r\n" + result);
                            } else {
                                dependencyBeanList.add(dependencyBean);
                                ResultEnum resultEnum = this.repositoryService.existsJarForLocal(uploadFile, dependencyBean);
                                if (resultEnum == ResultEnum.NULL) {
                                    result = (errorList == null ? "1" : errorList.size() + 1) + "、存在，在选定的路径下，未找到相应的jar包！请检查【路径】或【dependency】  ";
                                    System.out.println(result);
                                    errorList.add("\r\n" + result);
                                } else {
                                    flag = this.repositoryService.executeMavenCmd(uploadFile, dependencyBean, resultEnum);
                                    if(!flag){
                                        result = (errorList == null ? "1" : errorList.size() + 1) +  "、" + artifactIdElement.getText() + "-" + versionElement.getText()+ ".jar 打包失败 ERROR！  ";
                                        errorList.add("\r\n" + result);
                                    }else{
                                        result = artifactIdElement.getText() + "-" + versionElement.getText()+ ".jar";
                                        successList.add(result);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    result = "";
                    if(!successList.isEmpty()){
                        result = "【打包成功数】：" + successList.size()+";\n"+
                        "已将 "+String.join(",", successList)+" 打包进本地仓库。\n\n";
                    }

                    if (!errorList.isEmpty()) {
                        result = result + "【打包失败数】：" + errorList.size() + ";\n";
                        jsonObject.put("result", result + String.join("\r\n", errorList));
                    } else {
                        jsonObject.put("result", result);
                    }
                    return jsonObject.toJSONString();
                }
            }
        }
    }
}
