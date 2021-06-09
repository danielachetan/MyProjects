<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >

    <xsl:template match="/">

        <html>
            <head>

                <title>Title</title>
                <link rel="stylesheet" href="cssRules.css"/>
            </head>
            <body>
                <h1 align="center">Students' Basic Details</h1>
                <table border="3" align="center">
                    <tr>
                        <th>Type</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Publisher</th>
                        <th>Publishing Year</th>
                        <th>Price</th>
                    </tr>
                    <xsl:for-each select="entries/entry[publishingYear&lt;'2007' and publishingYear&gt;'2001']">
                        <xsl:sort select=" publishingYear" />

                        <tr>
                            <td class="typeClass" >
                                <xsl:value-of select="type"/>
                            </td>
                            <td class="titelClass">
                                <xsl:value-of select="title"/>
                            </td>
                            <td class="authorClass">
                                <xsl:value-of select="author"/>
                            </td>
                            <td class="publisherClass">
                                <xsl:value-of select="publisher"/>
                            </td>
                            <td class="publishingYearClass">
                                <xsl:value-of select="publishingYear"/>
                            </td>
                            <td class="priceClass">
                                <xsl:value-of select="price"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>