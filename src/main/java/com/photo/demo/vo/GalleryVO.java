package com.photo.demo.vo; 

import java.util.Date;
import java.util.List;

public class GalleryVO {
	private int boardNo;
	private String galleryId;
	private String boardTitle;
	private String boardContent;
	private Date boardUploadDate;
	private Date boardUpdate;
	private List<UploadImageVO> imageList;
	
	//시작
	private int stratRowNum;
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
	public Date getBoardUploadDate() {
		return boardUploadDate;
	}
	public void setBoardUploadDate(Date boardUploadDate) {
		this.boardUploadDate = boardUploadDate;
	}
	public Date getBoardUpdate() {
		return boardUpdate;
	}
	public void setBoardUpdate(Date boardUpdate) {
		this.boardUpdate = boardUpdate;
	}
	public List<UploadImageVO> getImageList() {
		return imageList;
	}
	public void setImageList(List<UploadImageVO> imageList) {
		this.imageList = imageList;
	}
	public int getStratRowNum() {
		return stratRowNum;
	}
	public void setStratRowNum(int stratRowNum) {
		this.stratRowNum = stratRowNum;
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
		return "GalleryVO [boardNo=" + boardNo + ", galleryId=" + galleryId + ", boardTitle=" + boardTitle
				+ ", boardContent=" + boardContent + ", boardUploadDate=" + boardUploadDate + ", boardUpdate="
				+ boardUpdate + ", imageList=" + imageList + ", stratRowNum=" + stratRowNum + ", endRowNum=" + endRowNum
				+ ", rowCount=" + rowCount + "]";
	}


	
	
	
	
}
