<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<title>Home</title>
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/index.css">
</head>

<body>
	<form name="list_form">
	<input type="hidden" name="galleryId" value ="${member.galleryId}"/>
    <div id="wrap">
        <div class="inner_size">
            <header>
                <div class="thead">
                    <div class="title">Gallery</div>
					
                    <div class="account_box">
                        <div class="login">
                           	<c:if test="${member != null}">	
                        		<div class="infoName">
                        			<p class="name">${member.galleryName} 님</p>
                        			<div class="arr"><img src="/resources/images/arr.png"></div>
                     
                        		    <div class="userInfo">
                        				<div class="info1">
                        					${member.galleryName} 님 <span class="logOut">로그아웃</span>
                        				</div>
                        				<div class="info2">${member.galleryId}</div>
                        			</div>
                        		</div>
                        	</c:if>
                        	
                        	<c:if test="${member == null}">	
                            	<a href="/account/login">login</a>
                            </c:if>
                        </div>
                    </div>
                    <!-- .account_box -->
                </div>
                <!-- .thead -->
            </header>

            <main>
                <div class="content">
                    <div class="content_top">
                    	<c:if test="${member != null}">	
<!--                     		<div class="count"> -->
<!--                     			게시물 : 4 -->
<!--                     		</div> -->
                    	</c:if>
                        <a href="/myphoto/galleryUpload" class="write">갤러리 작성</a>
                    </div>

                    <ul class="gallery_box">
                    	<c:if test="${member != null}">		
                    		<div class="photo">
								<p class="nodata">* 등록된 게시물이 없습니다.</p>
                    		</div>
                    	</c:if>

							
						<c:if test="${member == null}">	
                    		<div class="please_login">
                    			<a href="/account/login" class="login_con">로그인 후 이용해주세요.(클릭)</a>
                    		</div>
                    	</c:if>
                    	
                    	                    		
                    	<div class="imgLoading">
                    		<div class="loadingInfo">
                    			<img src="/resources/images/loading.gif">		
                    		</div>
                    		이미지 로딩중...
                    	</div>
                     
                    </ul>

 
                </div>
                <!-- .content -->
            </main>

        </div>
        <!-- .inner_size -->
        
        <div class="loading">
       		<p class="loading1">
            	<img src="/resources/images/loading1.gif">
            </p>
        </div>
        <!-- .loading -->
        
        <div class="bakcg"></div>
    </div>
    <!-- #wrap -->
    </form>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script>
	$(document).ready(function(){

		$('.infoName').click(function(){
			$('.userInfo').addClass('on');
			$('.bakcg').addClass('on');
			
		});
		
		$('.bakcg').click(function(){
			$('.userInfo').removeClass('on');
			$(this).removeClass('on');
		});
		
		$('.logOut').click(function(){
			location.href ="/account/logout.do";
		});
		
		loadList(1);
		
	});
	

	//첫번째 페이지번호
	var currentPage =1;
	
	function loadList(){
    	
    	var galleryId = $('input[name=galleryId]').val(); 

		$.ajax({
			url : 'myphoto/galleryList',
			type : 'get',
			data : {
				"galleryId" : galleryId,
				"pageNum" : currentPage
			},
			dataType : 'json',
			success : function (result){
				var data= result.data

				
				if(data.list.length > 0){
					var html = "";
					for(var i=0; i<data.list.length; i++){
						
						var callPath = "http://comixs.synology.me:19092/resources"+ data.list[i].uploadPath + data.list[i].imguuid +"_"+ data.list[i].fileName;
						
						html+="<li>";
						html+="<a href='/myphoto/galleryDetail?boardNo="+data.list[i].boardNo+"'>";
						html+="<div class='bg'>";
						html+="<div class='regdate'> "+data.list[i].boardUploadDate+"</div>";
						html+="<div class='imgTitle'>"+ data.list[i].boardTitle +"</div>";
						html+="</div>";
						html+="<div class='img_area'>";
						html+="<img src='"+callPath+"'>";
						html+="</div>";
						html+="<a>";
						html+="</li>";

					}
					$('.gallery_box').append(html);
					$('.imgLoading').hide();
				} 

			
				$(window).on("scroll", function(){
					
					var isLosding = false;
					//위로 스크롤된 길이
					var scrollTop = $(window).scrollTop();
					
					//웹브라우저의 창의 높이
					var windowHeight = $(window).height();
					
					//문서 천제의 높이
					var documentHeight = $(document).height();
					
					//바닥까지 스크롤 되었는 지 여부 확인
					var isBottom = scrollTop + windowHeight + 50 >= documentHeight;
					
					if(isBottom) {
						//마지막 페이지라면 
							if(currentPage == data.totalPageCount || isLosding){
								return; //함수가 요기서 끝남
							}

						//현재로딩중
						isLoading = true;
						
						//로딩바
// 						$('.imgLoading').show();
						
						currentPage++;
							
						loadList(currentPage);
					}
				});
				$('.loading').hide();

				
			},
			error : function(){
				alert("연결에 실패하였습니다. 로그인 후 다시 시도 해주세요.");
				$('.loading').hide();
				$('.imgLoading').hide();

			}
		});
		

    }
	
	var galleryId = $('input[name=galleryId]').val(); 
 	$.ajax({
 		
 		url : '/myphoto/boardCount',
 		type : 'get',
 		dataType : 'json',
 		data : {
 			"galleryId" : galleryId
 		},
 		success : function(result){

			if(result.boardcount == 0 ){
				$('.nodata').show();
			}else {
				$('.nodata').hide();
			}
			
 			var html = "";
 			html+= '<div class="count">총 게시물 : '+ result.boardcount + '</div>'
 			$('.content_top').append(html);
				
 		},
 		error : function(){}
			
 	});
	
	
</script>
</body>
</html>
