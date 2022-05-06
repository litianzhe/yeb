package com.xxxx.server.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @className: DateConverter
 * @copyright: HTD
 * @description: 日期转换
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/3/3
 * @version: 1.0
 */
@Component
public class DateConverter implements Converter<String, LocalDate> {


	@Override
	public LocalDate convert(String s) {

		try {
			return LocalDate.parse( s , DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) );
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
