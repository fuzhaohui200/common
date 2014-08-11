/**
 * Copyright (c) 2008-2011 Guangzhou LianYi Information Technology Co,.Ltd. All rights reserved.  
 * lyasp_v3.0 2012-9-14
 *
 * 相关描述： 
 *
 */
package org.shine.hibernate.test.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * 相关描述：
 * <p/>
 * 文件名：Student.java
 * 作者： ces
 * 完成时间：2012-9-14 上午11:40:20
 * 维护人员：ces
 * 维护时间：2012-9-14 上午11:40:20
 * 维护原因：
 * 当前版本： v3.0
 */
public class Student {

    private int id;
    private String name;
    private Set<Teacher> teachers = new HashSet<Teacher>();
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
}
