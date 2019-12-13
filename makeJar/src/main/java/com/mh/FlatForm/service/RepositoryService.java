//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mh.FlatForm.service;

import com.mh.FlatForm.entity.DependencyBean;
import com.mh.FlatForm.enums.ResultEnum;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {
    private static final Logger logger = LogManager.getLogger(RepositoryService.class);

    public File getAllFileNames(String path) {
        File repositoryFile = null;
        File dir = new File(path);
        List<File> fileList = this.getAllFilePath(dir);
        File m2File = this.getM2File(fileList, ".m2");
        if (m2File != null && m2File.isDirectory()) {
            File[] repositoryFiles = m2File.listFiles();
            repositoryFile = this.getRepositoryFile(repositoryFiles, "repository");
        }

        return repositoryFile;
    }

    private List<File> getAllFilePath(File dir) {
        List<File> fileList = new ArrayList();
        File[] files = dir.listFiles();

        for(int i = 0; files != null && i < files.length; ++i) {
            if (files[i].isDirectory()) {
                String fileName = files[i].getName().trim();
                if (!fileName.equalsIgnoreCase("Default") && !fileName.equalsIgnoreCase("All Users") && !fileName.equalsIgnoreCase("Default User") && !fileName.equalsIgnoreCase("Public")) {
                    fileList.add(files[i]);
                    System.out.println(files[i].getPath());
                }
            }
        }

        return fileList;
    }

    private File getM2File(List<File> fileList, String disFileName) {
        File m2File = null;
        if (fileList != null && fileList.size() > 0) {
            Iterator var4 = fileList.iterator();

            while(var4.hasNext()) {
                File file = (File)var4.next();
                File[] files = file.listFiles();
                m2File = this.getRepositoryFile(files, disFileName);
                if (m2File != null) {
                    break;
                }
            }
        }

        return m2File;
    }

    private File getRepositoryFile(File[] files, String disFileName) {
        File repositoryFile = null;

        for(int i = 0; files != null && i < files.length; ++i) {
            if (files[i].isDirectory()) {
                String fileName = files[i].getName().trim();
                if (fileName.equalsIgnoreCase(disFileName)) {
                    repositoryFile = files[i];
                    break;
                }
            }
        }

        return repositoryFile;
    }

    public boolean existsJarForLocalRepository(File repositoryFile, DependencyBean dependencyBean) {
        String repositoryPath = repositoryFile.getPath();
        String resultPath = repositoryPath + "\\" + dependencyBean.getGroupId().replace(".", "\\") + "\\" + dependencyBean.getArtifactId() + "\\" + dependencyBean.getVersion() + "\\" + dependencyBean.getArtifactId() + "-" + dependencyBean.getVersion() + ".jar";
        File disFile = new File(resultPath);
        return disFile.exists();
    }

    public ResultEnum existsJarForLocal(String jarPath, DependencyBean dependencyBean) {
        System.out.println("judge.existsJar():" + jarPath);
        jarPath = this.getPath(jarPath);
        File jarFile = null;
        String jarAllPath = "";
        jarAllPath = jarPath + "\\" + dependencyBean.getArtifactId() + "-" + dependencyBean.getVersion() + ".jar";
        System.out.println("jarAllPath1:" + jarAllPath);
        jarFile = new File(jarAllPath);
        if (jarFile != null && jarFile.exists()) {
            return ResultEnum.FULL;
        } else {
            if (StringUtils.isNotBlank(dependencyBean.getVersion()) && ("1.0.0".equals(dependencyBean.getVersion()) || "0.0.1".equals(dependencyBean.getVersion()) || "1.0.0.0".equals(dependencyBean.getVersion()) || "0.0.0.1".equals(dependencyBean.getVersion()))) {
                jarAllPath = jarPath + "\\" + dependencyBean.getArtifactId() + ".jar";
            }

            System.out.println("jarAllPath2:" + jarAllPath);
            jarFile = new File(jarAllPath);
            return jarFile != null && jarFile.exists() ? ResultEnum.DEFECT : ResultEnum.NULL;
        }
    }

    public String getPath(String jarPath) {
        if (StringUtils.isBlank(jarPath)) {
            return "";
        } else {
            System.out.println("INTO <== jarPath = initValue = " + jarPath);
            if (StringUtils.isNotBlank(jarPath) && jarPath.contains(",")) {
                jarPath = jarPath.substring(jarPath.lastIndexOf(",") + 1, jarPath.length());
            }
            // 没有输入 “\”，如 “ D： ”，自动加上，为 “ D：\ ”
            if(jarPath.contains(":") && !jarPath.contains("\\")){
                jarPath = jarPath+"\\";
            }
            System.out.println("OUT ==> jarPath = returnValue = " + jarPath);
            return jarPath;
        }
    }

    public boolean executeMavenCmd(String jarPath, DependencyBean dependencyBean, ResultEnum resultEnum) {
        if (StringUtils.isBlank(jarPath)) return false;
        boolean flag = false;
        System.out.println("exec.Before():" + jarPath);
        jarPath = this.getPath(jarPath);
        jarPath = jarPath.replace("\\", "\\\\");
        System.out.println("jarPath.replace(\\ => \\\\).After():" + jarPath);
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            String cmd = "cmd /c   cd " + jarPath + " && mvn install:install-file  -Dfile=" + jarPath + "\\\\" + dependencyBean.getArtifactId() + (resultEnum == ResultEnum.FULL ? "-" + dependencyBean.getVersion() : "") + ".jar -DgroupId=" + dependencyBean.getGroupId() + " -DartifactId=" + dependencyBean.getArtifactId() + " -Dversion=" + dependencyBean.getVersion() + " -Dpackaging=jar";
            System.out.println(cmd);
            process = runtime.exec(cmd);
            synchronized (process) {
                flag = this.systemOutInfo(process);
                process.waitFor();
                process.destroy();
            }
        } catch (IOException var7) {
            var7.printStackTrace();
        } catch (InterruptedException var8) {
            var8.printStackTrace();
        }
        System.out.println("执行结束！！");
        return flag;
    }

    private boolean systemOutInfo(Process process) {
        boolean flagIn = false;
        boolean flagEr = false;
        try {
            InputStream is = process.getInputStream();
            InputStream es = process.getErrorStream();
            flagIn = this.printLog(is);
            flagEr = this.printLog(es);
        } catch (Exception var4) {
            var4.printStackTrace();
        }
        return flagIn|flagEr;
    }

    private boolean printLog(InputStream in) {
        try {
            String writeLog = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "GBK"));

            String line;
            while((line = br.readLine()) != null) {
                if (line.contains("Installing")) {
                    writeLog = line;
                }

                if (line.contains("BUILD SUCCESS")) {
                    logger.error(">>>{}", writeLog);
                    return true;
                }
            }
        } catch (IOException var5) {
            var5.printStackTrace();
        } catch (Exception var6) {
            var6.printStackTrace();
        }
        return false;
    }


}
