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

	// 메소드 호출부
    $(function(){
	  fnChkAll();
	  fnChkOne();
	  fnInit();
	  fnMemberRegister();
	  fnMemberList();
	  fnMemberDetail();
	  fnMemberModify();
	  fnRemoveMember();
	  fnRemoveMembers();
  })
  
   // 전체 선택을 클릭하면 개별 선택에 영향을 미친다.
    function fnChkAll(){
	  $('#chk_all').click(function(){
		  $('.chk_one').prop('checked', $(this).prop('checked'));
	  })
  }
  
  //개별 선택을 클릭하면 전체 선택에 영향을 미친다.
    function fnChkOne(){
	  $(document).on('click', '.chk_one', function(){
		  var total = 0;
		  $.each($('.chk_one'), function(i, elem){
			  total += $(elem).prop('checked');
		  })
		  $('#chk_all').prop('checked', total === $('.chk_one').length);
	  })
  }
  
 // 입력란 초기화
    function fnInit(){
  	  $('#memberNo').val('');
  	  $('#id').val('').prop('disabled', false);
  	  $('#name').val('');
  	  $(':radio[value=none]').prop('checked', true);
  	  $('#address').val('');
  	  $('#btn_register').prop('disabled', false);
      $('#btn_modify').prop('disabled', true);
      $('#btn_remove').prop('disabled', true);
    }

  // 회원 등록
  function fnMemberRegister(){
    $('#btn_register').click(function(){
      $.ajax({
        // 요청
        type: 'post',
        url: '${contextPath}/members',
        contentType: 'application/json',
        data: JSON.stringify({
          id: $('#id').val(),	    // id 임의로 넣었음
          name: $('#name').val(),	// name 임의로 넣었음
          gender: $(':radio:checked').val(),  // radio 중에 체크된 놈의 값
          address: $('#address').val() 
        }),
        // 응답
        dataType: 'json',
        success: function(resData){ // resData === {"addResult": 1}
      	if(resData.addResult === 1){
        	  alert('회원 정보가 등록되었습니다.');
        	  page = 1;
        	  fnMemberList();
        	  fnInit();
          } else {
        	  alert('회원 정보가 등록되지 않았습니다.');
          }
        },
        error: function(jqXHR){
        	alert(jqXHR.responseText + '(예외코드 ' + jqXHR.status + ')');
          }
        })
  	  })
    }
  
  // 전역변수 (모든 함수에서 사용할 수 있는 변수)
  var page = 1;
  
  // 회원 목록
  function fnMemberList(){
        $.ajax({
          // 요청
          type: 'get',
          url: '${contextPath}/members/page/' + page, // 첫 실행에서 숫자 1을 보낸다.
          // 응답
          dataType: 'json',
          success: function(resData){
            // 회원 목록을 테이블로 만들기
            $('#member_list').empty();
            $.each(resData.memberList, function(i, member){
              var tr = '<tr>';
              tr += '<td><input type="checkbox" class="chk_one" value="'+member.memberNo+'"></td>';
              tr += '<td>'+member.id+'</td>';
              tr += '<td>'+member.name+'</td>';
              tr += '<td>'+member.gender+'</td>';
              tr += '<td>'+member.address+'</td>';
              tr += '<td><button type="button" class="btn_detail" data-member_no="'+member.memberNo+'">조회</button></td>';
              tr += '</tr>';	     				// ( ↑ 각 회원 조회 버튼을 누르면 해당 회원의 member_no 를 가져온다.) 클릭한 버튼디테일에 data 속성에 값이 들어있다.
              $('#member_list').append(tr);
            })
            // 페이징
            $('#paging').html(resData.paging);
          }
        })
  }
  
  // 페이지를 바꿀때마다 호출되는 fnAjaxPaging 함수
  function fnAjaxPaging(p){
        page = p;        // 페이지 번호를 바꾼다.
        fnMemberList();  // 새로운 목록을 가져온다.
  }
  
  // 회원 정보 상세 조회하기
  function fnMemberDetail(){
	  $(document).on('click', '.btn_detail', function(){	// 이벤트는 다 document 방식으로 하면 다 된다.
		$.ajax({
		  // 요청
		  type: 'get',
		  url: '${contextPath}/members/' + $(this).data('member_no'),
		  // 응답
		  dataType: 'json',
		  success: function(resData){
			     console.log(resData);
      			var member = resData.member;
      			if(!member){
      			  alert('회원 정보를 조회할 수 없습니다.');
      			} else {
      			  $('#memberNo').val(member.memberNo); 	// hidden으로 숨어 있지만 번호는 채워진다.
      			  $('#id').val(member.id).prop('disabled', true);
      			  $('#name').val(member.name);
      			  $(':radio[value=' + member.gender + ']').prop('checked', true);	// value 가 해당 성별인 radio 칸에 체크할 것.
      			  $('#address').val(member.address);
      			  $('#btn_register').prop('disabled', true);
      			  $('#btn_modify').prop('disabled', false);
				  $('#btn_remove').prop('disabled', false);
      			}
		  }
		})
	  });
  }

  // 회원 정보 수정하기	(rest api에서 수정의 타입은 put 이다.)
  function fnMemberModify(){
	  $('#btn_modify').click(function(){
		  $.ajax({
		  	// 요청
		  	type: 'put',
		  	url: '${contextPath}/members',
		  	contentType: 'application/json',	// ← 자바 측에서 Controller가 json을 인식하는 방법.
		  	data: JSON.stringify({
		  	 memberNo: $('#memberNo').val(),
		  	 name: $('#name').val(),
		  	 gender: $(':radio:checked').val(),
		  	 address: $('#address').val()
		  	}),
		  	// 응답
		  	dataType: 'json',
		  	success: function(resData){
		  	  if(resData.modifyResult === 1){
    		  		 alert('회원 정보가 수정되었습니다.');
    		  		fnMemberList(); // 회원 수정 후에 현재 페이지에 그대로 목록만 갱신해서 나타낸다.
		  	  } else {
		  			 alert('회원 정보가 수정되지 않았습니다.');
  			  }
  			  }
  		  })
  	  })
  }
  
  // 회원 정보 삭제
  function fnRemoveMember(){
	  $('#btn_remove').click(function(){
		  if(!confirm('회원 정보를 삭제할까요?')){
			  return;
		  }
		  $.ajax({
			  // 요청
			  type: 'delete',
			  url: '${contextPath}/members/' + $('#memberNo').val(),
			  // 응답
			  dataType: 'json',
			  success: function(resData){
				  if(resData.removeResult === 1){
					  alert('회원 정보가 삭제되었습니다.');
	          page = 1;
	          fnMemberList();
	          fnInit();
				  } else {
					  alert('회원 정보가 삭제되지 않았습니다.');
				  }
			  }
		  })
	  })
  }
  
	// 회원들의 정보 삭제
	function fnRemoveMembers(){
		$('#btn_remove_list').click(function(){
	     	// 체크된 요소의 value를 배열 arr에 저장하기(push 메소드)   . *push 메소드는 자바스크립트 메소드이다.
			var arr = [];
			var chkOne = $('.chk_one');	// 체크된 회원들의 배열이므로 싹 다 긁어온다.
			$.each(chkOne, function(i, elem){
				if($(elem).is(':checked')){ 		// if에서 많이 쓰는 건 'is' 이다.  ( $(elem).prop('checked') 방법도 있음 )
					arr.push($(elem).val());
	    	    }
	    	})
	    	// 체크된 요소가 없으면 삭제 중지
			  if(arr.length === 0){
				  alert('선택된 회원 정보가 없습니다. 다시 시도하세요.');
				  return;
			  }
			// 선택된 회원 삭제
			  $.ajax({
				  // 요청
				  type: 'delete',
				  url: '${contextPath}/members/' + arr.join(','),
				  // 응답
				  dataType: 'json',
				  success: function(resData){
					  if(resData.removeResult > 0){
						  alert('선택한 회원 정보들이 삭제되었습니다.');
					// 1페이지로 돌아와서 초기화하기
				      page = 1;
	            fnMemberList();
	            fnInit();
					  } else {
						  alert('선택한 회원 정보들이 삭제되지 않았습니다.');
					  }
				  }
			  })
		  })
	  }
  
