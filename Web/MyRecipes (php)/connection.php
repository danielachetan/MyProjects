<?php
	try {
		$connectionString = "sqlsrv:Server=DESKTOP-EINH3N1\SQLEXPRESS04;Database=Lab10PW";
		$conn = new PDO($connectionString, "", "");
		$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	}
	catch (PDOException $e) {
		die(print_r($e->getMessage()));
	}
	/*
	$serverName = "DESKTOP-EINH3N1\SQLEXPRESS04";
	$connectionOptions = array("Database"=>"Lab10PW");
	$conn = sqlsrv_connect($serverName, $connectionOptions);
	if ($conn === false)
		die(print_r(sqlsrv_errors(), true));
	*/
?>