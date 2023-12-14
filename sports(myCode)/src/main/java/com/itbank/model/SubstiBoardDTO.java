package com.itbank.model;

import java.sql.Date;

public class SubstiBoardDTO {
	private int idx;
	private int juBoard_idx;
	private String joinPrice;
	private String place;
	private Date schedule;

	private int board_idx;
	private String title;
	private String gender;
	private String age;
	private String content;
	private String category;
	private String mainarea;

	private String boardType;
	private Date wdate;
	private String username;
	private String userid;
	private int member_idx;

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getJuBoard_idx() {
		return juBoard_idx;
	}

	public void setJuBoard_idx(int juBoard_idx) {
		this.juBoard_idx = juBoard_idx;
	}

	public String getJoinPrice() {
		return joinPrice;
	}

	public void setJoinPrice(String joinPrice) {
		this.joinPrice = joinPrice;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getSchedule() {
		return schedule;
	}

	public void setSchedule(Date schedule) {
		this.schedule = schedule;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMainarea() {
		return mainarea;
	}

	public void setMainarea(String mainarea) {
		this.mainarea = mainarea;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getMember_idx() {
		return member_idx;
	}

	public void setMember_idx(int member_idx) {
		this.member_idx = member_idx;
	}

}