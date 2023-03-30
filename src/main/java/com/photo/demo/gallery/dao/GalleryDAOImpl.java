package com.photo.demo.gallery.dao;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.photo.demo.vo.GalleryVO;
import com.photo.demo.vo.ImgListVO;
import com.photo.demo.vo.UploadImageVO;

@Repository
public class GalleryDAOImpl implements GalleryDAO{
	
	@Autowired 
	private SqlSession sql;
	
	private static String namespace="gallery";
	
	//갤러리생성
	@Override
	public void galleryPOST(GalleryVO galleryvo) {
		sql.insert(namespace+".galleryPOST", galleryvo);
	}

	@Override
	public void saveImgFile(UploadImageVO attach) {
		sql.insert(namespace+".saveImgFile", attach);
	}

	@Override
	public Object galleryDetail(int boardNo) {
		return sql.selectOne(namespace+".galleryDetail",boardNo);
	}

	@Override
	public List<UploadImageVO> galleryDetailImg(UploadImageVO uploadImagevo) {
		return sql.selectList(namespace+".galleryDetailImg", uploadImagevo);
	}

	@Override
	public List<UploadImageVO> thumbnailList(List<Map<String, Object>> list) {	
		return sql.selectList(namespace+".thumbnailList", list);
	}

	@Override
	public List<ImgListVO> galleryList(String galleryId, int startRowNum, int rowCount) {
		HashMap<String, Object>rtnMap = new HashMap<String, Object>();
		
		rtnMap.put("galleryId", galleryId);
		rtnMap.put("startRowNum", startRowNum);
		rtnMap.put("rowCount", rowCount);
		
		return sql.selectList(namespace+".galleryList", rtnMap);
	}

	@Override
	public void galleryTextDelete(int boardNo) {
		sql.delete(namespace+".galleryTextDelete", boardNo);
	}

	@Override
	public void galleryImgDelete(int boardNo) {
		sql.delete(namespace+".galleryImgDelete", boardNo);
		
	}

	@Override
	public List<UploadImageVO> ImgPathDataDelete(int boardNo) {
		return sql.selectList(namespace+".ImgPathDataDelete", boardNo);
		
	}

//	@Override
//	public void galleryImgUpdate(int boardNo) {
//		sql.selectOne(namespace+".galleryImgUpdate", boardNo);
//	}

	@Override
	public Object galleryTextUpdate(int boardNo) {
		return sql.selectOne(namespace+".galleryTextUpdateGET", boardNo);

	}

	@Override
	public List<UploadImageVO> ImgPathDataSelect(Map<String, Object> uploadImgData) {
		return sql.selectList(namespace+".ImgPathDataSelect", uploadImgData);
	}

	@Override
	public void galleryimgUpdate(Map<String, Object> uploadImgData) {
		sql.delete(namespace+".galleryimgUpdate", uploadImgData);
	}

	@Override
	public void galleryTextUpdatePOST(GalleryVO galleryvo) {
		sql.update(namespace+".galleryTextUpdatePOST", galleryvo);
	}

	@Override
	public int boardCount(String galleryId) {
		return sql.selectOne(namespace+".boardCount", galleryId);
	}







}
