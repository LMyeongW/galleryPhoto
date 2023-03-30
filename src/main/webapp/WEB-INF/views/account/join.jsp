<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/join.css">
</head>
<body>
    <div id="wrap">
        <div class="inner_size">

            <div class="content">
                <div class="title">
                    	회원가입
                </div>
                <!-- .title -->
                <div class="text">
                    	자신만의 소중한 갤러리를 만들고 싶다면 가입하세요.
                </div>
                <!-- .text -->

                <form name="joinform">
                    <div class="input_box">
                        <div class="name">
                            <span>이름</span>
                            <input name="galleryName" placeholder="이름을 입력해주세요." id="inputVal">
                            <div class="warn_name">이름을 입력해주세요.</div>
                        </div>
                        <div class="email_Id">
                            <span>아이디</span>
                            <input name="galleryId" placeholder="이메일으로 입력해주세요." id="inputVal1">
                            <div class="warn_Id">아이디를 입력해주세요.</div>
                            <div class="warn_Idck">이메일형식에 맞게 입력해주세요.</div>
                        </div>
                        <div class="pw">
                            <span>비밀번호</span>
                            <input name="galleryPw" type="password" placeholder="비밀번호 입력해주세요." id="inputVal2">
                            <div class="warn_pw">비밀번호를 입력해주세요.</div>
                        </div>
                        <div class="pwck">
                            <span>비번확인</span>
                            <input name="galleryPwck"  type="password" placeholder="비밀번호 확인" id="inputVal3">
                            <div class="warn_pwck">비밀번호가 일치하지 않습니다.</div>
                        </div>

                        <div class="btn_box">
                            <p class="joinbtn">가입</p>
                            <p class="cancel">취소</p>
                        </div>
                    </div>
                    <!-- .input_box -->
                </form>

            </div>
            <!-- .content -->
        </div>
        <!-- .inner_size -->
    </div>
    <!-- #wrap -->
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function(){
    	
		$('.cancel').click(function(){
			location.href ="/account/login";
		});
        
    	$("#inputVal").on("propertychange change keyup paste input", function(){
    		
    		var valck = $('#inputVal').val();

    		if(valck == ""){
    			$('#inputVal').removeClass('on');
    		} else {
    			$('#inputVal').addClass('on');
    			$('.warn_name').css("display","none");
    		}
    		
    	});
    	
    	$("#inputVal1").on("propertychange change keyup paste input", function(){
    	
    		var valck1 = $('#inputVal1').val();
    		
    		if(valck1 == ""){
    			$('#inputVal1').removeClass('on');
    			$('.warn_Idck').css("display","none");
    		} else {
    			$('#inputVal1').addClass('on');
    			$('.warn_Id').css("display","none");
    			
    		}
    			
    	});
    	
    	$("#inputVal2").on("propertychange change keyup paste input", function(){
        	
    		var valck2 = $('#inputVal2').val();
    		
    		if(valck2 == ""){
    			$('#inputVal2').removeClass('on');
    		} else {
    			$('#inputVal2').addClass('on');
    			$('.warn_pw').css("display","none");
    		}
    		
    	});
    	
    	$("#inputVal3").on("propertychange change keyup paste input", function(){
        	
    		var valck3 = $('#inputVal3').val();
    		
    		if(valck3 == ""){
    			$('#inputVal3').removeClass('on');
    		} else {
    			$('#inputVal3').addClass('on');
    		}
    		
    	});
    	
    	$('.joinbtn').on("click", function(){
    		
    		var name = $('#inputVal').val();
    		var id = $('#inputVal1').val();
    		var pw = $('#inputVal2').val();
    		var pwck = $('#inputVal3').val();
    		
    		if(name == "" || name == null){
    			$('.warn_name').css("display","block");
    			return false;
    		}else {
    			$('.warn_name').css("display","none");
    		}
    		
    		if(id == "" || id == null){
    			$('.warn_Id').css("display","block");
    			return false;
    		}else {
    			$('.warn_Id').css("display","none");
    		}
    		
    		if(pw == "" || pw == null){
    			$('.warn_pw').css("display","block");
    			return false;
    		}else {
    			$('.warn_pw').css("display","none");
    		}
    		
    		if(pw == pwck){
    			$('.warn_pwck').css("display","none");
    			
    		}else {
    			$('.warn_pwck').css("display","block");
    			return false;
    		}
    		
    		
    		/* 입력 이메일 형식 유효성 검사 */
    		function mailFormCheck(email){
    			var form = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    			
    			return form.test(email);  //.test 형식에 부합하면 true
    		}
    		
    		var email = $("#inputVal1").val();
    		
    		if(mailFormCheck(email)){
    			$('.warn_Idck').css("display","none");
    		} else {
    			$('.warn_Idck').css("display","block");
    			return false;
    		}
    		
    		/* 아이디중복체크 */
    		$.ajax({
    			
    			url : '/account/galleryId',
    			type : 'get',
    			data : {
    				"galleryId" : id
    			},
    			success : function(result){
    				console.log(result);
    				
    				if(result == 'fail'){
    					alert("이미 존재하는 아이디입니다.");
    					return false;
    				} else {
    		    		var data = $('form[name=joinform]').serialize();
        	    		console.log(data);
        	 			
        	    		$.ajax({
        	    			
        	    			url : '/account/join.do',
        	    			type : 'post',
        	    			dataType :'json',
        	    			data : data,
        	    			success : function(result){
        	    				alert("회원가입 성공");
        	    				location.href="/account/login";
        	    			},
        	    			error : function(){
        	    				alert("연결에 실패하였습니다.");
        	    			}
        	    			
        	    		});
    				}
    				
    

    			},
    			error : function(){
    				alert("연결에 실패하였습니다.");
    			}
    			
    		});

    	});

    });
</script>
</body>
</html>