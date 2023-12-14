package com.itbank.model;

import java.sql.Date;

//	idx 	number		default board_seq.nextval primary  key,
//	member_idx	number		NOT NULL,
//	boardType	varchar2(100)		not NULL,
//	wdate   	date		default sysdate,

public class BoardDTO {

	private int idx;
	private int member_idx;
	private String boardType;
	private Date wdate;

	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getMember_idx() {
		return member_idx;
	}
	public void setMember_idx(int member_idx) {
		this.member_idx = member_idx;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public Date getWdate() {
		return wdate;
	}
	public void setWdate(Date wdate) {
		this.wdate = wdate;
	}
	
}
