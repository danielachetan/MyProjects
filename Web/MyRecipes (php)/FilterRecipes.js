function filterRecipes(str) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById('text').innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "ShowRecipes.php?q="+str, true);
	xhttp.send();
}