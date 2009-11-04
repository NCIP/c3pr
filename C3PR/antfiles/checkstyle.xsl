<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:param name="project" />

	<xsl:template match="/">
		<html>
			<head>
				<title>check-style for <xsl:value-of select="$project"/></title>
			</head>
			<body>
				<p><h1>check-style for <xsl:value-of select="$project"/></h1></p>
				<xsl:apply-templates select="checkstyle/file"/>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="checkstyle/file">
		<xsl:if test="count(error)>0">
		<p>
			<table border="1" width="100%">
				<tr><td colspan="3"><big><font color="green"><b><xsl:value-of select="@name" /></b></font></big></td></tr>
				<tr>
					<td width="75%"><b>Message</b></td>
					<td width="10%"><b>Line</b></td>
					<td width="15%"><b>Severity</b></td>
				</tr>
				<xsl:apply-templates select="error"/>
			</table>
		</p>
		</xsl:if>
	</xsl:template>

	<xsl:template match="error">
			<xsl:variable name="color"><xsl:if test="@severity = 'error'">red</xsl:if><xsl:if test="@severity != 'error'">black</xsl:if></xsl:variable>
			<tr>
				<td><font color="{$color}"><xsl:value-of select="@message" /></font></td>
				<td><font color="{$color}"><xsl:value-of select="@line" /></font></td>
				<td><font color="{$color}"><xsl:value-of select="@severity" /></font></td>
			</tr>
	</xsl:template>

</xsl:stylesheet>
