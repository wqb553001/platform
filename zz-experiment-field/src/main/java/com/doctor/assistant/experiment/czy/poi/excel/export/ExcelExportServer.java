/**
 * Copyright 2013-2015 JueYue (qrb.jueyue@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.doctor.assistant.experiment.czy.poi.excel.export;

import com.doctor.assistant.experiment.czy.poi.excel.annotation.ExcelTarget;
import com.doctor.assistant.experiment.czy.poi.excel.entity.enmus.ExcelType;
import com.doctor.assistant.experiment.czy.poi.excel.entity.params.ExcelExportEntity;
import com.doctor.assistant.experiment.czy.poi.excel.entity.params.ExportParams;
import com.doctor.assistant.experiment.czy.poi.excel.entity.vo.PoiBaseConstants;
import com.doctor.assistant.experiment.czy.poi.excel.export.base.ExcelExportBase;
import com.doctor.assistant.experiment.czy.poi.excel.export.styler.IExcelExportStyler;
import com.doctor.assistant.experiment.czy.poi.excel.utils.PoiPublicUtil;
import com.doctor.assistant.experiment.czy.poi.exception.excel.ExcelExportException;
import com.doctor.assistant.experiment.czy.poi.exception.excel.enums.ExcelExportEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Excel导出服务
 *
 * @author JueYue
 * @date 2014年6月17日 下午5:30:54
 */
