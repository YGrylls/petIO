package com.petio.petIO.beans;

public class NewCommentInfo {
	private int aID;
	private String toUsername;
	private String commentText;

	public int getaID() {
		return aID;
	}

	public void setaID(int aID) {
		this.aID = aID;
	}

	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	@Override
	public String toString() {
		return "NewCommentInfo [aID=" + aID + ", toUsername=" + toUsername + ", commentText=" + commentText + "]";
	}

	public boolean validate() {
		return (commentText.length() <= 140);
	}

	public NewCommentInfo(int aID, String toUsername, String commentText) {
		super();
		this.aID = aID;
		this.toUsername = toUsername;
		this.commentText = commentText;
	}

}
