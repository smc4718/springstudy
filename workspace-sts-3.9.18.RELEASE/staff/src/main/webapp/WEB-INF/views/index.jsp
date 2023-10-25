<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
  #paging a {
    margin: 10px;
  }
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>

    $(() => {
        fnRegisterStaff();
        fnGetStaffList();
    })
    
    
    // 사원 목록 조회
    const fnGetStaffList = () => {
        $.ajax({
            type: 'get',
            url: '${contextPath}/getStaffList.do',
            dataType: 'json',
            success: (resData) => {
                if (resData.staffList) {
                    const staffList = resData.staffList;
                    const $staffList = $('#staff_list');
                    $staffList.empty();
    
                    for (const staff of staffList) {
                        const $tr = $('<tr>');
                        $tr.append($('<td>').text(staff.sno));
                        $tr.append($('<td>').text(staff.name));
                        $tr.append($('<td>').text(staff.dept));
                        $tr.append($('<td>').text(staff.salary));
                        $staffList.append($tr);
                    }
                }
            },
            error: (jqXHR) => {
                alert('사원 목록을 불러오는데 실패했습니다.');
            }
        });
    }
    
	// 사원 등록하기
	const fnRegisterStaff = () => {
	  $('#btn_register').click(() => {
		$.ajax({
		  type: 'post',
		  url: '${contextPath}/add.do',
		  data: $('#frm_register').serialize(),
		  dataType: 'json',
		  success: (resData) => {
			if(resData.addResult === 1){
			  alert('사원 등록이 성공했습니다.');
			  fnGetStaffList();
		      // 입력란 초기화
              $('#sno').val('');
              $('#name').val('');
              $('#dept').val('');
			} else {
			  alert('사원 등록이 실패했습니다.');
			}
		  },
		  error: (jqXHR) => {
		    if(jqXHR.responseJSON.addResult === 0){
		    	alert('사원 등록이 실패했습니다.');
		    }
		  }
		})
	  })
	}
	
	// 사원 번호로 사원 조회
	const fnGetStaffBySno = (sno) => {
	    $.ajax({
	        type: 'get',
	        url: '${contextPath}/getStaff.do?sno=' + sno,
	        dataType: 'json',
	        success: (resData) => {
	            if (resData.staff) {
	                // 사원 정보
	                const staff = resData.staff;
	                const $staffList = $('#staff_list');
	                $staffList.empty();
	                const $tr = $('<tr>');
	                $tr.append($('<td>').text(staff.sno));
	                $tr.append($('<td>').text(staff.name));
	                $tr.append($('<td>').text(staff.dept));
	                $tr.append($('<td>').text(staff.salary));
	                $staffList.append($tr);
	            } else {
	                alert('조회된 사원 정보가 없습니다.');
	            }
	        },
	        error: (jqXHR) => {
	            alert('사원 조회에 실패했습니다.');
	        }
	    });
	}
	
	

</script>
</head>
<body>

    <div>
    <h1>사원등록</h1>
    <div>
      <form id="frm_register">
        <input type="text" name="sno" id="sno" placeholder="사원번호">
        <input type="text" name="name" id="name" placeholder="사원명">
        <input type="text" name="dept" id="dept" placeholder="부서명">
        <button type="button" id="btn_register">등록</button>
      </form>
    </div>
    </div>
    
    <hr>
    
    <div>
    <h1>사원조회</h1>
    <div>
        <input type="text" name="query" id="query" placeholder="사원번호입력">
        <button type="button" id="btn_query">조회</button>
        <button type="button" id="btn_list">전체</button>
    </div>
</div>
    
    <hr>
    
    <div>
      <h1>사원목록</h1>
      <div>
        <table border="1">
          <thead>
            <tr>
              <td>사원번호</td>
              <td>사원명</td>
              <td>부서명</td>
              <td>연봉</td>
            </tr>
          </thead>
          <tbody id="staff_list"></tbody>
        </table>
      </div>
    </div>
   
</body>
</html>