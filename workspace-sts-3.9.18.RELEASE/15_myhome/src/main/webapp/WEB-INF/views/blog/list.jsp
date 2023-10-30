<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="블로그" name="title"/>
</jsp:include>

<div>

    <!-- ↑ 위에 jsp 태그에 아무것도 적지 말기. 에러남 -->
  
    <!-- 새글작성으로 무조건 가면 안 되고, 로그인되어 있는지 확인해야 한다. -->
    <!-- Servlet-context 파일에서 인터셉터에 <mapping path="/free/write.form"/> 작성해준다. -->
    <div>
    <a href="${contextPath}/blog/write.form">
      <button type="button" class="btn btn-primary">새글작성</button>
    </a>
  </div>
  
  <hr>

  <div>
    <table border="1">
      <thead>
        <tr>
          <td>순번</td>
          <td>제목</td>
          <td>조회수</td>
          <td>작성자(이메일)</td>
          <td>작성일자</td>
        </tr>
      </thead>
      <c:forEach items="${blogList}" var="b" varStatus="vs">
          <tr>
            <td>${beginNo - vs.index}</td>
            <td>
              <!-- 내가 작성한 블로그의 조회수는 증가하지 않는다. -->
              <c:if test="${sessionScope.user.userNo == b.userDto.userNo}">
                <a href="${contextPath}/blog/detail.do?blogNo=${b.blogNo}">${b.title}</a>
              </c:if>
              <!-- 내가 작성하지 않았다면 조회수를 증가시킨 뒤 상세보기 요청을 한다. -->
              <c:if test="${sessionScope.user.userNo != b.userDto.userNo}">
                <a href="${contextPath}/blog/increaseHit.do?blogNo=${b.blogNo}">${b.title}</a>
              </c:if>
            </td>
            <td>${b.hit}</td>
            <td>${b.userDto.email}</td>  <!-- userDto에서 email을 꺼내야 한다. -->
            <td>${b.createdAt}</td>
          </tr>
        </c:forEach>
      </tbody>
      <tfoot>
        <tr>
          <td colspan="5">${paging}</td>
        </tr>
      </tfoot>
    </table>
  </div>
          

</div>

<script>
  
  

</script>

<%@ include file="../layout/footer.jsp" %>