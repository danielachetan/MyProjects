<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html>
<head>
	<title>lab5</title>
	<link rel="stylesheet" href="lab5.css"/>
</head>
<body>
	<br/><br/>
	<table border="2" align="center">
		<tr>
			<th>Type</th>
			<th>Title</th>
			<th>Author</th>
			<th>Publisher</th>
			<th>Publishing Year</th>
			<th>Price</th>
			<th>PageNR</th>
		</tr>
		<xsl:for-each select="entries/entry">
		<xsl:sort select=" author" />
		<tr>
			<td class="typeClass"><xsl:value-of select="type"/></td>
			<td class="titleClass"><xsl:value-of select="title"/></td>
			<td class="authorClass"><xsl:value-of select="author"/></td>
			<td class="publisherClass"><xsl:value-of select="publisher"/></td>
			<td class="publishingYearClass"><xsl:value-of select="publishingYear"/></td>
			<td class="priceClass"><xsl:value-of select="price"/></td>
			<td class="page_nrClass"><xsl:value-of select="page_nr"/></td>
		</tr>
		</xsl:for-each>
	</table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>
