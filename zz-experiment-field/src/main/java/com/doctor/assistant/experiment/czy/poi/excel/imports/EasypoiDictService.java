package com.doctor.assistant.experiment.czy.poi.excel.imports;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 描述：
 * @author：scott
 * @since：2017-4-12 下午05:15:04
 * @version:1.0
 */
@Service("easypoiDictService")
public class EasypoiDictService implements EasypoiDictServiceI {
	private Logger log = Logger.getLogger(EasypoiDictService.class);
	/**
	 * 通过字典查询easypoi，所需字典文本
	 * @param
	 * @return
	 * @author：qinfeng
	 * @since：2017-4-12 下午06:10:22
	 */
	public String[] queryDict(String dicTable, String dicCode,String dicText){

		return null;
	}
}
