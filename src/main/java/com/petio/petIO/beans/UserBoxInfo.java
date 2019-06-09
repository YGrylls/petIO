package com.petio.petIO.beans;

public class UserBoxInfo {
	private int uid;
	private String username;
	private int adoptionNum;
	private int completeAdoptionNum;
	private int completeApplyNum;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAdoptionNum() {
		return adoptionNum;
	}

	public void setAdoptionNum(int adoptionNum) {
		this.adoptionNum = adoptionNum;
	}

	public int getCompleteAdoptionNum() {
		return completeAdoptionNum;
	}

	public void setCompleteAdoptionNum(int completeAdoptionNum) {
		this.completeAdoptionNum = completeAdoptionNum;
	}

	public int getCompleteApplyNum() {
		return completeApplyNum;
	}

	public void setCompleteApplyNum(int completeApplyNum) {
		this.completeApplyNum = completeApplyNum;
	}

	public UserBoxInfo(int uid, String username, int adoptionNum, int completeAdoptionNum, int completeApplyNum) {
		super();
		this.uid = uid;
		this.username = username;
		this.adoptionNum = adoptionNum;
		this.completeAdoptionNum = completeAdoptionNum;
		this.completeApplyNum = completeApplyNum;
	}

	@Override
	public String toString() {
		return "UserBoxInfo [uid=" + uid + ", username=" + username + ", adoptionNum=" + adoptionNum
				+ ", completeAdoptionNum=" + completeAdoptionNum + ", completeApplyNum=" + completeApplyNum + "]";
	}

}
