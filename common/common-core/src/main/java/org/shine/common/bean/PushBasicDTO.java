/**
 * Copyright (c) 2008-2013 	Copyright © 2013 AutoNavi.All rights reserved.  
 * boss - 2013-12-2
 * 
 * 相关描述： 
 * 
 */
package org.shine.common.bean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.shine.common.annotation.PushParamName;

/**
 * 相关描述：
 *
 * 文件名：BasicDto.java
 * 作者： FuZhaohui
 * 完成时间：2013-12-2 上午11:45:37 
 * 维护人员：AutoNavi  
 * 维护时间：2013-12-2 上午11:45:37 
 * 维护原因：  
 * 当前版本： v1.0 
 *
 */
public abstract class PushBasicDTO  {
	
	private static Logger logger = Logger.getLogger(PushBasicDTO.class);
	
	public  Map<String, Object> toMap() {
		Map<String, Object> objectMap = new HashMap<String, Object>();
		try {
			Method[] methods = this.getClass().getDeclaredMethods();  
	        for (Method method : methods) {  
	            boolean hasAnnotation = method.isAnnotationPresent(PushParamName.class);  
	            if (hasAnnotation) {  
	            	PushParamName annotation = method.getAnnotation(PushParamName.class);  
	                Object value = method.invoke(this);
	                objectMap.put(annotation.value(), value);
	            }  
	        } 
		} catch (Exception e) {
			logger.error("对象转Map异常");
		} 
        return objectMap;
	}
}
