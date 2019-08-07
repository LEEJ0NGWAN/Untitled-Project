<?php
	switch($mailFlag){
		case 0:
			$subject = '회원가입 완료를 위한 인증링크';
			$body = '
		 
		회원가입을 해주셔서 감사합니다!
		회원님의 계정이 무사히 등록 되었으며, 아래에 보내드린 인증링크로 접속 시 인증이 완료됩니다!
		계정 활성화를 위해 다음의 링크로 접속 부탁드립니다:
		' .$hostAddr. 'verify.php?userMail='.$userMailAddress.'&verifyCode='.$verifyCode.'
		 
		';	break;
		
		case 1:
			$subject = '비밀번호 재발급을 위한 인증코드';
			$body = '
		 
		안녕하세요! 비밀번호 재설정을 위한 코드를 보내드립니다!
		다음의 8자리 숫자 코드를 앱의 인증코드란에 기입해주세요!
		CODE : '.$code.'
		 
		';	break;
		
		case 2:
			$subject = '새로운 비밀번호 입니다!';
			$body = '
		 
		짜잔! 새로운 비밀번호입니다!
		로그인 후에 알맞게 비밀번호를 변경해주세요.
		새로운 비밀번호 : '.$newPassword.'
		 
		';
	}
	
	$mail -> Subject = $subject;
	$mail -> Body =  $body;
?>