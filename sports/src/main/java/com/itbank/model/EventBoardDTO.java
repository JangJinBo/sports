package com.itbank.model;

import java.sql.Date;

//	idx 	number		default board_seq.nextval primary  key,
//	member_idx	number		NOT NULL,
//	boardType	varchar2(100)		not NULL,

public class EventBoardDTO {

	private int idx;
	private int board_idx;
	private String title;
	private String content;
	
	private String member_idx;
	private String boardType;
	private String username;
	private String userid;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	private Date wdate;
		
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
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
	public String getMember_idx() {
		return member_idx;
	}
	public void setMember_idx(String member_idx) {
		this.member_idx = member_idx;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getWdate() {
		return wdate;
	}
	public void setWdate(Date wdate) {
		this.wdate = wdate;
	}
	
}
