package com.doctor.assistant.experiment;

import com.doctor.assistant.experiment.czy.entity.EvalKpiSimValue;
import com.doctor.assistant.experiment.czy.poi.excel.entity.params.ImportParams;
import com.doctor.assistant.experiment.czy.poi.excel.utils.ExcelImportUtil;
import com.doctor.assistant.experiment.czy.repository.EvalKpiSimValueRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class ExperimentApplicationTests {
    @Autowired
    EvalKpiSimValueRepository repository;
//    @Test
    void contextLoads() {
    }

    @Test
    public void testRepository() throws Exception {
//        repository.saveAll(readFile());
    }

    private List<EvalKpiSimValue> readFile() throws Exception{
        List<EvalKpiSimValue> list = new ArrayList<>();
        String path = "C:\\Users\\Administrator\\Desktop\\message-kpi-value.xls";
        File file = new File(path);
        FileInputStream input = new FileInputStream(file);

        ImportParams params = new ImportParams();
        params.setTitleRows(2);
        params.setHeadRows(2);
        params.setNeedSave(true);
        list =  ExcelImportUtil.importExcel(input, EvalKpiSimValue.class, params);
        return list;
    }
}
