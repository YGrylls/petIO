package com.petio.petIO.beans;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Adoption {
	private int aID;
	private int editor;
	private String aTitle;
	private String aType;
	private String location;
	private String aText;
	private Timestamp publishDate;
	private int aRead;
	private Date startDate;
	private int aState;
	private List<String>imgPaths;
	public int getaID() {
		return aID;
	}
	public void setaID(int aID) {
		this.aID = aID;
	}
	public int getEditor() {
		return editor;
	}
	public void setEditor(int editor) {
		this.editor = editor;
	}
	public String getaTitle() {
		return aTitle;
	}
	public void setaTitle(String aTitle) {
		this.aTitle = aTitle;
	}
	public String getaType() {
		return aType;
	}
	public void setaType(String aType) {
		this.aType = aType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getaText() {
		return aText;
	}
	public void setaText(String aText) {
		this.aText = aText;
	}
	public Timestamp getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}
	public int getaRead() {
		return aRead;
	}
	public void setaRead(int aRead) {
		this.aRead = aRead;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getaState() {
		return aState;
	}
	public void setaState(int aState) {
		this.aState = aState;
	}
	public List<String> getImgPaths() {
		return imgPaths;
	}
	public void setImgPaths(List<String> imgPaths) {
		this.imgPaths = imgPaths;
	}

	
}
