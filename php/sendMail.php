<?php

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require "./src/PHPMailer.php";
require "./src/SMTP.php";
require "./src/Exception.php";

$mail = new PHPMailer(true);

try {

	include 'mailBaseSetting.php';
	include 'mailContentSetting.php';
	
	// 메일 전송
	$mail -> send();

	echo "송신 완료";

} catch (Exception $e) {
	echo "송신 에러. Mailer Error : ", $mail -> ErrorInfo;
}
?>