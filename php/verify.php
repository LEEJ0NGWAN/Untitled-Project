 <?php
	include 'dbLogin.php';
    session_start();
	include 'postUserInfo.php';
	
    $query = "UPDATE Userinfo u 
			  SET u.`verifyFlag` = 'true' 
		      WHERE u.`e-mail` = '$email'
					AND u.`verifyFlag` = '$verifyCode'";
 
    mysqli_query($connect,$query);
    if(mysqli_affected_rows($connect) == 1)
		echo "와우! 메일 인증을 완료했습니다!";
    else
		echo "유효한 링크가 아니군요. 죄송합니다! :(";
?>