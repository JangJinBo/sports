package com.itbank.model;

public class ReserveDTO {
	private int idx;
	private int space_idx;
	private int member_idx;
	private String pay_payIdx;
	private String startTime;
	private String endTime;
	private String reserverName;
	private String reserverNumber;
	private int status;

	private String spaceName;

	private String payIdx;
	private String reserveNum;
	private String reservePrice;

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

	public int getMember_idx() {
		return member_idx;
	}

	public void setMember_idx(int member_idx) {
		this.member_idx = member_idx;
	}

	public String getPay_payIdx() {
		return pay_payIdx;
	}

	public void setPay_payIdx(String pay_payIdx) {
		this.pay_payIdx = pay_payIdx;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getReserverName() {
		return reserverName;
	}

	public void setReserverName(String reserverName) {
		this.reserverName = reserverName;
	}

	public String getReserverNumber() {
		return reserverNumber;
	}

	public void setReserverNumber(String reserverNumber) {
		this.reserverNumber = reserverNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

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
