package com.itbank.model;

import java.sql.Date;

public class InquiryDTO {

	private int idx;
	private String title;
	private String content;
	private String sendId;
	private Date wdate;
	private String response;
	private String answer;
	private boolean displayBlock;

	public boolean isDisplayBlock() {
		return displayBlock;
	}

	public void setDisplayBlock(boolean displayBlock) {
		this.displayBlock = displayBlock;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
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

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	public Date getWdate() {
		return wdate;
	}

	public void setWdate(Date wdate) {
		this.wdate = wdate;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
