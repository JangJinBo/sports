package com.itbank.model;

public class PayDTO {
	private String payIdx;
	private String reserveNum;
	private String reservePrice;

	public String getPayIdx() {
		return payIdx;
	}

	public void setPayIdx(String payIdx) {
		this.payIdx = payIdx;
	}

	public String getReserveNum() {
		return reserveNum;
	}

	public void setReserveNum(String reserveNum) {
		this.reserveNum = reserveNum;
	}

	public String getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(String reservePrice) {
		this.reservePrice = reservePrice;
	}

}
