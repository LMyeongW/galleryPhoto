# galleryPhoto

**사원관리 프로젝트**

**개발 인원**
* 1명(개인 프로젝트)


**프로젝트 목적**
* 다중업로드 통해 이미지를 등록하고 이미지 보관


**개발환경**
* Eclipse
* Spring
* Java8
* Tomcat9
* MySQL8.0.31.0


**템플릿엔진**
* JSP


**구현부분**
* 회원가입
  * 간단한 정보입력 후 회원가입

* 업로드
  * commons-io, commons-fileupload 라이브러리 추가
  * multipartResolver bean 등록
  * <input file multiple>을 통해 지정한 다중 file값들의 길이만큼 formData에 담아서 Controller로 보내줌 
  * 상대경로 지정 후 file 를 지정하고 인스턴스 선언하고 file.exists를 통해 해당 경로가 있는지 체크 후 없다면 file.mkdirs로 경로 생성
  * MultipartFile 를 for문을 통해 넘어온 file 길이만큼 해당값의 정보를 가져오고 vo나 map을 통해 필요값들을 저장 후 처리
 
* 사진 목록
  * 성공한 업로드 목록들을 List로 출력 
  
* 상세페이지
  * 해당 페이지의 파라미터 값을 타고 해당값의 정보들을 조회
  * 페이지안에서 수정페이지 이동(업로드 부분은 동일/업로드전 기존 값들 조회 후 삭제)
  * 삭제버튼 클릭 시 해당 파라미터 값을 가진 데이터 삭제
