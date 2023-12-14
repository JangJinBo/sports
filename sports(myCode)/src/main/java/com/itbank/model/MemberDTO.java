package com.itbank.model;

public class MemberDTO {

	private int idx;
	private String userid;
	private String userpw;
	private String salt;
	private String nickname;
	private String username;
	private String pnum;
	private String email;
	private String memberType;
	private String block;

	private int addressIdx;
	private String addressLocation;
	private String addressAddX;
	private String addressAddY;

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpw() {
		return userpw;
	}

	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPnum() {
		return pnum;
	}

	public void setPnum(String pnum) {
		this.pnum = pnum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public int getAddressIdx() {
		return addressIdx;
	}

	public void setAddressIdx(int addressIdx) {
		this.addressIdx = addressIdx;
	}

	public String getAddressLocation() {
		return addressLocation;
	}

	public void setAddressLocation(String addressLocation) {
		this.addressLocation = addressLocation;
	}

	public String getAddressAddX() {
		return addressAddX;
	}

	public void setAddressAddX(String addressAddX) {
		this.addressAddX = addressAddX;
	}

	public String getAddressAddY() {
		return addressAddY;
	}

	public void setAddressAddY(String addressAddY) {
		this.addressAddY = addressAddY;
	}

}
