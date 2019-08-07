<?php
	// 서버세팅
	//디버깅 설정을 0 으로 하면 아무런 메시지가 출력되지 않습니다

	$mail -> SMTPDebug = 2; // 디버깅 설정
	$mail -> isSMTP(); // SMTP 사용 설정

	 
	// 지메일일 경우 smtp.gmail.com, 네이버일 경우 smtp.naver.com
	$mail -> Host = "smtpServerName";               // smtp 서버
	$mail -> SMTPAuth = true;                         // SMTP 인증을 사용함
	$mail -> Username = "sender@abc.com";    // 메일 계정 (지메일일경우 지메일 계정)
	$mail -> Password = "password";                  // 메일 비밀번호
	$mail -> SMTPSecure = "ssl";                       // SSL을 사용함
	$mail -> Port = 465;                                  // email 보낼때 사용할 포트를 지정
	$mail -> CharSet = "utf-8"; // 문자셋 인코딩


	// 보내는 메일
	$mail -> setFrom("sender@abc.com", "senderName");

	$userMailAddress = $_POST['address'];
	$newPassword = $_POST['newPW'];
	$verifyCode = $_POST['verifyCode'];
	$hostAddr = $_POST['hostAddr'];
	$mailFlag = $_POST['mailFlag'];
	
	// 받는 메일
	$mail -> addAddress($userMailAddress);
	
	// Gmail로 메일을 발송하기 위해서는 CA인증이 필요하다.
	// CA 인증을 받지 못한 경우에는 아래 설정하여 인증체크를 해지하여야 한다.
	$mail -> SMTPOptions = array(
		"ssl" => array(
		"verify_peer" => false,
		"verify_peer_name" => false,
		"allow_self_signed" => true));
		
	// HTML 태그 사용 여부
	$mail -> isHTML(true); 
?>