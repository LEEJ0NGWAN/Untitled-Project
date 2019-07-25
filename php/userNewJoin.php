<?php
	header('content-type: text/html; charset=utf-8'); //전달받아오는 컨텐츠의 charset을 UTF-8로 설정
	//변수 connect는 어떤 호스트로 연결하는 정보를 가지되 실패 시 종료
	$connect = mysqli_connect("localhost", "username", "password") or
	die("couldn't connect SQL server");
	
	//캐릭터 셋을 UTF8로 맞춘다. 한글의 깨짐을 예방하는 차원
	mysqli_query($connect,"SET NAMES UTF8");
	mysqli_select_db($connect,"dbname");
	session_start();
	$email = $_POST['userMail'];
	$password = $_POST['userPW'];
	$verifyCode = md5( rand(0,1000) );
	$query = "INSERT INTO Table(`e-mail`,`pw`,`verifyFlag`) 
			  VALUES ('$email','$password','$verifyCode')";
			  
	//변수 connect가 갖고 있는 호스트 연결로 변수 query가 갖고 있는 쿼리를 보냄
	//result는 선택된 호스트의 DB로 보낸 쿼리의 결과를 저장
	$result = mysqli_query($connect,$query); 
	echo mysqli_errno($connect) . "\n" ;
	if(mysqli_errno($connect) == 0){
		$_POST['address'] = $email;
		$_POST['verifyCode'] = $verifyCode;
		include 'sendMail.php';
	}
?>