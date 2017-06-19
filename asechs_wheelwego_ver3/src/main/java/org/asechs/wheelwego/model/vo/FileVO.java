package org.asechs.wheelwego.model.vo;

public class FileVO {
	private String no;
	private String filepath;
	private String beforefilepath;
	public FileVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FileVO(String no, String filepath, String beforefilepath) {
		super();
		this.no = no;
		this.filepath = filepath;
		this.beforefilepath = beforefilepath;
	}
	
	public FileVO(String no, String filepath) {
		super();
		this.no = no;
		this.filepath = filepath;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getBeforefilepath() {
		return beforefilepath;
	}
	public void setBeforefilepath(String beforefilepath) {
		this.beforefilepath = beforefilepath;
	}
	@Override
	public String toString() {
		return "FileVO [no=" + no + ", filepath=" + filepath + ", beforefilepath=" + beforefilepath + "]";
	}
	
}
