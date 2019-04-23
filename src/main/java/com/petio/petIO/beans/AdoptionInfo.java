package com.petio.petIO.beans;

import java.sql.Date;
import java.util.List;

public class AdoptionInfo {
	private int aID;
	private String editor;
	private String aTitle;
	private String aType;
	private String location;
	private String aText;
	private Date publishDate;
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
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
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
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
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
	public AdoptionInfo(int aID, String editor, String aTitle, String aType, String location, String aText,
			Date publishDate, int aRead, Date startDate, int aState, List<String> imgPaths) {
		super();
		this.aID = aID;
		this.editor = editor;
		this.aTitle = aTitle;
		this.aType = aType;
		this.location = location;
		this.aText = aText;
		this.publishDate = publishDate;
		this.aRead = aRead;
		this.startDate = startDate;
		this.aState = aState;
		this.imgPaths = imgPaths;
	}
}
