package com.petio.petIO.beans;

import java.sql.Date;
import java.sql.Timestamp;

public class Fosterage {
	private int fID;
	private int editor;
	private String fTitle;
	private String fType;
	private String location;
	private String fText;
	private Timestamp publishDate;
	private int fRead;
	private Date startDate;
	private int fState;
	public int getfID() {
		return fID;
	}
	public void setfID(int fID) {
		this.fID = fID;
	}
	public int getEditor() {
		return editor;
	}
	public void setEditor(int editor) {
		this.editor = editor;
	}
	public String getfTitle() {
		return fTitle;
	}
	public void setfTitle(String fTitle) {
		this.fTitle = fTitle;
	}
	public String getfType() {
		return fType;
	}
	public void setfType(String fType) {
		this.fType = fType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getfText() {
		return fText;
	}
	public void setfText(String fText) {
		this.fText = fText;
	}

	public Timestamp getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}
	public int getfRead() {
		return fRead;
	}
	public void setfRead(int fRead) {
		this.fRead = fRead;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getfState() {
		return fState;
	}
	public void setfState(int fState) {
		this.fState = fState;
	}
	@Override
	public String toString() {
		return "Fosterage [fID=" + fID + ", editor=" + editor + ", fTitle=" + fTitle + ", fType=" + fType
				+ ", location=" + location + ", fText=" + fText + ", publishDate=" + publishDate + ", fRead=" + fRead
				+ ", startDate=" + startDate + ", fState=" + fState + "]";
	}
	
}
