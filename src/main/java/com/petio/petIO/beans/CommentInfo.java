package com.petio.petIO.beans;

import java.sql.Date;

public class CommentInfo {
	private int cID;
	private String commentText;
	private String fromUser;
	private String toUser;
	private Date pubtime;

	public int getcID() {
		return cID;
	}

	public void setcID(int cID) {
		this.cID = cID;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public Date getPubtime() {
		return pubtime;
	}

	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}

	public CommentInfo(int cID, String commentText, String fromUser, String toUser, Date pubtime) {
		super();
		this.cID = cID;
		this.commentText = commentText;
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.pubtime = pubtime;
	}

	@Override
	public String toString() {
		return "CommentInfo [cID=" + cID + ", commentText=" + commentText + ", fromUser=" + fromUser + ", toUser="
				+ toUser + ", pubtime=" + pubtime + "]";
	}

}
