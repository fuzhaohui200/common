package org.shine.common.vo;

import java.util.ArrayList;
import java.util.List;

public class ResultDTO<L> {
	
	/**
	 * 每页记录数
	 */
	private int pageSize;
	
	/**
	 * 当前页
	 */
	private int curPage;
	
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 总条数
	 */
	private int totle;
	/**
	 * 记录集合
	 */
	private List<L> results = new ArrayList<L>();

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public List<L> getResults() {
		return results;
	}

	public void add(L obj){
		results.add(obj);
	}
	
	public void addAll(List<L> list){
		results.addAll(list);
	}

	public int getTotle() {
		return totle;
	}

	public void setTotle(int totle) {
		this.totle = totle;
	}
	
	
}
