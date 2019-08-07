<?php
	include 'dbLogin.php';
	session_start();
	include 'postUserInfo.php';
	$mailFlag = 0;
	$verifyCode = md5( rand(0,1000) );
	$query = "INSERT INTO Userinfo(`e-mail`,`pw`,`verifyFlag`) 
			  VALUES ('$email','$password','$verifyCode')";
			  
	//변수 connect가 갖고 있는 호스트 연결로 변수 query가 갖고 있는 쿼리를 보냄
	//result는 선택된 호스트의 DB로 보낸 쿼리의 결과를 저장
	$result = mysqli_query($connect,$query); 
	echo mysqli_errno($connect) . "\n" ;
	if($result){
		include 'postForSendingMail.php';
		include 'sendMail.php';
	}
?>