</script>

</head>
<body>

    <div>
    <h1>회원 관리하기</h1>
    <div>
      <label for="id">아이디</label>
      <input type="text" id="id">
    </div>
    <div>
      <label for="name">이름</label>
      <input type="text" id="name">
    </div>
    <div>
      <input type="radio" id="none" name="gender" value="none" checked>
      <label for="none">선택안함</label>
      <input type="radio" id="man" name="gender" value="man">
      <label for="man">남자</label>
      <input type="radio" id="woman" name="gender" value="woman">
      <label for="woman">여자</label>
    </div>
    <div>
      <label for="address">주소</label>
      <select id="address">
        <option value="">:::선택:::</option>
        <option>서울</option>
        <option>경기</option>
        <option>인천</option>
      </select>
    </div>
    <input type="hidden" id="memberNo">
    </div>
    <div>
      <button type="button" onclick="fnInit()">초기화</button>   <!--인라인 이벤트 : 클릭하면 fnInit()을 동작시켜 주세요. -->
      <button type="button" id="btn_register">등록</button>
      <button type="button" id="btn_modify">수정</button>      
      <button type="button" id="btn_remove">삭제</button>      
    </div>
   </div>
  
  

  <hr>
  
  <div>
    <button type="button" id="btn_remove_list">선택삭제</button>
  </div>
  <div>
    <table border="1">
      <thead>
        <tr>
          <td><input type="checkbox" id="chk_all"><label for="chk_all">전체선택</label></td>
          <td>아이디</td>
          <td>이름</td>
          <td>성별</td>
          <td>주소</td>
          <td></td>
        </tr>
      </thead>
      <tbody id="member_list"></tbody>
      <tfoot>
        <tr>
          <td colspan="6" id="paging"></td> <!-- 옆으로 6칸 합치기 : colspan -->
        </tr>
      </tfoot>
    </table>
  </div>

</body>
</html>