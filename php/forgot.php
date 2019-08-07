<?php
	include 'dbLogin.php';
	session_start();
	include 'postUserInfo.php';
	$mailFlag = 1;
	$code = rand(10101010,99990909);		  
	$query = "UPDATE Userinfo u 
			  SET u.`codeForPWChange` = '$code' 
			  WHERE u.`e-mail` = '$email'";
	//변수 connect가 갖고 있는 호스트 연결로 변수 query가 갖고 있는 쿼리를 보냄
	mysqli_query($connect,$query);
	
	if(mysqli_errno($connect) == 0){
		if(mysqli_affected_rows($connect) == 1){
			echo "0\n";
			include 'postForSendingMail.php';
			include 'sendMail.php';
		}
		else
			echo "-1";
	}
	else
		echo mysqli_errno($connect);
?>