<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>갤러리 수정</title>
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/galleryUpdate.css">
</head>
<body>
	<form name="galleryUpdateForm">
	<input type="hidden" name="boardNo" value ="${data.boardNo}"/>
	<input type="hidden" name="galleryId" value ="${member.galleryId}"/>
    <div id="wrap">
        <div class="inner_size">
			
            <div class="content">
               	<div class="login">
            		<c:if test="${member != null}">	
            			${member.galleryName} 님

            		</c:if>
            		<c:if test="${member == null}">	
            			<a href="/account/login">login</a>
            		</c:if>
            	
            	</div>
                <input type="file" name="uploadFile" id="upload" multiple="true"/>
                <div class="title">
                    <div class="t1">
                        Gallery
                    </div>
                    
                    <div class="t2">
                    	
                        <p>TITLE : </p><input type="text" name="boardTitle" value ='${data.boardTitle }'placeholder="제목을 입력해주세요.">
                    </div>
                </div>
                <div class="photo_box">
                    <div class="photo">
                    </div>
                </div>
                <div class="textbox">
                    <textarea name="boardContent" id=""  placeholder="기록을 남겨보세요~!">${data.boardContent}</textarea>
                </div>
                <div class="btn_box">
                    <div class="update_btn">등록</div>
                    <div class="cancel">취소</div>
                </div>
            </div>
        </div>
        <!-- .inner_size -->
        
        <div class="updateQ">
            <div class="alert_box">
               	 게시물을 수정하시겠습니까?
                <p class="wran">*수정시 내용이 변경됩니다.</p>
                <div class="btn_box">
                    <div class="yesU">네</div>
                    <div class="noU">아니오</div>
                </div>
            </div>
        </div>
        <!-- .updateQ -->
        
       	<div class="loading">
       		<p class="loading1">
            	<img src="/resources/images/loading1.gif">
            </p>
        </div>
        <!-- .loading -->

        <div class="endQ">
            <div class="alert_box">
                	수정이 완료되었습니다.
                <p class="loading">
                    <img src="/resources/images/loading2.gif">
                </p>
                <div class="btn_box">
                    <div class="yesE">확인</div>
                </div>
            </div>
        </div>
        <!-- .endQ -->
        
        <div class="maxCountQ">
            <div class="alert_box">
                	최대 10개까지 등록하실 수 있습니다. 
                <div class="btn_box">
                    <div class="yesM">확인</div>
                </div>
            </div>
        </div>
        <!-- .maxCountQ -->
        
    </div>
    <!-- #wrap -->
    </form>
	
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script>

	$(document).ready(function(){
		
		$("#update_btn").on("click", imgFileSelect);
		imgList();
	});
	
	function imgFileSelect(e){
		
		//이미지 정보들 초기화
		fileArray = [];
		$(".photo").empty();
		
		var files = e.target.files;
		//fileArray 배열 목록
		var filesArr = Array.prototype.slice.call(files);
				
		var index = 0;
		var fileCount = 0;
		var maxCount = 10;
		
		
		filesArr.forEach(function(f){
			if(!f.type.match("image.*")){
				alert("이미지 파일만 가능합니다.")
				return;
			}
			
			if(fileCount + filesArr.length > maxCount){
				$('.maxCountQ').addClass('on');
				$('#upload').val("");
				
				$('.yesM').click(function(){
					$('.maxCountQ').removeClass('on');
				});
				
				return false;
			}
			
			fileArray.push(f);
			
			var reader = new FileReader();
			reader.onload = function(e){
				var html = ""
				
				html +="<div class='image' id='imgFile_"+index+"'>";
				html +="<img src=\"" + e.target.result + "\" data-file='"+ f.name +"' class='selProductFile' title='Click to remove'>";
				html +="</div>";
				
				$(".photo").append(html);
				index++;
			}
			reader.readAsDataURL(f);
            
		});
	
	}
	

	function imgList(){
		
		var data = $('form[name=galleryUpdateForm]').serialize();	
		$.ajax({
			
			url : '/myphoto/galleryDetailImg',
			type : 'get',
			data : data,
			success : function(result){

				var data = result.rtnMap;
				
				var html = "";
				for(var i =0; i < data.length; i++){
					//http://comixs.synology.me:19092/resources
					var callPath = "http://localhost:18080/resources" + data[i].uploadPath + data[i].imguuid +"_"+ data[i].fileName;
					var callPath2 = data[i].imguuid +"_"+ data[i].fileName;
					
					html+="<div class='image'>";
					html += "<img src='"+ callPath +"'/>";
					html += "<input type='hidden' name='uploadPath' value='"+data[i].uploadPath+"'/>";
					html += "<input type='hidden' name='imguuid' value='"+data[i].imguuid+"'/>";
					html += "<input type='hidden' name='fileName' value='"+data[i].fileName+"'/>";
					html+="</div>";

				}
				
				$('.photo').append(html);
				

				$("#upload").on("change", imgFileSelect);


				$('.update_btn').click(function(){
					$('.updateQ').addClass('on');
				});
				
				$('.yesU').click(function(){
					
					var data2 = $('form[name=galleryUpdateForm]').serialize();	
					$.ajax({

						url :'/myphoto/galleryTextUpdate',
						type : 'post',
						data : data2,
						dataType : 'json',
						success : function(result){

							var checkFile = $('input[name=uploadFile]').val();
					
							if(checkFile == null || checkFile == ""){
								$('.endQ').addClass('on');
								
					            $('.yesE').click(function(){
					            	var boardNo = $('input[name=boardNo]').val();
					            	location.href="/myphoto/galleryDetail?boardNo="+boardNo;
					            });
								
							} else {

								let formData = new FormData();
								let fileInput = $('input[name="uploadFile"]');
								let fileList = fileInput[0].files;
								let fileObj = fileList[0];
								
								for(let i = 0; i < fileList.length; i++){
									formData.append("uploadFile", fileList[i]);
								}

								
								let targetData = {
									"boardNo" : result.galleryvo.boardNo,
									"galleryId" : result.galleryvo.galleryId
								};
									
								//file은 JSON에 포함될 수 없다. 그래서 FormData 안에 file과 JSON (= data)를 append 시킨다
								formData.append('boardInfo', new Blob([ JSON.stringify(targetData) ], {type : "application/json"}));
								
								$.ajax({

									url :'/myphoto/galleryImgUpdatePost',
									type : 'post',
							    	processData : false,
							    	contentType : false,
									data : formData,
									dataType : 'json',
									success : function(result){
										
							            $('.endQ').addClass('on');
							            $('.updateQ').removeClass('on');
							            $('.loading').removeClass('on');
							            
							            $('.yesE').click(function(){
							            	var boardNo = $('input[name=boardNo]').val();
							            	location.href="/myphoto/galleryDetail?boardNo="+boardNo;
							            });
									
										
									},
									error : function(){
										alert("연결에 실패하였습니다2.");
									},
									beforeSend : function(){
										$('.loading').addClass('on');
									}
									
								});
							}
							


						},
						error : function(){
							aler("연결에 실패하였습니다.");
						}
						
					});
				});
				
				$('.cancel').click(function(){
					var boardNo = $('input[name=boardNo]').val();
					location.href="/myphoto/galleryDetail?boardNo="+boardNo;
				})
				
				$('.noU').click(function(){
					$('.updateQ').removeClass('on');
				});
			

			},
			error : function(){
				alert("연결실패");
			}
			
			
		});
		

	}

</script>
</body>
</html>