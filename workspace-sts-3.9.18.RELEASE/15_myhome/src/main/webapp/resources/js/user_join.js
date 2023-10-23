/**
 * 회원 가입 페이지
 */

/* 함수 호출 */
$(() => {
  fnCheckEmail();
  fnJoin();
  fnCheckPassword();
  fnCheckPassword2();
})


/* 전역변수 선언 */
var emailPassed = false;
var pwPassed = false;


/* 함수 정의 */

const getContextPath = () => {
  let begin = location.href.indexOf(location.host) + location.host.length;
  let end = location.href.indexOf('/', begin + 1);
  return location.href.substring(begin, end);
}

const fnCheckEmail = () => {
  
  $('#btn_get_code').click(() => {
    
    let email = $('#email').val();

    // 연속된 ajax() 함수 호출의 실행 순서를 보장하는 JavaScript 객체 Promise
    new Promise((resolve, reject) => {
      
      // 성공했다면 resolve() 함수 호출 -> then() 메소드에 정의된 화살표 함수 호출
      // 실패했다면 reject() 함수 호출 -> catch() 메소드에 정의된 화살표 함수 호출
      
      // 1. 정규식 검사
      let regEmail = /^[A-Za-z0-9-_]+@[A-Za-z0-9]{2,}([.][A-Za-z]{2,6}){1,2}$/;
      if(!regEmail.test(email)){
        reject(1);
        return;
      }
      
      // 2. 이메일 중복 체크
      $.ajax({
        // 요청
        type: 'get',
        url: getContextPath() + '/user/checkEmail.do',
        data: 'email=' + email,
        // 응답
        dataType: 'json',
        success: (resData) => {  // resData === {"enableEmail": true}
          if(resData.enableEmail){
            resolve();
          } else {
            reject(2);
          }
        }
      })
      
    }).then(() => {
      
      // 3. 인증코드 전송
      $.ajax({
        // 요청
        type: 'get',
        url: getContextPath() + '/user/sendCode.do',
        data: 'email=' + email,
        // 응답
        dataType: 'json',
        success: (resData) => {  // resData === {"code": "6자리코드"}
          alert(email + "로 인증코드를 전송했습니다.");
          $('#code').prop('disabled', false);
          $('#btn_verify_code').prop('disabled', false);
          $('#btn_verify_code').click(() => {
            emailPassed = $('#code').val() === resData.code;
            if(emailPassed){
              alert('이메일이 인증되었습니다.');
            } else {
              alert('이메일 인증이 실패했습니다.');
            }
          })
        }
      })
      
    }).catch((state) => {
      emailPassed = false;
      switch(state){
      case 1: $('#msg_email').text('이메일 형식이 올바르지 않습니다.'); break;
      case 2: $('#msg_email').text('이미 가입한 이메일입니다. 다른 이메일을 입력해 주세요.'); break;
      }
    })
  })
  
}

const fnCheckPassword = () => {
  // 비밀번호 : 8~20자, 영문,숫자,특수문자, 2가지 이상 포함
  let validPwCount = /[A-Z]/.test(pw)          // 대문자가 있으면   true
                   + /[a-z]/.test(pw)          // 소문자가 있으면   true
                   + /[0-9]/.test(pw)          // 숫자가 있으면     true
                   + /[^A-Za-z0-9]/.test(pw);  // 특수문자가 있으면 true
  $('#pw').keyup((ev) => {
    let pw = $(ev.target).val();
    pwPassed = pw.length >= 8 && pw.length <= 20 && validPwCount >= 2;
    if(pwPassed){
      $('#msg_pw').text('사용 가능한 비밀번호입니다.');
    } else {
      $('#msg_pw').text('비밀번호는 8~20자, 영문/숫자/특수문자를 2가지 이상 포함해야 합니다.');       
    }
  })
}

const fnJoin = () => {
  $('#frm_join').submit((ev) => {
    if(!emailPassed){
      alert('이메일을 인증 받아야 합니다.');
      ev.preventDefault();
      return;
    } else if(!pwPassed){
      alert('비밀번호를 확인하세요.');
      ev.preventDefault();
      return;
    }
  })
}