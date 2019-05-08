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
	private Date expireDate;
	private int aState;   //状态
	private String aSex;  //性别
	private int aMoney;   //金额
	private String aDetailInfo;  //详细信息
	private boolean isFree = false;  //是否免费
	private List<String>imgPaths;
	private String communicationType;
	private String communication;
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

	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
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
	public String getaSex() {
		return aSex;
	}
	public void setaSex(String aSex) {
		this.aSex = aSex;
	}
	public Adoption(int editor, String aTitle, String aType, String location, String aText,
			String aSex, int aMoney, String aDetailInfo, String communicationType,
	 String communication) {
		super();
		if (aMoney == 0) {
			isFree = true;
		}
		this.editor = editor;
		this.aTitle = aTitle;
		this.aType = aType;
		this.location = location;
		this.aText = aText;
		this.aRead = aRead;
		this.aState = aState;
		this.aSex = aSex;
		this.aMoney = aMoney;
		this.aDetailInfo = aDetailInfo;
		this.isFree = isFree;
		this.communicationType = communicationType;
		this.communication = communication;
	}
	
	public Adoption() {
		super();
	}
	public String getCommunicationType() {
		return communicationType;
	}
	public void setCommunicationType(String communicationType) {
		this.communicationType = communicationType;
	}
	public String getCommunication() {
		return communication;
	}
	public void setCommunication(String communication) {
		this.communication = communication;
	}
	
}
