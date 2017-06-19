package org.asechs.wheelwego.model.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BoardVO {
	private String no;
	private String id;
	private String title;
	private String content;
	private String timePosted;
	private int hits;
	private List<MultipartFile> file;
	private FileVO fileVO;
	public BoardVO() {
		super();
	}
	public BoardVO(String no, String id, String title, String content, String timePosted, int hits,
			List<MultipartFile> file, FileVO fileVO) {
		super();
		this.no = no;
		this.id = id;
		this.title = title;
		this.content = content;
		this.timePosted = timePosted;
		this.hits = hits;
		this.file = file;
		this.fileVO = fileVO;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTimePosted() {
		return timePosted;
	}
	public void setTimePosted(String timePosted) {
		this.timePosted = timePosted;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public List<MultipartFile> getFile() {
		return file;
	}
	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}
	public FileVO getFileVO() {
		return fileVO;
	}
	public void setFileVO(FileVO fileVO) {
		this.fileVO = fileVO;
	}
	@Override
	public String toString() {
		return "BoardVO [no=" + no + ", id=" + id + ", title=" + title + ", content=" + content + ", timePosted="
				+ timePosted + ", hits=" + hits + ", file=" + file + ", fileVO=" + fileVO + "]";
	}
	
}
