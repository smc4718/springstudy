<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="자유게시판" name="title"/>
</jsp:include>

<style>
  .blind {
    display: none;
  }
</style>

  <div>
    <!-- ↑ 위에 jsp 태그에 아무것도 적지 말기. 에러남 -->
  
    <!-- 새글작성으로 무조건 가면 안 되고, 로그인되어 있는지 확인해야 한다. -->
    <!-- Servlet-context 파일에서 인터셉터에 <mapping path="/free/write.form"/> 작성해준다. -->
    <div><a href="${contextPath}/free/write.form">새글작성</a></div>
  
  <hr>
  
    <div>
      <table border="1">
        <thead>
          <tr>
            <td>순번</td>
            <td>작성자</td>
            <td>내용</td>
            <td>작성일자</td>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${freeList}" var="free" varStatus="vs">
            <tr class="list">
              <td>${beginNo - vs.index}</td>  <!-- 최근 것을 위로 올려준다. -->
              <td>${free.email}</td>
              <td>
              <!-- depth에 따른 들여쓰기 (depth 하나당 2칸씩 공백) -->
              <c:forEach begin="1" end="${free.depth}" step="1">&nbsp;&nbsp;</c:forEach>
              <!-- 폰트어썸에서 댓글버튼 이미지 코드 가져오기. -->
                <c:if test="${free.depth != 0}">   <!-- 댓글인지 아닌지는 depth가 1인지 2인지로 알 수 있다.-->
                  <i class="fa-solid fa-comment-dots"></i>  <!-- 폰트어썸 사이트에서 가져다 쓴 버튼 이미지 코드 -->
                </c:if>
              ${free.contents}
              <!-- 댓글작성버튼 -->
              <button type="button" class="btn_reply">댓글</button>
              </td>
              <td>${free.createdAt}</td>
            </tr>
            <tr class="blind write_tr">    <!-- 사용자에게 숨겨놓을 정보 --> <!--클래스끼리는 공백으로 구분하면 된다. -->
              <td colspan="4">
                <form method="post" action="${contextPath}/free/addReply.do">
                  <div>
                    <label for="email">작성자</label>        <!-- session에있는 유저의 이메일을 갖다 놓은 뒤 수정할 수 없게 readonly 한다. -->
                    <input type="text" name="email" id="email" value="${sessionScope.user.email}" readonly>   <!-- value 값이 꼭 필요함 -->
                  </div>
                  <div>
                    <label for="contents">내용</label>
                    <input type="text" name="contents" id="contents">
                  </div>  <!-- 원래 '내용'은 textarea로 하는 게 맞음.(여기에서는 그냥 text 써봤음) -->
                  <div>
                    <input type="hidden" name="depth" value="${free.depth}">
                    <input type="hidden" name="groupNo" value="${free.groupNo}">
                    <input type="hidden" name="groupOrder" value="${free.groupOrder}">
                    <button type="submit">댓글달기</button>
                  </div>
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
        <tfoot>
          <tr>
            <td colspan="4">${paging}</td>
          </tr>
        </tfoot>
      </table>
    </div>
  
  </div>
  
<script>


  const fnAddResult = () => {
	  let addResult = '${addResult}';
	  if(addResult !== ''){
		  if(addResult === '1'){
			  alert('게시글이 등록되었습니다.');
		  } else {
			  alert('게시글이 등록되지 않았습니다.');
		  }
	  }
  }

  
  const fnBlind = () => {
    $('.btn_reply').click((ev) => {			// ev = 이벤트 객체의 줄임말.
     if('${sessionScope.user}' === ''){ // 로그인 풀리면 댓글버튼이 안 보임. 세션에 올라간 유저 점검
      	if(confirm('로그인이 필요한 기능입니다. 로그인할까요?')){
      	  location.href = '${contextPath}/user/login.form';	// 로그인하는 곳으로 이동.	
      	}
      }
      // 화살표 함수는 this 객체가 지원되지 않기 때문에
      // 이벤트 대상을 "이벤트 객체"의 "target" 속성으로 처리한다.
      
      // ↓ 댓글작성하는 폼이 포함될 tr이다.
      let writeTr = $(ev.target).closest('.list').next()	  // closest : 가장 가까운.  // 가장 가까운 list 클래스.
      
      // 버튼 누를 때마다 열렸다 닫혔다 한다.
      // class="blind"를 가진 상태 : 숨김 상태이므로 열어 준다.
      if(writeTr.hasClass('blind')){		// blind 클래스를 가지고 있다.
    	 $('.write_tr').addClass('blind');	// 모든 작성화면 닫기
    	 writeTr.removeClass('blind');  	// 현재 작성화면 열기
      } 
      // class="blind"가 없는 상태 : 이미 열린 상태이므로 다시 숨긴다.
      else {
    	writeTr.addClass('blind');
      }
    })
	  
  }
  
  const fnAddReplyResult = () => {
	  let addReplyResult = '${addReplyResult}';
	  if(addReplyResult !== ''){
		  if(addReplyResult === '1'){
			  alert('댓글이 등록되었습니다.');
		  } else {
			  alert('댓글이 등록되지 않았습니다.');
		  }
	  }
  }
  
  // 함수 호출
  fnAddResult();
  fnBlind();
  fnAddReplyResult();
  

</script>

<%@ include file="../layout/footer.jsp" %>