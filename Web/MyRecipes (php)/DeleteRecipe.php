<!DOCTYPE html>

<html>
	<head>
		<title>MyRecipes</title>
	</head>
	<body>
		<?php
			try {
				include "connection.php";
				$sql = "delete from Recipes where Id=?";
				$params = array($_POST['id']);
				$stmt = $conn->prepare($sql);
				$stmt->execute($params);
				header("Location: MyRecipes.html");
			} 
			catch (Exception $e) {
				die(print_r($e->getMessage()));
			}
		?>
	</body>
</html>