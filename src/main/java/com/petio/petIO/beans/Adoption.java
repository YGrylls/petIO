package com.petio.petIO.beans;

import java.sql.Date;
import java.util.List;

public class Adoption {
	private int aID;
	private int editor;
	private String aTitle;   //标题
	private String aType;    //类型
	private String location; //地点
	private String aText;    //简介
	private Date publishDate;
	private int aRead;
	private Date startDate;
	private int aState;   //状态
	private String aSex;  //性别
	private int aMoney;   //金额
	private String aDetailInfo;  //详细信息
	private boolean isFree;  //是否免费
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
	public String getaSex() {
		return aSex;
	}
	public void setaSex(String aSex) {
		this.aSex = aSex;
	}
	public int getaMoney() {
		return aMoney;
	}
	public void setaMoney(int aMoney) {
		this.aMoney = aMoney;
	}
	public String getaDetailInfo() {
		return aDetailInfo;
	}
	public void setaDetailInfo(String aDetailInfo) {
		this.aDetailInfo = aDetailInfo;
	}
	public boolean isFree() {
		return isFree;
	}
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	@Override
	public String toString() {
		return "Adoption [aID=" + aID + ", editor=" + editor + ", aTitle=" + aTitle + ", aType=" + aType + ", location="
				+ location + ", aText=" + aText + ", publishDate=" + publishDate + ", aRead=" + aRead + ", startDate="
				+ startDate + ", aState=" + aState + ", aSex=" + aSex + ", aMoney=" + aMoney + ", aDetailInfo="
				+ aDetailInfo + ", isFree=" + isFree + ", imgPaths=" + imgPaths + "]";
	}

	
}
