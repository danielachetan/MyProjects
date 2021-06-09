<!DOCTYPE html>

<html>
	<head>
		<title>MyRecipes - Edit Recipe</title>
	</head>
	<body>
		<form method="post">
			<label>Author: </label>
			<input type="text" name="author" size="25" required="required">
			<br><br>
			<label>Name: </label>
			<input type="text" name="name" size="50" required="required">
			<br><br>
			<label>Type: </label>
			<input type="text" name="type" size="25" required="required">
			<br><br>
			<label>Ingredients: </label>
			<br>
			<textarea name="ingredients" rows="10" cols="100" required="required"></textarea>
			<br><br>
			<label>Recipe: </label>
			<br>
			<textarea name="recipe" rows="15" cols="100" required="required"></textarea>
			<br><br>
			<label>Photo: </label>
			<input type="text" name="photo" required="required" size="100">
			<br><br>
			<input type="submit" value="Upload" name="button">
		</form>
		<?php
		if (array_key_exists('button', $_POST))
			insert();
		function insert() {
			try {
				include "connection.php";
				$sql = "insert into Recipes (Author, Name, Type, Ingredients, Recipe, Photo)
					values (?, ?, ?, ?, ?, ?)";
				$params = array($_POST['author'], $_POST['name'], $_POST['type'],
								$_POST['ingredients'], $_POST['recipe'], $_POST['photo']);
				$stmt = $conn->prepare($sql);
				$stmt->execute($params);
				header("Location: MyRecipes.html");
			}
			catch (Exception $e) {
				die(print_r($e->getMessage()));
			}
		}
		?>
	</body>
</html>