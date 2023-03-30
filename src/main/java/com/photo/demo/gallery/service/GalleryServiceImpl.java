package com.photo.demo.gallery.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photo.demo.gallery.dao.GalleryDAO;
import com.photo.demo.vo.GalleryVO;
import com.photo.demo.vo.ImgListVO;
import com.photo.demo.vo.UploadImageVO;

@Service
public class GalleryServiceImpl implements GalleryService{
	
	@Autowired
	private GalleryDAO galleryDao;
	
	//갤러리 생성
	@Override
	public void galleryPOST(GalleryVO galleryvo) {
		galleryDao.galleryPOST(galleryvo);
	}

	@Override
	public void saveImgFile(UploadImageVO attach) {
		galleryDao.saveImgFile(attach);
	}

	@Override
	public Object galleryDetail(int boardNo) {
		return galleryDao.galleryDetail(boardNo);
	}

	@Override
	public List<UploadImageVO> galleryDetailImg(UploadImageVO uploadImagevo) {
		return galleryDao.galleryDetailImg(uploadImagevo);
	}

	@Override
	public List<UploadImageVO> thumbnailList(List<Map<String, Object>> list) {
		return galleryDao.thumbnailList(list);
	}

	@Override
	public List<ImgListVO> galleryList(String galleryId, int startRowNum, int rowCount) {
		return galleryDao.galleryList(galleryId, startRowNum, rowCount);
	}

	@Override
	public void galleryTextDelete(int boardNo) {
		galleryDao.galleryTextDelete(boardNo);
	}

	@Override
	public void galleryImgDelete(int boardNo) {
		galleryDao.galleryImgDelete(boardNo);
	}

	@Override
	public List<UploadImageVO> ImgPathDataDelete(int boardNo) {
		return galleryDao.ImgPathDataDelete(boardNo);
	}

//	@Override
//	public void galleryImgUpdate(int boardNo) {
//		galleryDao.galleryImgUpdate(boardNo);
//		
//	}

	@Override
	public Object galleryTextUpdate(int boardNo) {
		return galleryDao.galleryTextUpdate(boardNo);
		
	}

	@Override
	public List<UploadImageVO> ImgPathDataSelect(Map<String, Object> uploadImgData) {
		return galleryDao.ImgPathDataSelect(uploadImgData);
	}

	@Override
	public void galleryimgUpdate(Map<String, Object> uploadImgData) {
		galleryDao.galleryimgUpdate(uploadImgData);
	}

	@Override
	public void galleryTextUpdatePOST(GalleryVO galleryvo) {
		galleryDao.galleryTextUpdatePOST(galleryvo);
	}

	@Override
	public int boardCount(String galleryId) {
		return galleryDao.boardCount(galleryId);
	}


	




}
