<?php
	include 'dbLogin.php';
	session_start();
	include 'postUserInfo.php';
	
	$query = "SELECT IF(STRCMP(u.`pw`,'$password'),0,1) `login_flag`, u.`verifyFlag`, u.`name`
			  FROM Userinfo u
			  WHERE u.`e-mail` = '$email'";
	$result = mysqli_query($connect,$query);
	
	if($result){
		$row = mysqli_fetch_array($result);
		if(mysqli_affected_rows($connect) == 0) //no user
			echo "noUser";
		else if($row['login_flag'] == 0) //not Correct PW
			echo "notCorrectPW";
		else if($row['verifyFlag'] != 'true') //not verified
			echo "notVerified";
		else if($row['name'] == NULL) //not initialized
			echo "notInitialized";
		else{
			echo "loginSuccess\n";
			echo $row['name'];
		}
		echo "\ndummy";
	}
	else
		echo mysqli_errno($connect);
?>