<?php
	include 'dbLogin.php';
	session_start();
	include 'postUserInfo.php';
	
	$query = "SELECT u.`id`
			  FROM Userinfo u
			  WHERE u.`name` = '$name'";
	$result = mysqli_query($connect,$query);
	
	if($result){
		$row = mysqli_fetch_array($result);
		if(mysqli_affected_rows($connect) == 0) //중복 닉네임 없음
			echo "1";
		else	//중복 닉네임 존재
			echo "0"; 
	}
	else
		echo mysqli_errno($connect);
?>