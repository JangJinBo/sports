package com.itbank.model;

import java.sql.Date;

//	IDX       NOT NULL NUMBER         
//	SPACE_IDX NOT NULL NUMBER         
//	TITLE     NOT NULL VARCHAR2(200)  
//	CONTENT   NOT NULL VARCHAR2(3000)

public class FacilityDTO {

	private int idx;
	private int space_idx;
	private String title;
	private String content;
	
	private Date wdate;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getSpace_idx() {
		return space_idx;
	}
	public void setSpace_idx(int space_idx) {
		this.space_idx = space_idx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getWdate() {
		return wdate;
	}
	public void setWdate(Date wdate) {
		this.wdate = wdate;
	}
	
}
