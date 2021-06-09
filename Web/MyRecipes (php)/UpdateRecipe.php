<!DOCTYPE html>

<html>
	<head>
		<title>MyRecipes</title>
	</head>
	<body>
		<?php
			try {
				include "connection.php";
				$sql = "update Recipes set Author=?, Name=?, Type=?, Ingredients=?, Recipe=?,
						Photo=? where Id=?";
				$params = array($_POST['author'], $_POST['name'], $_POST['type'],
								$_POST['ingredients'], $_POST['recipe'], $_POST['photo'],
								$_POST['id']);
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