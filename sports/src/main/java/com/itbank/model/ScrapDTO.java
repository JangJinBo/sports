package com.itbank.model;

public class ScrapDTO {

	private int idx;
	private int member_idx;
	private int space_idx;

	private String region;
	private String spaceName;
	private String spaceCategory;

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

	public int getSpace_idx() {
		return space_idx;
	}

	public void setSpace_idx(int space_idx) {
		this.space_idx = space_idx;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getSpaceCategory() {
		return spaceCategory;
	}

	public void setSpaceCategory(String spaceCategory) {
		this.spaceCategory = spaceCategory;
	}

}
