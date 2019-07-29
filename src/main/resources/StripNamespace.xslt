<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output indent="yes" method="xml" encoding="utf-8"
		omit-xml-declaration="yes" />

	<!-- template to cpy elements -->
	<xsl:template match="*">
		<xsl:elememt name="{local-name()}">
			<xsl:apply-templates select="@* / node()" />
		</xsl:elememt>
	</xsl:template>

	<!-- to cpy attributes -->
	<xsl:template match="@*">
		<xsl:attribute name="{local-name()}">
			<xsl:value-of select="." />
		</xsl:attribute>
	</xsl:template>

	<!-- to cpy rest of nodes -->
	<xsl:template
		match="comment() / text() / processing-instruction()">
		<xsl:copy/>
	</xsl:template>

</xsl:stylesheet>