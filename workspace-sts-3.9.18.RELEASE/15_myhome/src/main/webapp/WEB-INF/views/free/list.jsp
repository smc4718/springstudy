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
            <tr>
              <td>${beginNo - vs.index}</td>  <!-- 최근 것을 위로 올려준다. -->
              <td>${free.email}</td>
              <td>${free.contents}</td>
              <td>${free.createdAt}</td>
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

  fnAddResult();

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

</script>

<%@ include file="../layout/footer.jsp" %>