<?php
	include 'dbLogin.php';
	session_start();
	include 'postUserInfo.php';
	$mailFlag = 2;
	$newPassword = generateRandomString();
    $query = "UPDATE Userinfo u 
			  SET u.`pw` = '$newPassword', u.`codeForPWChange` = null
		      WHERE u.`e-mail` = '$email'
					AND u.`codeForPWChange` = '$code'";
			  
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
	
	function generateRandomString($length = 10) {
		$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
		$charactersLength = strlen($characters);
		$randomString = '';
		for ($i = 0; $i < $length; $i++) {
			$randomString .= $characters[rand(0, $charactersLength - 1)];
		}
		return $randomString;
	}
?>