<?php
	header('content-type: text/html; charset=utf-8'); //전달받아오는 컨텐츠의 charset을 UTF-8로 설정
	
	//변수 connect는 어떤 호스트로 연결하는 정보를 가지되 실패 시 종료
	$connect = mysqli_connect("localhost", "username", "password") or
	die("couldn't connect SQL server");
	
	//캐릭터 셋을 UTF8로 맞춘다. 한글의 깨짐을 예방하는 차원
	mysqli_query($connect,"SET NAMES UTF8");
	mysqli_select_db($connect,"dbname");

	session_start();
	$email = $_POST[userMail];
	$password = $_POST[userPW];
	
	$query = "SELECT IF(STRCMP(u.`pw`,'$password'),0,1) `login_flag`
			  FROM table u
			  WHERE u.`e-mail` = '$email'";
	$result = mysqli_query($connect,$query);
	if(result){
		$row = mysqli_fetch_array($reult);
		if(is_null($row['login_flag']))
			echo "no User";
		else
			echo $row[login_flag];
	}
	else
		echo mysqli_errno($connect);
?>