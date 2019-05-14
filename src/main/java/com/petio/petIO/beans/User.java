package com.petio.petIO.beans;

public class User {
	String username;
	String password;
	float userScore;
	String userMail;

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public User(String username, String password, String userMail, float userScore) {
		super();
		this.username = username;
		this.password = password;
		this.userScore = userScore;
		this.userMail = userMail;
	}

	public float getUserScore() {
		return userScore;
	}

	public void setUserScore(float userScore) {
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

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", userScore=" + userScore + ", userMail="
				+ userMail + "]";
	}

}
