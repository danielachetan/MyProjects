<!DOCTYPE html>

<html>
	<head>
		<title>MyRecipe - Edit Recipe</title>
	</head>
	<body>
		<?php
		try {
			include 'connection.php';
			$sql = "select * from Recipes where Id = ?";
			$params = array($_POST['id']);
			$stmt = $conn->prepare($sql);
			$stmt->execute($params);
			$row = $stmt->fetch();
			echo
			"<form action='UpdateRecipe.php' method='post'>
				<input type='hidden' name='id' value='" . $row['Id'] . "'>
				<label>Author: </label>
				<input type='text' name='author' size='25' required='required' value='" . $row['Author'] . "'>
				<br><br>
				<label>Name: </label>
				<input type='text' name='name' size='50' required='required' value='" . $row['Name'] . "'>
				<br><br>
				<label>Type: </label>
				<input type='text' name='type' size='25' required='required' value='" . $row['Type'] . "'>
				<br><br>
				<label>Ingredients: </label>
				<br>
				<textarea name='ingredients' required='required' rows='10' cols='100'>" . $row['Ingredients'] . "</textarea>
				<br><br>
				<label>Recipe: </label>
				<br>
				<textarea name='recipe' required='required' rows='15' cols='100'>" . $row['Recipe'] . "</textarea>
				<br><br>
				<label>Photo: </label>
				<input type='text' name='photo' required='required' size='100' value='" . $row['Photo'] . "'>
				<br><br>
				<input type='submit' value='Update' name='button'>
			</form>";
		}
		catch (Exception $e) {
			die(print_r($e->getMessage()));
		}
		?>
	</body>
</html>