function displayImage() {
	document.getElementById("disp").src = this.src;
}

function show() {
	document.getElementById("autumn").addEventListener("mouseover", displayImage);
	document.getElementById("winter").addEventListener("mouseover", displayImage);
	document.getElementById("spring").addEventListener("mouseover", displayImage);
	document.getElementById("summer").addEventListener("mouseover", displayImage);
}

window.onload = show;