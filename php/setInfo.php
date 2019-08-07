<?php
	include 'dbLogin.php';
	session_start();
	include 'postUserInfo.php';
	
	$changedFlag = false;
    $query = "UPDATE Userinfo u 
			  SET ";
	if($name != "notChanged"){
		$changedFlag = true;
		$query .= "u.`name` = '$name'";
	}
	if($majorType != "notChanged"){
		if(!$changedFlag)
			$changedFlag = true;
		$query .= ", u.`majorType` = '$majorType'";
	}
	if($major != "notChanged"){
		if(!$changedFlag)
			$changedFlag = true;
		$query .= ", u.`major` = '$major'";
	}
	if($interests != "notChanged"){
		if(!$changedFlag)
			$changedFlag = true;
		$query .= ", u.`interests` = '$interests'";
	}
	//변경 사항이 없다면
	if(!$changedFlag)
		echo "-1";
	else{
		$query .= " WHERE u.`e-mail` = '$email'";
		
		//변수 connect가 갖고 있는 호스트 연결로 변수 query가 갖고 있는 쿼리를 보냄
		mysqli_query($connect,$query);
		
		if(mysqli_errno($connect) == 0){
			if(mysqli_affected_rows($connect) == 1)
				echo "0";
			else //couldn't find email
				echo "-2";
		}
		else
			echo mysqli_errno($connect);
	}
?>