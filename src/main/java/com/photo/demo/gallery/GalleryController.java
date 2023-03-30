package com.photo.demo.gallery;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.photo.demo.gallery.service.GalleryService;
import com.photo.demo.vo.GalleryVO;
import com.photo.demo.vo.ImgListVO;
import com.photo.demo.vo.UploadImageVO;

@Controller
@RequestMapping("/myphoto")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@RequestMapping(value = "/galleryUpload", method = RequestMethod.GET)
	public ModelAndView galleryUpload() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/galleryPage/galleryUpload");
		
		return mav;
	}
	
	//이미지 업로드
	@RequestMapping(value = "/uploadAjaxAction", method = RequestMethod.POST)
	public ModelAndView uploadAjaxAction(@RequestPart(value = "boardInfo") Map<String, Object> param, MultipartFile[] uploadFile, HttpServletRequest request) {
		
		System.out.println(uploadFile);
		
		ModelAndView mav = new ModelAndView();
		
		//저장경로
		String path = request.getSession().getServletContext().getRealPath("resources/uploadFiles3/");
		System.out.println("path : " + path);
		String uploadFilePath = "\\uploadFiles3\\";
		

		File file = new File(path);
		
    	// 파일이 존재하지않을시 mkdirs(디렉토리)생성
		if(!file.exists()){
			file.mkdirs();
			System.out.println("파일 생성 완료" + file);
		}
		
		//이미지 정보 담는 객체
		List<UploadImageVO> list = new ArrayList();
		for(MultipartFile multipartFile : uploadFile) {
			logger.info("-----------------------------------------------");
			logger.info("파일 이름 : " + multipartFile.getOriginalFilename()); //오리지널 이름
			logger.info("파일 타입 : " + multipartFile.getContentType()); //파일타입
			logger.info("파일 크기 : " + multipartFile.getSize());	//크기
			
			//이미지 정보 객체
			UploadImageVO vo = new UploadImageVO();

			
			 //파일 이름 
			String uploadFileName = multipartFile.getOriginalFilename();
			vo.setFileName(uploadFileName);//setter로 파일명 담음
			vo.setUploadPath(uploadFilePath);//setter로 경로 담음
				
			//---------------------------------------vo에 저장
			 //uuid 적용 파일 이름 
			String uuid = UUID.randomUUID().toString();
			vo.setImguuid(uuid); //setter로 uuid파일명 담음
			
			//게시판 아이디, boardNo 값 받은거 vo에 넣기
			int boardNo =  (int) param.get("boardNo");
			String galleryId = (String)param.get("galleryId");
			vo.setBoardNo(boardNo);
			vo.setGalleryId(galleryId);
			//-------------------------------------------
			
			
			uploadFileName = uuid + "_" + uploadFileName;
			
			 //파일 위치, 파일 이름을 합친 File 객체 
			File saveFile = new File(file, uploadFileName);

			//경로 파일 저장 
			try {
				multipartFile.transferTo(saveFile);
				System.out.println("저장 : "+ saveFile);

				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//db 저장
			galleryService.saveImgFile(vo);
			
			list.add(vo);
			
			System.out.println("경로 : "+vo.getUploadPath());
			System.out.println("uuid : "+vo.getImguuid());
			System.out.println("이름 : "+vo.getFileName());
		}//for
		
		mav.addObject("data", list);
		mav.setViewName("jsonView");
		return mav;
	}
	
	//갤러리등록
	@RequestMapping(value = "/galleryPOST", method=RequestMethod.POST)
	public ModelAndView galleryPOST(@ModelAttribute("galleryvo")GalleryVO galleryvo) {
		ModelAndView mav = new ModelAndView();
		
		galleryService.galleryPOST(galleryvo);
		
		//selectKey에서 max key값 받아옴
		System.out.println("==========" + galleryvo);
				

		mav.addObject("galleryvo", galleryvo);
		mav.setViewName("jsonView");
		return mav;
	}
	

	//갤러리 상세페이지
	@RequestMapping(value ="/galleryDetail", method=RequestMethod.GET)
	public ModelAndView galleryDetail(@RequestParam("boardNo")int boardNo) {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("data", galleryService.galleryDetail(boardNo));
		System.out.println(boardNo);
		
		mav.setViewName("/galleryPage/galleryDetail");
		return mav;
	}
	
	//갤러리 삭제
	@RequestMapping(value = "/galleryimgDelete", method=RequestMethod.GET)
	public ModelAndView galleryDelete(int boardNo,  HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		System.out.println("받아온 정보1 : "+boardNo);
		
		String path = request.getSession().getServletContext().getRealPath("resources/uploadFiles3/");
		
		
		// 경로에 저장된 이미지 다중 삭제
		File file = null;
		
		//key값을 통해 DB에 저장된 해당 게시물의 이미지 저장경로를 select
		List<UploadImageVO>pathLit = galleryService.ImgPathDataDelete(boardNo);
		
		List<String> rtnMap = new ArrayList<String>();
		
		System.out.println("------------ : "+pathLit);
		
		//  for문을 통해 해당 경로를 모두 만듬
		for(int i = 0 ; i < pathLit.size(); i++) {
			rtnMap.add( path + pathLit.get(i).getImguuid()+"_"+pathLit.get(i).getFileName());
		}
		
		//for문을 통해 이미지들 모두 delete
		for(int i = 0 ; i < rtnMap.size(); i++) {
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@ : "+rtnMap.get(i));
			
			try {
				
				file = new File(rtnMap.get(i));
				
				file.delete();
				
				System.out.println(file);
				
				
			} catch(Exception e) {
				
				e.printStackTrace();

			}
		}
		
		//이미지삭제(게시판부터 삭제하면 이미지 삭제시 boardNo값을 못찾아 삭제가 되지않음)
		galleryService.galleryImgDelete(boardNo);
		
		mav.setViewName("jsonView");
		return mav;
	}
	
	@RequestMapping(value = "/galleryTextDelete", method=RequestMethod.GET)
	public ModelAndView galleryDelete(int boardNo) {
		ModelAndView mav = new ModelAndView();
		
		//게시판삭제
		galleryService.galleryTextDelete(boardNo);
		
		mav.setViewName("jsonView");
		return mav;
	}
	
	//수정페이지 출력
	@RequestMapping(value = "/galleryUpdate", method=RequestMethod.GET)
	public ModelAndView galleryUpdateGET(@RequestParam int boardNo) {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("data", galleryService.galleryTextUpdate(boardNo));
		
		mav.setViewName("/galleryPage/galleryUpdate");
		return mav;
	}
	
	//수정페이지 TEXT 수정
	@RequestMapping(value = "/galleryTextUpdate", method=RequestMethod.POST)
	public ModelAndView galleryTextUpdatePOST(@ModelAttribute("galleryvo")GalleryVO galleryvo) {
		ModelAndView mav = new ModelAndView();
		System.out.println("TEXT : "+galleryvo);
		
		galleryService.galleryTextUpdatePOST(galleryvo);
		
		mav.setViewName("jsonView");
		return mav;
	}
	
	//수정페이지 이미지 수정
	@RequestMapping(value = "/galleryImgUpdatePost", method=RequestMethod.POST)
	public ModelAndView galleryImgUpdatePOST(@RequestPart(value = "boardInfo") Map<String, Object> uploadImgData, MultipartFile[] uploadFile, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		System.out.println("uploadFile : " +uploadFile);
		System.out.println("uploadImgData : " +uploadImgData);

		
		//저장경로
		String path = request.getSession().getServletContext().getRealPath("resources/uploadFiles3/");
		String uploadFilePath = "\\uploadFiles3\\";
		
		File file = null;
		
		List<UploadImageVO>pathListSelect = galleryService.ImgPathDataSelect(uploadImgData);
		System.out.println("DB에 저장된 이미지 리스트 : "+pathListSelect);
		
		if(uploadFile != null) {
			List<String> rtnMap = new ArrayList<String>();
			
			for(int i = 0 ; i < pathListSelect.size(); i++) {
				rtnMap.add( path + pathListSelect.get(i).getImguuid()+"_"+pathListSelect.get(i).getFileName());
			}
			
			//for문을 통해 이미지들 모두 delete
			for(int i = 0 ; i < rtnMap.size(); i++) {
				System.out.println("삭제될 경로파일 : "+rtnMap.get(i));
				
				try {
					
					file = new File(rtnMap.get(i));
					
					file.delete();
					
					System.out.println(file);
					
					
				} catch(Exception e) {
					
					e.printStackTrace();

				}
			}
			
			galleryService.galleryimgUpdate(uploadImgData);
			
		} 
		
		File file1 = new File(path);
		
    	// 파일이 존재하지않을시 mkdirs(디렉토리)생성
		if(!file1.exists()){
			file1.mkdirs();
			System.out.println("파일 생성 완료" + file1);
		}
		
		//이미지 정보 담는 객체
		List<UploadImageVO> list = new ArrayList();
		for(MultipartFile multipartFile : uploadFile) {
			logger.info("-----------------------------------------------");
			logger.info("파일 이름 : " + multipartFile.getOriginalFilename()); //오리지널 이름
			logger.info("파일 타입 : " + multipartFile.getContentType()); //파일타입
			logger.info("파일 크기 : " + multipartFile.getSize());	//크기
			
			//이미지 정보 객체
			UploadImageVO vo = new UploadImageVO();

			
			 //파일 이름 
			String uploadFileName = multipartFile.getOriginalFilename();
			vo.setFileName(uploadFileName);//setter로 파일명 담음
			vo.setUploadPath(uploadFilePath);//setter로 경로 담음
			
			System.out.println("vo" + vo);
			
			
			//---------------------------------------vo에 저장
			 //uuid 적용 파일 이름 
			String uuid = UUID.randomUUID().toString();
			vo.setImguuid(uuid); //setter로 uuid파일명 담음
			
			//게시판 아이디, boardNo 값 받은거 vo에 넣기
			int boardNo =  (int)uploadImgData.get("boardNo");
			String galleryId = (String)uploadImgData.get("galleryId");
			
			vo.setBoardNo(boardNo);
			vo.setGalleryId(galleryId);
			//-------------------------------------------
			
			
			uploadFileName = uuid + "_" + uploadFileName;
			
			 //파일 위치, 파일 이름을 합친 File 객체 
			File saveFile = new File(file1, uploadFileName);

			//경로 파일 저장 
			try {
				multipartFile.transferTo(saveFile);
				System.out.println("저장 : "+ saveFile);

				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//db 저장
			galleryService.saveImgFile(vo);
			
			list.add(vo);
		}	
	
		mav.setViewName("jsonView");
		return mav;
	}
	
	//상세페이지 이미지출력
	@RequestMapping(value ="/galleryDetailImg", method=RequestMethod.GET)
	public ModelAndView galleryDetailImg(@ModelAttribute("uploadImagevo")UploadImageVO uploadImagevo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		System.out.println(uploadImagevo);//
		
		List<UploadImageVO> imgFiles = galleryService.galleryDetailImg(uploadImagevo);
		
		System.out.println(imgFiles);
		
		mav.addObject("rtnMap", imgFiles);
		mav.setViewName("jsonView");

		return mav;
	}
	
	//갤러리 리스트페이지 출력
	@RequestMapping(value = "/galleryListPage", method=RequestMethod.GET)
	public ModelAndView galleryListPage() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/index");
		return mav;
	}
	
	//갤러리 리스트
	@RequestMapping(value = "/galleryList", method=RequestMethod.GET)
	public ModelAndView galleryList(String galleryId, int pageNum , HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		System.out.println(pageNum);
		
		//한페이지에 표시될 게시물
		final int PAGE_ROW_COUNT =12;

		//보여줄 페이지의 시작 ROWNUM - 0 부터 시작
		int startRowNum = 0 + (pageNum - 1) * PAGE_ROW_COUNT;
		
		//보여줄 페이지의 끝 ROWNUM 
		int endRowNum = pageNum * PAGE_ROW_COUNT;
		
		int rowCount = PAGE_ROW_COUNT;
		
		ImgListVO imgListvo = new ImgListVO();
		imgListvo.setStartRowNum(startRowNum);
		imgListvo.setEndRowNum(endRowNum);
		imgListvo.setRowCount(rowCount);
		
		//전체갯수
		int totalRow = 0;
		
		//글의 갯수
		totalRow = galleryService.boardCount(galleryId);
		System.out.println(totalRow);
		
		//전체 페이지의 갯수
		int totalPageCount = (int)Math.ceil(totalRow / (double) PAGE_ROW_COUNT); 
		request.setAttribute("totalPageCount", totalPageCount);	
		System.out.println(galleryId);

		List<ImgListVO> list = galleryService.galleryList(galleryId, startRowNum, rowCount);
		
		System.out.println("갤러리리스트 출력 : " + list);
		
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		
		rtnMap.put("list", list);
		rtnMap.put("totalPageCount", totalPageCount);

		mav.addObject("data", rtnMap);
		
		mav.setViewName("jsonView");
		return mav;
	}
	
	
	//게시물 숫자
	@RequestMapping(value = "/boardCount", method=RequestMethod.GET)
	public ModelAndView boardCount(String galleryId, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		int boardCount =  galleryService.boardCount(galleryId);
		
		request.setAttribute("totalPageCount", boardCount);
		
		mav.addObject("boardcount", boardCount);
		
		System.out.println(boardCount);
		
		mav.setViewName("jsonView");
		return mav;
	}



}
