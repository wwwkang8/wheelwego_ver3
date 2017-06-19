package org.asechs.wheelwego.model.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
//멀티파일다운로드를 위한 VO
public class UploadTestVO {
	private String userInfo;
	private List<MultipartFile> file;
	public UploadTestVO() {
		super();
	}
	public UploadTestVO(String userInfo, List<MultipartFile> file) {
		super();
		this.userInfo = userInfo;
		this.file = file;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	public List<MultipartFile> getFile() {
		return file;
	}
	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "UploadTestVO [userInfo=" + userInfo + ", file=" + file + "]";
	}
	
}
