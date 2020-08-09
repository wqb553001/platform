package com.doctor.assistant.userserver.springdata.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 解决@RequestBody接收json数据，Jackson 反序列化Date格式
 * @author scott
 *
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {
	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String text = jp.getText();

		if (StringUtils.hasText(text)) {
			System.out.println("日期处理出错前：" + text);

			try {
				if (text.indexOf(":") == -1 && text.length() == 10) {
					return this.dateFormat.parse(text);
				} else if (text.indexOf(":") > 0 && text.length() == 19) {
					return this.datetimeFormat.parse(text);
				} else if(org.apache.commons.lang.StringUtils.isNumeric(text)) {
					return null;
				} else {
					System.out.println("确实出错：" + text);
					throw new IllegalArgumentException("Could not parse date, date format is error ");
				}
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		} else {
			return null;
		}
	}

}