public class ExcelExportServer extends ExcelExportBase {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelExportServer.class);

    // 最大行数,超过自动多Sheet
    private int MAX_NUM = 60000;

    private int createHeaderAndTitle(ExportParams entity, Sheet sheet, Workbook workbook, List<ExcelExportEntity> excelParams) {
        int rows = 0, feildWidth = getFieldWidth(excelParams);
        if (entity.getTitle() != null) {
            rows += createHeaderRow(entity, sheet, workbook, feildWidth);
        }
        rows += createTitleRow(entity, sheet, workbook, rows, excelParams);
        sheet.createFreezePane(0, rows, 0, rows);
        return rows;
    }

    /**
     * 创建 表头改变
     *
     * @param entity
     * @param sheet
     * @param workbook
     * @param feildWidth
     */
    public int createHeaderRow(ExportParams entity, Sheet sheet, Workbook workbook, int feildWidth) {
        Row row = sheet.createRow(0);
        row.setHeight(entity.getTitleHeight());
        createStringCell(row, 0, entity.getTitle(), getExcelExportStyler().getHeaderStyle(entity.getHeaderColor()), null);
        for (int i = 1; i <= feildWidth; i++) {
            createStringCell(row, i, "", getExcelExportStyler().getHeaderStyle(entity.getHeaderColor()), null);
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, feildWidth));
        if (entity.getSecondTitle() != null) {
            row = sheet.createRow(1);
            row.setHeight(entity.getSecondTitleHeight());
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(CellStyle.ALIGN_RIGHT);
            createStringCell(row, 0, entity.getSecondTitle(), style, null);
            for (int i = 1; i <= feildWidth; i++) {
                createStringCell(row, i, "", getExcelExportStyler().getHeaderStyle(entity.getHeaderColor()), null);
            }
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, feildWidth));
            return 2;
        }
        return 1;
    }

    public void createSheet(Workbook workbook, ExportParams entity, Class<?> pojoClass, Collection<?> dataSet) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Excel export start ,class is {}", pojoClass);
            LOGGER.debug("Excel version is {}", entity.getType().equals(ExcelType.HSSF) ? "03" : "07");
        }
        if (workbook == null || entity == null || pojoClass == null || dataSet == null) {
            throw new ExcelExportException(ExcelExportEnum.PARAMETER_ERROR);
        }
        super.type = entity.getType();
        if (type.equals(ExcelType.XSSF)) {
            MAX_NUM = 1000000;
        }
        Sheet sheet = null;
        try {
            sheet = workbook.createSheet(entity.getSheetName());
        } catch (Exception e) {
            // 重复遍历,出现了重名现象,创建非指定的名称Sheet
            sheet = workbook.createSheet();
        }
        try {
            dataHanlder = entity.getDataHanlder();
            if (dataHanlder != null) {
                needHanlderList = Arrays.asList(dataHanlder.getNeedHandlerFields());
            }
            // 创建表格样式
            setExcelExportStyler((IExcelExportStyler) entity.getStyle().getConstructor(Workbook.class).newInstance(workbook));
            Drawing patriarch = sheet.createDrawingPatriarch();
            List<ExcelExportEntity> excelParams = new ArrayList<ExcelExportEntity>();
            if (entity.isAddIndex()) {
                excelParams.add(indexExcelEntity(entity));
            }
            // 得到所有字段
            Field fileds[] = PoiPublicUtil.getClassFields(pojoClass);
            ExcelTarget etarget = pojoClass.getAnnotation(ExcelTarget.class);
            String targetId = etarget == null ? null : etarget.value();
            getAllExcelField(entity.getExclusions(), targetId, fileds, excelParams, pojoClass, null);
            sortAllParams(excelParams);
            int index = entity.isCreateHeadRows() ? createHeaderAndTitle(entity, sheet, workbook, excelParams) : 0;
            int titleHeight = index;
            setCellWith(excelParams, sheet);
            short rowHeight = getRowHeight(excelParams);
            setCurrentIndex(1);
            Iterator<?> its = dataSet.iterator();
            List<Object> tempList = new ArrayList<Object>();
            while (its.hasNext()) {
                Object t = its.next();
                index += createCells(patriarch, index, t, excelParams, sheet, workbook, rowHeight);
                tempList.add(t);
                if (index >= MAX_NUM)
                    break;
            }
            mergeCells(sheet, excelParams, titleHeight);

            if (entity.getFreezeCol() != 0) {
                sheet.createFreezePane(entity.getFreezeCol(), 0, entity.getFreezeCol(), 0);
            }

            its = dataSet.iterator();
            for (int i = 0, le = tempList.size(); i < le; i++) {
                its.next();
                its.remove();
            }
            // 创建合计信息
            addStatisticsRow(getExcelExportStyler().getStyles(true, null), sheet);

            // 发现还有剩余list 继续循环创建Sheet
            if (dataSet.size() > 0) {
                createSheet(workbook, entity, pojoClass, dataSet);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcelExportException(ExcelExportEnum.EXPORT_ERROR, e.getCause());
        }
    }

    public void createSheetForMap(Workbook workbook, ExportParams entity, List<ExcelExportEntity> entityList, Collection<? extends Map<?, ?>> dataSet) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Excel version is {}", entity.getType().equals(ExcelType.HSSF) ? "03" : "07");
        }
        if (workbook == null || entity == null || entityList == null || dataSet == null) {
            throw new ExcelExportException(ExcelExportEnum.PARAMETER_ERROR);
        }
        super.type = entity.getType();
        if (type.equals(ExcelType.XSSF)) {
            MAX_NUM = 1000000;
        }
        Sheet sheet = null;
        try {
            sheet = workbook.createSheet(entity.getSheetName());
        } catch (Exception e) {
            // 重复遍历,出现了重名现象,创建非指定的名称Sheet
            sheet = workbook.createSheet();
        }
        try {
            dataHanlder = entity.getDataHanlder();
            if (dataHanlder != null) {
                needHanlderList = Arrays.asList(dataHanlder.getNeedHandlerFields());
            }
            // 创建表格样式
            setExcelExportStyler((IExcelExportStyler) entity.getStyle().getConstructor(Workbook.class).newInstance(workbook));
            Drawing patriarch = sheet.createDrawingPatriarch();
            List<ExcelExportEntity> excelParams = new ArrayList<ExcelExportEntity>();
            if (entity.isAddIndex()) {
                excelParams.add(indexExcelEntity(entity));
            }
            excelParams.addAll(entityList);
            sortAllParams(excelParams);
            int index = entity.isCreateHeadRows() ? createHeaderAndTitle(entity, sheet, workbook, excelParams) : 0;
            int titleHeight = index;
            setCellWith(excelParams, sheet);
            short rowHeight = getRowHeight(excelParams);
            setCurrentIndex(1);
            Iterator<?> its = dataSet.iterator();
            List<Object> tempList = new ArrayList<Object>();
            while (its.hasNext()) {
                Object t = its.next();
                index += createCells(patriarch, index, t, excelParams, sheet, workbook, rowHeight);
                tempList.add(t);
                if (index >= MAX_NUM)
                    break;
            }
            if (entity.getFreezeCol() != 0) {
                sheet.createFreezePane(entity.getFreezeCol(), 0, entity.getFreezeCol(), 0);
            }

            mergeCells(sheet, excelParams, titleHeight);

            its = dataSet.iterator();
            for (int i = 0, le = tempList.size(); i < le; i++) {
                its.next();
                its.remove();
            }
            // 发现还有剩余list 继续循环创建Sheet
            if (dataSet.size() > 0) {
                createSheetForMap(workbook, entity, entityList, dataSet);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcelExportException(ExcelExportEnum.EXPORT_ERROR, e.getCause());
        }
    }

    /**
     * 创建表头
     *
     * @param title
     * @param index
     */
    private int createTitleRow(ExportParams title, Sheet sheet, Workbook workbook, int index, List<ExcelExportEntity> excelParams) {
        List<ExcelExportEntity> temp = new ArrayList<>();
        temp.addAll(excelParams);
        int rows = getRowNums(temp);
//        Row listRow = null;
//        if (rows >= 2) {
//            listRow = sheet.createRow(index + 1);
//            listRow.setHeight((short) 450);
//        }
//        int cellIndex = 0;
        CellStyle titleStyle = getExcelExportStyler().getTitleStyle(title.getColor());
        Map<Integer, String> jump = new LinkedHashMap<>();
        for (int x = 0, i = 0, n = 0; x < rows; x++) {
            Row row = sheet.createRow(index + x);
            row.setHeight((short) 450);
            List<ExcelExportEntity> tempList = new ArrayList<>();
            for (int j = 0, exportFieldTitleSize = temp.size(); j < exportFieldTitleSize; j++, i++) {
                ExcelExportEntity entity = temp.get(j);
                if (StringUtils.isNotBlank(entity.getName())) {
                    if (jump.get(i) != null) {
                        String value = jump.get(i);
                        String[] str = value.split("_");
                        i = Integer.parseInt(str[0]);
                    }
                    createStringCell(row, i, entity.getName(), titleStyle, entity);
                }
                if (entity.getList() != null) {
                    List<ExcelExportEntity> sTitel = entity.getList();
                    int last = sTitel.size();
                    last = getLastCol(last, sTitel);
                    // 横向合并
                    sheet.addMergedRegion(new CellRangeAddress(index + x, index + x, i, i + last - 1));
                    if (n == 0) {
                        int len = getExcelCellSize(temp);
                        jump.put(len, i + "_" + last);
                    } else {
                        try {
                            Field tail = null;
                            tail = jump.getClass().getDeclaredField("tail");
                            tail.setAccessible(true);
                            Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) tail.get(jump);
                            String value = entry.getValue();
                            String[] str = value.split("_");
                            jump.put(Integer.parseInt(str[0]) + Integer.parseInt(str[1]), i + "_" + last);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    tempList.addAll(entity.getList());
                    i += last - 1;
                    n++;
                } else if (x < rows - 1) {
                    // 纵向合并
                    sheet.addMergedRegion(new CellRangeAddress(index + x, index + rows - 1, i, i));
                }
                if (j == exportFieldTitleSize - 1) {
                    temp.clear();
                    temp.addAll(tempList);
                    tempList.clear();
                }
//                if (entity.getList() != null) {
//                    List<ExcelExportEntity> sTitel = entity.getList();
//                    int last = sTitel.size();
//                    last = getLastCol(last, sTitel);
//                    if (StringUtils.isNotBlank(entity.getName())) {
//                        sheet.addMergedRegion(new CellRangeAddress(index, index, cellIndex, cellIndex + last - 2));
//                    }
//                    for (int j = 0, size = sTitel.size(); j < size; j++) {
//                        createStringCell(rows >= 2 ? listRow : row, cellIndex, sTitel.get(j).getName(), titleStyle, entity);
//                        if (rows >= 2) {
//                            if (sTitel.get(j).getList() == null) {
//                                sheet.addMergedRegion(new CellRangeAddress(index + 1, index + (rows - 1), cellIndex, cellIndex));
//                            } else {
//                                sheet.addMergedRegion(new CellRangeAddress(index + 1, index + 1, cellIndex, cellIndex + (last - sTitel.size() - 1)));
//                            }
//                        }
//                        cellIndex++;
//                    }
//                    cellIndex--;
//                } else if (rows >= 2) {
//                    createStringCell(listRow, cellIndex, "", titleStyle, entity);
//                    sheet.addMergedRegion(new CellRangeAddress(index, index + (rows - 1), cellIndex, cellIndex));
//                }
//                cellIndex++;
            }
        }
        return rows;

    }

    private int getExcelCellSize(List<ExcelExportEntity> temp) {
        int len = temp.size();
        for (ExcelExportEntity t : temp) {
            if (t.getList() != null) {
                len += getExcelCellSize(t.getList());
                len--;
            }
        }
        return len;
    }

    private void createRowAndCol(List<ExcelExportEntity> excelParams, Sheet sheet, Row row, CellStyle titleStyle, int index, int i) {
    }

    private int getLastCol(int last, List<ExcelExportEntity> sTitel) {
        for (ExcelExportEntity excelExportEntity : sTitel) {
            if (excelExportEntity.getList() != null) {
                last += excelExportEntity.getList().size() - 1;
                last = getLastCol(last, excelExportEntity.getList());
                break;
            }
        }
        return last;
    }

    /**
     * 判断表头是只有一行还是多行
     *
     * @param excelParams
     * @return
     */
    private int getRowNums(List<ExcelExportEntity> excelParams) {
        int rowNumsChild = 1;
        for (int i = 0; i < excelParams.size(); i++) {
            if (excelParams.get(i).getList() != null && StringUtils.isNotBlank(excelParams.get(i).getName())) {
                int tempRowNums = 1;
                tempRowNums += getRowNums(excelParams.get(i).getList());
                if (tempRowNums > rowNumsChild) {
                    rowNumsChild = tempRowNums;
                }
            }
        }
        return rowNumsChild;
    }

    private ExcelExportEntity indexExcelEntity(ExportParams entity) {
        ExcelExportEntity exportEntity = new ExcelExportEntity();
        exportEntity.setOrderNum(0);
        exportEntity.setName(entity.getIndexName());
        exportEntity.setWidth(10);
        exportEntity.setFormat(PoiBaseConstants.IS_ADD_INDEX);
        return exportEntity;
    }

}
