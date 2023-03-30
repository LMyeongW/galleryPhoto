package com.photo.demo.vo;

public class UploadImageVO {
	private String uploadPath; // 경로
	private String imguuid; //uuid
	private String fileName;
	private String galleryId; //계정아이디
	private int boardNo; //게시판 번호
	private int imgDataNo; //게시판 번호
	
	
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getImguuid() {
		return imguuid;
	}
	public void setImguuid(String imguuid) {
		this.imguuid = imguuid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getGalleryId() {
		return galleryId;
	}
	public void setGalleryId(String galleryId) {
		this.galleryId = galleryId;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public int getImgDataNo() {
		return imgDataNo;
	}
	public void setImgDataNo(int imgDataNo) {
		this.imgDataNo = imgDataNo;
	}
	
	@Override
	public String toString() {
		return "UploadImageVO [uploadPath=" + uploadPath + ", imguuid=" + imguuid + ", fileName=" + fileName
				+ ", galleryId=" + galleryId + ", boardNo=" + boardNo + ", imgDataNo=" + imgDataNo + "]";
	}
	
	
	
	
}
