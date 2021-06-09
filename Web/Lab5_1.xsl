<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <head>
	<title>Lab5</title>
	<link rel="stylesheet" href="Lab5_2.css"/>
  </head>
  <body>
    <table border="1">
      <tr bgcolor="#9acd32">
        <th>Title</th>
		<th colspan="4">Actors</th>
        <th>Genre</th>
		<th colspan="3">Date of playing</th>
		<th>Duration</th>
      </tr>
      <xsl:for-each select="movies/movie[date-of-playing/year='2020' and 
	  date-of-playing/month='4' and 
	  (date-of-playing/day&gt;'4' and date-of-playing/day&lt;'13')]">
      <tr>
		<xsl:attribute name="class">
			<xsl:value-of select="genre"/>
		</xsl:attribute>
        <td><xsl:value-of select="title"/></td>
		<xsl:for-each select="actors/actor">
			<td><xsl:value-of select="name"/></td>
		</xsl:for-each>
		<td><xsl:value-of select="genre"/></td>
		<td><xsl:value-of select="date-of-playing/day"/></td>
		<td><xsl:value-of select="date-of-playing/month"/></td>
		<td><xsl:value-of select="date-of-playing/year"/></td>
		<td><xsl:value-of select="duration"/></td>
      </tr>
      </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>
