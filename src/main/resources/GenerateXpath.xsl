<xsl:stylesheet version="1.0" xmlns:xsl= "http://www.w3.org/1999/XSL/Transform">
<xsl:output omit-xml-declaration = "yes" indent = 'yes'/>
<xsl:strip-space elements= "*"/>

<xsl:variable name= "vApos">'</xsl:variable>

<xsl:template match="*[@* or not(*)]">
	<xsl:if test="not(*) and current()!=''">
	<xsl:apply-templates select="ancestor-or-self::*" mode="path"/>	
		<xsl:text>&#160;</xsl:text>
		<xsl:text>&#160;</xsl:text>
		<xsl:text>&#160;</xsl:text>
		<xsl:text>&#160;</xsl:text>
		<xsl:copy-of select="./text()"/>
		<xsl:text>&#xA;</xsl:text>	
	</xsl:if>
	<xsl:apply-templates select="@*|*"/>
</xsl:template>

<xsl:template match="*" mode="path">
	<xsl:value-of select="concat('/',name())"/>
	<xsl:variable name="vnumSiblings" select="count(../*[name()=name(current())])"/>
	<xsl:if test ="$vnumSiblings>1">
	<xsl:value-of select="concat('[',count(preceding-sibling::*[name()=name(current())])+1,']')"/>
	</xsl:if>
</xsl:template>

<xsl:template match="text()"/>

<xsl:template match="@*">
<xsl:apply-templates select="../ancestor-or-self::*" mode="path"/>
<xsl:value-of select="concat('[@',name(),'=', $vApos,.,$vApos,']')"/>
<xsl:text>&#xA;</xsl:text>
</xsl:template>

</xsl:stylesheet>