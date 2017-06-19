package org.asechs.wheelwego.model.vo;

public class CommentVO {
	private int commentNo;
	private String id;
	private int contentNo;
	private String comment;
	private String timePosted;
	private int parentNo;
	public CommentVO() {
		super(); 
	}
	public CommentVO(int commentNo, String id, int contentNo, String comment, String timePosted, int parentNo) {
		super();
		this.commentNo = commentNo;
		this.id = id;
		this.contentNo = contentNo;
		this.comment = comment;
		this.timePosted = timePosted;
		this.parentNo = parentNo;
	}
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getContentNo() {
		return contentNo;
	}
	public void setContentNo(int contentNo) {
		this.contentNo = contentNo;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTimePosted() {
		return timePosted;
	}
	public void setTimePosted(String timePosted) {
		this.timePosted = timePosted;
	}
	public int getParentNo() {
		return parentNo;
	}
	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
	}
	@Override
	public String toString() {
		return "CommentVO [commentNo=" + commentNo + ", id=" + id + ", contentNo=" + contentNo + ", comment=" + comment
				+ ", timePosted=" + timePosted + ", parentNo=" + parentNo + "]";
	}
	
}
