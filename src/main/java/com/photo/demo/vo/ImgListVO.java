package com.photo.demo.vo;

public class ImgListVO {
	private int boardNo;
	private String galleryId;
	private String boardTitle;
	private String boardContent;
	private String boardUploadDate;
	private int imgDataNo; //게시판 번호
	private String uploadPath; // 경로
	private String imguuid; //uuid
	private String fileName;
	
	//시작
	private int startRowNum;
	//끝
	private int endRowNum;
	//가져갈 게시물
	private int rowCount;
	
	
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getGalleryId() {
		return galleryId;
	}
	public void setGalleryId(String galleryId) {
		this.galleryId = galleryId;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardUploadDate() {
		return boardUploadDate;
	}
	public void setBoardUploadDate(String boardUploadDate) {
		this.boardUploadDate = boardUploadDate;
	}
	public int getImgDataNo() {
		return imgDataNo;
	}
	public void setImgDataNo(int imgDataNo) {
		this.imgDataNo = imgDataNo;
	}
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
	public int getStartRowNum() {
		return startRowNum;
	}
	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}
	public int getEndRowNum() {
		return endRowNum;
	}
	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	@Override
	public String toString() {
		return "ImgListVO [boardNo=" + boardNo + ", galleryId=" + galleryId + ", boardTitle=" + boardTitle
				+ ", boardContent=" + boardContent + ", boardUploadDate=" + boardUploadDate + ", imgDataNo=" + imgDataNo
				+ ", uploadPath=" + uploadPath + ", imguuid=" + imguuid + ", fileName=" + fileName + ", startRowNum="
				+ startRowNum + ", endRowNum=" + endRowNum + ", rowCount=" + rowCount + "]";
	}


	
}
