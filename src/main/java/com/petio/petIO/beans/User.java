package com.petio.petIO.beans;

public class User {
	String username;
	String password;
	String userTel;
	float userScore;

	public User(String username, String password, String userTel, float userScore) {
		super();
		this.username = username;
		this.password = password;
		this.userTel = userTel;
		this.userScore = userScore;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", userTel=" + userTel + "]";
	}
}
