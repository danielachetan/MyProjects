<?php
include "connection.php";
try {
	if (!array_key_exists("q", $_GET) || $_GET['q'] == '') {
		$sql = "select * from Recipes";
		$params = array();
	}
	else {
		$sql = "select * from Recipes where Type=?";
		$params = array($_GET['q']);
	}
	$stmt = $conn->prepare($sql);
	$stmt->execute($params);
	while ($row = $stmt->fetch())
		echo
			"<div class='buttons'>
				<form action='RecipeInputForm.php' method='post' class='update'>
					<button class='button' type='submit' name='id' value='" . $row['Id'] . 
					"'>Update</button>
				</form>
				<br>
				<form action='DeleteRecipe.php' method='post' class='delete'>
					<button class='button' type='submit' name='id' value='" . $row['Id'] . 
					"'>Delete</button>
				</form>
			</div>
			<br>
			<div class='recipeInformation'>
				<div class='author'>" . $row['Author'] . "</div>" .  
				"<br>" . 
				"<div class='name'>" . $row['Name'] . "</div>" .  
				"<br>" . 
				"<div class='type'>" . $row['Type'] . "</div>" .
			"</div>
			<br>" .
			"<div class='photoIngredientsLayout'>
				<img src='" . $row['Photo'] . "' class='photo'>
				<br>
				<div class='ingredientsBlock'>
					<div class='ingredientsTitle'>Ingredients:</div>
					<br>
					<div class='ingredients'>" . $row['Ingredients'] . "</div>
				</div>
			</div>" .  
			"<br>" . 
			"<div class='recipe'>" . $row['Recipe'] . "</div>
			<br><br><hr>";
}
catch (Exception $e) {
	die(print_r($e->getMessage()));
}
?>