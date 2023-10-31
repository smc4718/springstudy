<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="${blog.blogNo}번 블로그" name="title"/>
</jsp:include>

<!--선택하는 느낌이 나도록 커서 넣어주기-->
<<style>
  .blind {
    display: none;
  }
  .ico_remove_comment {
    cursor: pointer;
  }
</style>

<div>

  <!-- 블로그 상세보기 -->
  <div>
    <h1>${blog.title}</h1>
    <div>작성자 : ${blog.userDto.name}</div>
    <div>조회수 : ${blog.hit}</div>
    <div>작성IP : ${blog.ip}</div>
    <div>작성일 : ${blog.createdAt}</div>
    <div>수정일 : ${blog.modifiedAt}</div>
    <div>
     <!-- 블로그의 작성자는 편집/삭제를 수행할 수 있다. -->
     <c:if test="${sessionScope.user.userNo == blog.userDto.userNo}"> <!-- 작성자가 맞다면(로그인되어있다면) -->
      <form id="frm_btn" method="post">
       <input type="hidden" name="blogNo" value="${blog.blogNo}"> <!-- blogNo : 블로그 번호를 키값으로 포함시켜야 된다. -->
        <button type="button" id="btn_edit">편집</button>
        <button type="button" id="btn_remove">삭제</button>
      </form>
     </c:if>
    </div>
    <div>${blog.contents}</div>
  </div>
  <script>
  
   var frmBtn = $('#frm_btn');
   
  	const fnEditBlog = () => {  // 변수를 저장할 때, 함수를 등록시키면 변수가 함수가 된다.
  		$('#btn_edit').click(() => {
  		  frmBtn.attr('action', '${contextPath}/blog/edit.form');
  		  frmBtn.submit();
  		})		
  	}
  	
  	const fnRemoveBlog = () => {
  		$('#btn_remove').click(() => {
  		  if(confirm('블로그를 삭제하면 모든 댓글이 함께 삭제됩니다. 삭제할까요?')){
  	 		frmBtn.attr('action', '${contextPath}/blog/remove.do');
  	  		frmBtn.submit();
  		  }
  		})
  	}
  
  	fnEditBlog();
  	fnRemoveBlog();
  	
  	
  </script>
  
  <hr>
  
  <!-- 댓글 작성 화면 -->
  <div>
    <form id="frm_comment_add">
      <textarea rows="3" cols="50" name="contents" id="contents" placeholder="댓글을 작성해 주세요"></textarea>
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">   <!-- 세션에 있는 유저에 유저넘버 -->
      <input type="hidden" name="blogNo" value="${blog.blogNo}">
      <button type="button" id="btn_comment_add">작성완료</button>
    </form>
  </div>
  
  <!-- 블로그 댓글 목록 -->
  <div style="width: 100%; border-bottom: 1px solid gray;"></div>  <!-- div로 써놨지만, 사실은 회색 선이다. -->
  <div id="comment_list"></div>
  <div id="paging"></div>
  
  <script>
    
      const fnCommentAdd = () => {
        $('#btn_comment_add').click(() => {
      	 if('${sessionScope.user}' === ''){
            if(confirm('로그인이 필요한 기능입니다. 로그인할까요?')){
                location.href = '${contextPath}/user/login.form';
            } else {
                return;
            }
         }
          $.ajax({
           // 요청
           type: 'post',
           url: '${contextPath}/blog/addComment.do',
           data: $('#frm_comment_add').serialize(),	 // serialize 가 등장하려면 form 안에는 name이 들어가있어야하고(name기준으로 동작함),
           // 응답								     // 폼 안의 모든 정보를 가져오기 위해 serialize를 쓴다.
           dataType: 'json',
           success: (resData) => {   // {"addCommentResult": 1}
            if(resData.addCommentResult === 1){
              alert('댓글이 등록되었습니다.');
              $('#contents').val(''); 	// 댓글 등록 후 내용창 비우기.
              fnCommentList();
            }
           }
          })
        })
      }
      
      // 전역 변수
      var page = 1;
      
      const fnCommentList = () => {
      $.ajax({
        // 요청
        type: 'get',
        url: '${contextPath}/blog/commentList.do',
        data: 'page=' + page + '&blogNo=${blog.blogNo}',
        // 응답
        dataType: 'json',
        success: (resData) => { // resData = {"commentList": [], "paging": "<div>...</di>"}
		  $('#comment_list').empty();
          $('#paging').empty();
          if(resData.commentList.length === 0){
			$('#comment_list').text('첫 번째 댓글의 주인공이 되어 보세요');
			$('#paging').text('');
		  	return;
          }
          $.each(resData.commentList, (i, c) => {
            let str = '';
            if(c.depth === 0){
              str += '<div style="width: 100%; border-bottom: 1px solid gray;">';
            } else {															  // 꼭 구현할 모양을 같이 적어가면서 쓰기.
              str += '<div style="width: 100%; border-bottom: 1px solid gray; margin-left: 32px;">';						
            }
            if(c.status === 0){
              str += '<div>삭제된 댓글입니다.</div>';
            } else {
              str += '  <div>' + c.userDto.name + '</div>';							         
              str += '  <div>' + c.contents + '</div>';		    						        
              str += '  <div style="font-size: 12px;">' + c.createdAt + '</div>';	            
              if(c.depth === 0){		// depth가 0이면 답글달기 버튼을 보여주자.
                str += '  <div><button type="button" class="btn_open_reply"> 답글달기</button></div>'; 
              }
              /************************** 답글 입력 창 **************************/
              str += '  <div class="blind frm_add_reply_wrap">';  // 클래스를 더 주고 싶으면 공백으로 구분하여 작성.
              str += '    <form class="frm_add_reply">';
              str += '      <textarea rows="3" cols="50" name="contents" placeholder="답글을 입력하세요"></textarea>';
              str += '      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">';
              str += '      <input type="hidden" name="blogNo" value="${blog.blogNo}">';
              str += '      <input type="hidden" name="groupNo" value="' + c.groupNo + '">';
              str += '      <button type="button" class="btn_add_reply">답글작성완료</button>';
              str += '    </form>';
              str += '  </div>';
              /******************************************************************/
              if('${sessionScope.user.userNo}' == c.userDto.userNo){	// 자바스크립트의 특징 : 등호 ==. true값 (===와 ==는 다름)            	  
                str += '  <div>';
                str += '    <input type="hidden" value="' + c.commentNo + '">';
                str += '    <i class="fa-solid fa-trash ico_remove_comment"></i>';  // 공백으로 클래스끼리 구분한다.
                str += '  </div>';												 			  		   	
              }
            }
            str += '</div>';												 			  		
            $('#comment_list').append(str);  // comment_list 에 저장.
          })
          $('#paging').append(resData.paging);  // fnAjaxPaging() 함수가 호출되는 곳
        }
      })
    }
      
      const fnAjaxPaging = (p) => {
    	page = p;
    	fnCommentList(); // 몇페이지로 바뀔거다. 라는 새 목록 갱신.
      }
      
      const fnBlind = () => {
    	  $(document).on('click', '.btn_open_reply', (ev) => {
  		    if('${sessionScope.user}' === ''){
  	          if(confirm('로그인이 필요한 기능입니다. 로그인할까요?')){
  	                location.href = '${contextPath}/user/login.form';
  	          } else {
  	            return;
  	          }
  	        }
            var blindTarget = $(ev.target).parent().next();
            if(blindTarget.hasClass('blind')){
              $('.frm_add_reply_wrap').addClass('blind'); // 모든 답글 입력화면 닫기
            	blindTarget.removeClass('blind');		  // 지금 타겟의 답글만 입력화면 열기
            } else {
              blindTarget.addClass('blind');
            }
    	 })
      }
      
      const fnCommentReplyAdd = () => {
    	$(document).on('click', '.btn_add_reply', (ev) => {	// 위 상황처럼 버튼을 사용할 수 없을 때 사용하는 방법 : document 방식.
  		if('${sessionScope.user}' === ''){
            if(confirm('로그인이 필요한 기능입니다. 로그인할까요?')){
                location.href = '${contextPath}/user/login.form';
            } else {
              return;
            }
        }
    	  var frmAddReply = $(ev.target).closest('.frm_add_reply'); 
       	  $.ajax({
       		 // 요청
       		 type: 'post',
       		 url: '${contextPath}/blog/addCommentReply.do',
       		 data: frmAddReply.serialize(),	// 클릭한 버튼의 부모(.frm_add_reply)클래스의 모든 요소를 보내 준다.
       	     // 응답
       	     dataType: 'json',
       	     success: (resData) => {	// resData = {"addCommentReplyResult": 1}
       	       if(resData.addCommentReplyResult === 1){
       	    	  alert('답글이 등록되었습니다.');
       	    	  fnCommentList();	 // 목록 갱신하는 함수 호출
       	    	  frmAddReply.find('textarea').val(''); // 답글내용 초기화 , find : 자식 찾는 메소드
       	       } else {
       	    	  alert('답글이 등록되지 않았습니다.');
       	       }
       	     }
       	  })
    	})
      }
      
      const fnCommentRemove = () => {
      	$(document).on('click', '.ico_remove_comment', (ev) => {
      		if(!confirm('해당 댓글을 삭제할까요?')){
      			return;
      		}
      		$.ajax({
      			// 요청
      			type: 'post',
      			url: '${contextPath}/blog/removeComment.do',
      			data: 'commentNo=' + $(ev.target).prev().val(),	  // prev() : 인접형제.
      			// 응답
      			dataType: 'json',
      			success: (resData) => {  // resData = {"removeResult": 1}
      				if(resData.removeResult === 1){
      					alert('해당 댓글이 삭제되었습니다.');
      					fnCommentList();
      				} else {
      					alert('댓글이 삭제되지 않았습니다.');
      				}
      			}
      		})
      	})
      }
      
      fnCommentAdd();
      fnCommentList();
      fnBlind();
      fnCommentReplyAdd();
      fnCommentRemove();
      
      
      /*
      <div style="width: 100%; border-bottom: 1px solid gray;">
      
        // 삭제된 댓글/답글
        <div>삭제된 댓글입니다.</div>
        
        // 정상 댓글/답글
        <div>이름</div>
        <div>내용</div>
        <div style="font-size: 12px;">작성일자</div>
        <div><button type="button" class="btn_open_reply"> 답글달기</button></div>
        <div class="blind">
        <div class="blind frm_add_reply_wrap">  
          <form class="frm_add_reply"
          <textarea rows="3" cols="50" name="contents" placeholder="답글을 입력하세요"></textarea>
          <input type="hidden" name="userNo" vlaue="">  
          <input type="hidden" name="blogNo" vlaue="">  
          <input type="hidden" name="groupNo" vlaue="">  
          <button type="button" class="btn_add_reply">답글작성완료</button>
          </form>
        </div>
        <input type="hidden" value="commentNo">
        <div><button type="button" class="btn_remove_comment">삭제</button></div>
      </div>
      */
      
    </script>  	

</div>


<%@ include file="../layout/footer.jsp" %>