package com.zhku.bean;

import java.util.List;

public class Pagination<T> {
	private int pageSize;
	private int currentPage;
	private int totalSize;
	private List<T> pageDataList;
	
	public Pagination(int pageSize, int currentPage,int totalSize) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalSize=totalSize;
	}

	public Pagination() {
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageCount() {
		return (totalSize-1)/pageSize+1;
	}
	public int getBegin(){
		return (currentPage-1)*pageSize;
	}
	public int getEnd(){
		return currentPage*pageSize-1;
	}
	public List<T> getPageDataList() {
		return pageDataList;
	}
	public void setPageDataList(List<T> pageDataList) {
		this.pageDataList = pageDataList;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
}
