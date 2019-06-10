package com.petio.petIO.beans;

import java.sql.Time;

public class NewInfo {

	private int aID;
	
	
	
	private String aTitle;
	
	private String username;
	
	private String content;
	
	private Time time;
	private int uID;
	public int getaID() {
		return aID;
	}

	public int getuID() {
		return uID;
	}

	public void setuID(int uID) {
		this.uID = uID;
	}

	public void setaID(int aID) {
		this.aID = aID;
	}

	public String getaTitle() {
		return aTitle;
	}

	public void setaTitle(String aTitle) {
		this.aTitle = aTitle;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}
	public NewInfo() {}

	public NewInfo(int aID, String aTitle, String username, String content, Time time) {
		super();
		this.aID = aID;
		this.aTitle = aTitle;
		this.username = username;
		this.content = content;
		this.time = time;
	}

	public NewInfo(int aID, String aTitle, String username, Time time) {
		super();
		this.aID = aID;
		this.aTitle = aTitle;
		this.username = username;
		this.time = time;
	}
	
	
}
