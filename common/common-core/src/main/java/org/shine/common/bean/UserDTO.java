/**
 * Copyright (c) 2008-2013 	Copyright © 2013 AutoNavi.All rights reserved.  
 * boss - 2013-12-2
 * 
 * 相关描述： 
 * 
 */
package org.shine.common.bean;

import org.shine.common.annotation.PushParamName;

/**
 * 相关描述：
 *
 * 文件名：GetwayDto.java
 * 作者： FuZhaohui
 * 完成时间：2013-12-2 上午11:18:45 
 * 维护人员：AutoNavi  
 * 维护时间：2013-12-2 上午11:18:45 
 * 维护原因：  
 * 当前版本： v1.0 
 *
 */
public class UserDTO extends PushBasicDTO {
	
	private String username;
	private String Password;
	
	@PushParamName(value = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@PushParamName(value = "password")
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	/* 
     * 解析注解，将Test_1类 所有被注解方法 的信息打印出来 
     */  
    public static void main(String[] args) {  
    	UserDTO user = new UserDTO();
    	user.setUsername("fadfdaa");
    	user.setPassword("123456");
		System.out.println(user.toMap());
    }  
	

}
