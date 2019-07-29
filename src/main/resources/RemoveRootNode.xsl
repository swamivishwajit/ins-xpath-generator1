<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes" />
	<xsl:strip-space elements="*" />

	<xsl:template match="@*/node()">
		<xsl:copy>
			<xsl:apply-templates select="@*/node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template
		match="Column[@SourceColumn='status']|Status" />
	<xsl:template
		match="Column[@SourceColumn='ProcessSummary']|ProcessSummary" />
	<xsl:template
		match="Column[@SourceColumn='ProcessDetails']|ProcessDetails" />


	<xsl:template match="FormServerResponse/ReturnPayload">
		<xsl:copy-of select="@*/node()" />
	</xsl:template>

	<xsl:template match="/FormServerResponse">
		<xsl:copy-of select="@*/node()" />
	</xsl:template>

	<xsl:template match="/*">
		<xsl:apply-templates select="node()" />
	</xsl:template>

</xsl:stylesheet>