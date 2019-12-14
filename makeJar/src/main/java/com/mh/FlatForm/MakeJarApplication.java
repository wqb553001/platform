//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mh.FlatForm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(
    exclude = {DataSourceAutoConfiguration.class}
)
public class MakeJarApplication extends SpringBootServletInitializer {
    public MakeJarApplication() {
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(new Class[]{MakeJarApplication.class});
    }

    public static void main(String[] args) {
        SpringApplication.run(MakeJarApplication.class, args);
    }
}
