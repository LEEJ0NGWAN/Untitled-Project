<?php
	header('content-type: text/html; charset=utf-8'); //전달받아오는 컨텐츠의 charset을 UTF-8로 설정
	//변수 connect는 어떤 호스트로 연결하는 정보를 가지되 실패 시 종료
	$connect = mysqli_connect("hostName", "userName", "password") or
	die("SQL server에 접속하지 못했습니다.");
	//캐릭터 셋을 UTF8로 맞춘다. 한글의 깨짐을 예방하는 차원
	mysqli_query($connect,"SET NAMES UTF8");
	mysqli_select_db($connect,"dbName");
?>