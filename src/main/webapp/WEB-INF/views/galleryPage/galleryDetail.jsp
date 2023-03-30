<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/galleryDetail.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.css"/>
</head>
<body>
	<form name="detail_form">
	<input type="hidden" name="boardNo" value="${data.boardNo}">
<%-- 	<input type="hidden" name="galleryId" value="${data.galleryId}"> --%>
    <div id="wrap">
        <div class="inner_size">
            <header>
                <div class="thead">
                    <div class="title">
                    	<a href='/'>Gallery</a>
                    </div>

                    <div class="account_box">
                        <div class="login">
                        	<c:if test="${member != null}">	
                        		<div class="infoName">
                        			<p>${member.galleryName} 님</p>
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

                <div class="top_main">
                    <div class="title">
                       - TITLE <span>&nbsp&nbsp ${data.boardTitle}</span>
                    </div>
                    <div class="regDate">
                        <span><fmt:formatDate pattern = "yyyy.  MM.  dd" value="${data.boardUploadDate}"/></span>
                    </div>
                </div>
                <!-- .upload -->
                
                <div class="text">
                    <span>${data.boardContent}</span>
                </div>
                <div class="top_btnBox">
            		<a href="/myphoto/galleryUpdate?boardNo=${data.boardNo}" class="update">수정</a>&nbsp&nbsp<a href='javascript:void(0)' class="delete">삭제</a>
            	</div>
  				<div class="swiper gallery_box">
    				<div class="swiper-wrapper">

    				</div>
    				<div class="swiper-button-next"></div>
    				<div class="swiper-button-prev"></div>
  				</div>
                <!-- .silde -->


                <!-- .text -->
                <div class="btn_box">
                    <div class="back_btn">돌아가기</div>
                </div>
                
            <c:if test="${member.galleryId != data.galleryId}" >
            	<script>
            		location.href="/";
            		alert("등록된 정보가 없습니다.")
            	</script>
            </c:if>
                
            </main>
        </div>
        <!-- .inner_size -->
        
		<div class="deleteQ">
            <div class="alert_box">
                 	게시물을 삭제하시겠습니까?
                <p class="wran">*삭제시 모든 데이터가 삭제됩니다.</p>
                <div class="btn_box">
                    <div class="yesQ">네</div>
                    <div class="noQ">아니오</div>
                </div>
            </div>
        </div>
        <!-- .deleteQ -->
        
        <div class="endQ">
            <div class="alert_box">
                	삭제가 완료되었습니다.
                <p class="loading3">
                    <img src="/resources/images/loading2.gif">
                </p>
                <div class="btn_box">
                    <div class="yesE">확인</div>
                </div>
            </div>
        </div>
        <!-- .endQ -->
        
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
<script src="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.js"></script>
<script>

	$(document).ready(function(){
		
	    var swiper = new Swiper(".gallery_box", {
	    	
	    	autoHeight : true,
	        navigation: {
	          nextEl: ".swiper-button-next",
	          prevEl: ".swiper-button-prev",
	        },
	      });
	    

	});
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

	
	$('.back_btn').click(function(){
		location.href="/";
	});
	
	var data = $('form[name=detail_form]').serialize();
	
	$.ajax({
		
		url : '/myphoto/galleryDetailImg',
		type : 'get',
		data : data,
		success : function(result){
			$('.loading').hide();
			var data = result.rtnMap;

			var html = "";
			for(var i =0; i < data.length; i++){
				//http://comixs.synology.me:19092/resources
				var callPath = "http://comixs.synology.me:19092/resources" + data[i].uploadPath + data[i].imguuid +"_"+ data[i].fileName;

				html+="<div class='swiper-slide' data-file='"+data[i]+"'>";
				html += "<img src='"+ callPath +"'/>";
				html += "<input type='hidden' name='uploadPath' value='"+data[i].uploadPath+"'/>";
				html += "<input type='hidden' name='imguuid' value='"+data[i].imguuid+"'/>";
				html += "<input type='hidden' name='fileName' value='"+data[i].fileName+"'/>";
				html+="</div>";

			}
			
			$('.swiper-wrapper').append(html); 
		
			
		    $('.delete').click(function(){
		        $('.deleteQ').addClass('on');
		    });

		    $('.yesQ').click(function(){
		        $('.endQ').addClass ('on');
		        $('.deleteQ').removeClass('on');;

		    });
		    $('.noQ').click(function(){
		        $('.deleteQ').removeClass('on');;

		    });
			
		    $('.delete').click(function(){
		    	$('.deleteQ').addClass('on');
		    });	
		    
	        $('.yesQ').click(function(){
	        	var data2 = $('form[name=detail_form]').serialize();
	        	
		    	$.ajax({
		    		url:'/myphoto/galleryimgDelete',
		    		type : 'get',
		    		dataType : 'json',
		    		data : data2,
		    		success : function(result){
		    			var data3 = $('form[name=detail_form]').serialize();
		    			
				    	$.ajax({
					 		   
				    		url:'/myphoto/galleryTextDelete',
				    		type : 'get',
				    		dataType : 'json',
				    		data : data3,
				    		success : function(result){
					            $('.endQ').addClass('on');
					            $('.deleteQ').removeClass('on');
					            $('.loading').removeClass('on');
					            
					            $('.yesE').click(function(){
					            	location.href="/";
					            });
				    		},
				    		error: function(){
				    			alert("오류가 발생하였습니다.");
				    		},
							beforeSend : function(){
								$('.loading').addClass('on');
							}
				    			
				    	});
		    		},

		    	});

	        });

			
		},
		error : function(){
			alert("연결에 실패하였습니다.");
		},
		beforeSend : function(){
			$('.loading').show();
		}
		
		
	});

</script>
</body>
</html>