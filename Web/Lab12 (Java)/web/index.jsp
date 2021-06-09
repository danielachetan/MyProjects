<%--
  Created by IntelliJ IDEA.
  User: danie
  Date: 5/18/2020
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lab12</title>
</head>
<body>
<form action="Servlet1" method="post" enctype="multipart/form-data">
    <label>Select a file: </label>
    <input type="file" name="myfile" id="myfile">
    <br>
    <input type="submit" value="Upload">
</form>
</body>
</html>
