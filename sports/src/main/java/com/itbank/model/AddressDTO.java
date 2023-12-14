package com.itbank.model;

//	idx       number      default address_seq.nextval primary  key,
//	member_idx   number      NOT NULL,
//	location   varchar2(200)      not NULL,
//	addX   number      not NULL,
//	addY   number      not NULL,
//	addressType number default 1,

public class AddressDTO {

	private int idx;
	private int member_idx;
	private String location;
	private double addX;
	private double addY;
	private int addressType;
	
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getAddX() {
		return addX;
	}
	public void setAddX(double addX) {
		this.addX = addX;
	}
	public double getAddY() {
		return addY;
	}
	public void setAddY(double addY) {
		this.addY = addY;
	}
	public int getAddressType() {
		return addressType;
	}
	public void setAddressType(int addressType) {
		this.addressType = addressType;
	}
	
}
