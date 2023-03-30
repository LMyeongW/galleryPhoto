<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사진등록</title>
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/galleryUpload.css">
</head>
<body>
	<form name="galleryForm">
    <div id="wrap">
    <input type="hidden" name="galleryId" value ="${member.galleryId}"/>
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
                    	
                        <p>TITLE : </p><input type="text" name="boardTitle" placeholder="제목을 입력해주세요.">
                    </div>
                </div>
                <div class="photo_box">
                    <div class="photo">
						<p class="nodata">* 등록된 이미지가 없습니다.</p>
                    </div>
                </div>
                <div class="textbox">
                    <textarea name="boardContent" id="" placeholder="기록을 남겨보세요~!"></textarea>
                </div>
                <div class="btn_box">
                    <div class="upload_btn">등록</div>
                    <div class="cancel">취소</div>
                </div>
            </div>
        </div>
        <!-- .inner_size -->
        <div class="uploadQ">
            <div class="alert_box">
                 	게시물을 등록하시겠습니까?
                <p class="wran">*등록시 갤러리에 추가됩니다.</p>
                <div class="btn_box">
                    <div class="yesQ">네</div>
                    <div class="noQ">아니오</div>
                </div>
            </div>
        </div>
        <!-- .deleteQ -->
        <div class="titleQ">
            <div class="alert_box">
                	제목을 입력해주세요.
                <div class="btn_box">
                    <div class="yesT">확인</div>
                </div>
            </div>
        </div>
        <!-- .titleQ -->
        
        <div class="pictureQ">
            <div class="alert_box">
                	사진을 등록해주세요.
                <div class="btn_box">
                    <div class="yesP">확인</div>
                </div>
            </div>
        </div>
        <!-- .pictureQ -->
        
  		<div class="loading">
       		<p class="loading1">
            	<img src="/resources/images/loading1.gif">
            </p>
        </div>
        
        <div class="successQ">
            <div class="alert_box">
                	등록에 성공하였습니다..
                <div class="btn_box">
                    <div class="yesS">확인</div>
                </div>
            </div>
        </div>  
        <!-- .SuccesQ -->
        
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
	
	//이미지 담을 배열
	var fileArray = [];
	
	$(document).ready(function(){
		$("#upload").on("change", imgFileSelect);
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
	
	$('.cancel').click(function(){
		location.href ="/";
	});
	
	$(".upload_btn").click(function(){
		
		var titleCk = $('input[name=boardTitle]').val();
		var conCk = $('textarea[name=boardContent]').val();
		var imgCk = $('input[name=uploadFile]').val();
		
		if(titleCk == null || titleCk == ""){
			$('.titleQ').addClass('on');
		    $('.yesT').click(function(){
		        $('.titleQ').removeClass ('on');
		    });
			return false;
		}
		
		if(imgCk == null || imgCk == ""){
			$('.pictureQ').addClass('on');
		    $('.yesP').click(function(){
		        $('.pictureQ').removeClass ('on');
		    });
			$('.nodate').show();
			return false;
		} else {
			$('.nodate').hide();
		}
		
		
		$('.uploadQ').addClass('on');
		
		$('.yesQ').click(function(){
			
			var Alldata = $('form[name=galleryForm]').serialize();
			
			$.ajax({
				
				url : '/myphoto/galleryPOST',
				type : 'POST',
				dataType : 'json',
				data : Alldata,
				success : function(result){
					
					let formData = new FormData();
					let fileInput = $('input[name="uploadFile"]');
					let fileList = fileInput[0].files;
					let fileObj = fileList[0];
					
					for(let i = 0; i < fileList.length; i++){
						formData.append("uploadFile", fileList[i]);
					}
					
					let data = {
						"boardNo" : result.galleryvo.boardNo,
						"galleryId" : result.galleryvo.galleryId
					};
					
					//file은 JSON에 포함될 수 없다. 그래서 FormData 안에 file과 JSON (= data)를 append 시킨다
					formData.append('boardInfo', new Blob([ JSON.stringify(data) ], {type : "application/json"}));
					
					
					 $.ajax({
						
						url : '/myphoto/uploadAjaxAction',
				    	processData : false,
				    	contentType : false,
						type : 'POST',
						dataType : 'json',
						data : formData,
						success : function(result){
							$('.loading').removeClass('on');
							$('.successQ').addClass('on');
							$('.yesS').click(function(){
								location.href = "/";
							});
							
						},
						error : function(){
							alert("연결에 실패하였습니다.");
						},
						beforeSend : function(){
							$('.loading').addClass('on');
						},
					});  
				},
				error : function(){
					alert("연결에 실패하였습니다.");
				}
				
				
			});
			

			
		});
		
		$('.noQ').click(function(){
			$('.uploadQ').removeClass('on');
		});

	});
	


</script>
</body>
</html>