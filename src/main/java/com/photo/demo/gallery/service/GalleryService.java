package com.photo.demo.gallery.service;


import java.util.List;
import java.util.Map;

import com.photo.demo.vo.GalleryVO;
import com.photo.demo.vo.ImgListVO;
import com.photo.demo.vo.UploadImageVO;

public interface GalleryService {
	
	//갤러리 생성
	public void galleryPOST(GalleryVO galleryvo);
	
	//이미지업로드
	void saveImgFile(UploadImageVO attach);
	
	public Object galleryDetail(int boardNo);

	public List<UploadImageVO> galleryDetailImg(UploadImageVO uploadImagevo);
	
	public List<UploadImageVO> thumbnailList(List<Map<String, Object>> list);

	public List<ImgListVO> galleryList(String galleryId, int startRowNum, int rowCount);

	public void galleryTextDelete(int boardNo);

	public void galleryImgDelete(int boardNo);

	public List<UploadImageVO> ImgPathDataDelete(int boardNo);

//	public void galleryImgUpdate(int boardNo);

	public Object galleryTextUpdate(int boardNo);

	public List<UploadImageVO> ImgPathDataSelect(Map<String, Object> uploadImgData);

	public void galleryimgUpdate(Map<String, Object> uploadImgData);

	public void galleryTextUpdatePOST(GalleryVO galleryvo);

	public int boardCount(String galleryId);



}
