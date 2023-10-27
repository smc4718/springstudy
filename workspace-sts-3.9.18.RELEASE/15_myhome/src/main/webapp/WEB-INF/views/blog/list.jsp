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


    <!-- ↑ 위에 jsp 태그에 아무것도 적지 말기. 에러남 -->
  
    <!-- 새글작성으로 무조건 가면 안 되고, 로그인되어 있는지 확인해야 한다. -->
    <!-- Servlet-context 파일에서 인터셉터에 <mapping path="/free/write.form"/> 작성해준다. -->
    <div style="text-align: center;">
      <a href="${contextPath}/blog/write.form">
        <button type="button" class="btn btn-success">새글작성</button>
      </a>
    </div>
  
    <hr>
    
    <div>
    
    </div>
  
<script>
  
  

</script>

<%@ include file="../layout/footer.jsp" %>