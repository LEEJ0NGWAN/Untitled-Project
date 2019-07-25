 <?php //verify.php
	
    header('content-type: text/html; charset=utf-8'); 
 
    // 데이터베이스 접속 문자열. (db위치, 유저 이름, 비밀번호)
    $connect = mysqli_connect("localhost", "username", "password") or  
        die( "SQL server에 연결할 수 없습니다.");
 
	//캐릭터 셋을 UTF8로 맞춘다. 한글의 깨짐을 예방하는 차원
	mysqli_query($connect,"SET NAMES UTF8");
	mysqli_select_db($connect,"dbname");

    // 세션 시작
    session_start();
    $email = $_REQUEST['userMail'];
	$verifyCode = $_REQUEST['verifyCode'];
 
    $query = "UPDATE Table u 
			  SET u.verifyFlag = 'true' 
		      WHERE u.`e-mail` = '$email'
					AND u.`verifyFlag` = '$verifyCode'";
 
    $result = mysqli_query($connect,$query);

    if(mysqli_affected_rows($connect) == 1)
    {
     echo "YEE! Verified your Email!";
    }
    else
    {
     echo "Invalid Link. Sorry!	:(";
    }
?>