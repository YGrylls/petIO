package com.petio.petIO.beans;

public class ConnectInfo {
	private String username;
	private String userTel;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	@Override
	public String toString() {
		return "ConnectInfo [username=" + username + ", userTel=" + userTel + "]";
	}
}
