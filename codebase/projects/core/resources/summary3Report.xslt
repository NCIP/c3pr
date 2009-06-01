<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" />
	<xsl:attribute-set name="sub-head">
		<xsl:attribute name="font-family">arial</xsl:attribute>
		<xsl:attribute name="font-size">10pt</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="text-decoration">underline</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="label">
		<xsl:attribute name="font-family">arial</xsl:attribute>
		<xsl:attribute name="font-size">8pt</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="normal">
		<xsl:attribute name="font-family">arial</xsl:attribute>
		<xsl:attribute name="font-size">8pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="tr-height-1">
		<xsl:attribute name="height">4mm</xsl:attribute>
	</xsl:attribute-set>
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4"
					margin-left="0.75in" margin-top="1in" margin-right="1.25in">
					<fo:region-body margin-top="1.0in" margin-bottom="0.75in"/>
					<fo:region-before extent="1in" />
					<fo:region-after extent="0.2in" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="A4">
				<fo:static-content flow-name="xsl-region-after">
					<fo:block font-size="8pt" font-family="arial" space-before="3mm"
						text-align-last="right">
						Page
						<fo:page-number />
						of
						<fo:page-number-citation ref-id="content_terminator" />
					</fo:block>
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-before">
					<fo:table width="100%" >
						<fo:table-column column-width="12%" />
						<fo:table-column column-width="70%" />
						<fo:table-column column-width="18%" />
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block font-size="10pt" font-family="arial"
										text-align-last="left"> Summary 3: Reportable Patients/Participation
										in Therapeutic Protocols</fo:block>
									<fo:block space-before="5mm" font-size="12pt"
										font-family="arial" text-align-last="center" font-weight="bold"
										display-align="center">
										<xsl:value-of select="summary3Report/reportingOrganization/name" />
									</fo:block>
									<fo:block font-size="12pt" font-family="arial"
										text-align-last="center" font-weight="bold" display-align="center">
										Reporting Period
										<xsl:call-template name="standard_date">
											<xsl:with-param name="date"
												select="summary3Report/startDate" />
										</xsl:call-template>
										-
										<xsl:call-template name="standard_date">
											<xsl:with-param name="date" select="summary3Report/endDate" />
										</xsl:call-template>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block font-size="10pt" font-family="arial"
										text-align-last="left"> 2P30CA654321-50</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:table>
						<fo:table-column column-width="55%" />
						<fo:table-column column-width="15%" />
						<fo:table-column column-width="30%" />
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell border="0.5pt solid black">
									<fo:table>
										<fo:table-column column-width="100%" />
										<fo:table-body>
											<fo:table-row height="6mm">
												<fo:table-cell border="0.5pt solid black" padding-start="5pt"
													height="100%">
													<fo:block font-size="11pt" font-family="arial"
														text-align-last="left"> 
														<xsl:value-of select="summary3Report/reportingSource" />
														</fo:block>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row height="8mm">
												<fo:table-cell border="0.5pt solid black "  padding-start="5pt"
													height="100%">
													<fo:block font-size="11pt" font-family="arial"
														font-weight="bold" text-align-last="left"> Disease Site
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</fo:table-body>
									</fo:table>
								</fo:table-cell>
								<fo:table-cell border="0.5pt solid black" padding-start="5pt">
									<fo:block font-size="11pt" font-family="arial"
										text-align-last="left"> Newly Registered Patients</fo:block>
								</fo:table-cell>
								<fo:table-cell border="0.5pt solid black" padding-start="5pt">
									<fo:block font-size="11pt" font-family="arial"
										text-align-last="left"> Total patients newly</fo:block>
									<fo:block font-size="11pt" font-family="arial"
										text-align-last="left">
										enrolled in
										<fo:inline font-weight="bold">therapeutic
										</fo:inline>
									</fo:block>
									<fo:block font-size="11pt">protocols</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:for-each select="summary3Report/reportData">
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black" padding-start="5pt">
										<fo:block font-size="11pt" font-family="arial"
											text-align-last="left">
											<fo:inline xsl:use-attribute-sets="normal"
												font-size="11">
												<xsl:value-of select="key/@name" />
											</fo:inline>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell border="0.5pt solid black" padding-start="5pt">
										<fo:block font-size="11pt" font-family="arial"
											text-align-last="left">
											<fo:inline xsl:use-attribute-sets="normal"
												font-size="11">
												<xsl:value-of select="value[1]/value" />
											</fo:inline>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell border="0.5pt solid black" padding-start="5pt">
										<fo:block font-size="11pt" font-family="arial"
											text-align-last="left">
											<fo:inline xsl:use-attribute-sets="normal"
												font-size="11">
												<xsl:value-of select="value[2]/value" />
											</fo:inline>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</xsl:for-each>
						</fo:table-body>
					</fo:table>
					<fo:block id="content_terminator" />
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="summary3Report/startDate">
		Found a learner!
	</xsl:template>
	<xsl:template name="standard_date">
		<xsl:param name="date" />
		<xsl:if test="$date">
			<!-- Month -->
			<xsl:value-of select="substring($date, 6, 2)" />
			<xsl:text>/</xsl:text>
			<!-- Day -->
			<xsl:value-of select="substring($date, 9, 2)" />
			<xsl:text>/</xsl:text>
			<!-- Year -->
			<xsl:value-of select="substring($date, 1, 4)" />
		</xsl:if>
	</xsl:template>
	<xsl:template name="left-trim">
		<xsl:param name="s" />
		<xsl:choose>
			<xsl:when test="substring($s, 1, 1) = ''">
				<xsl:value-of select="$s" />
			</xsl:when>
			<xsl:when test="normalize-space(substring($s, 1, 1)) = ''">
				<xsl:call-template name="left-trim">
					<xsl:with-param name="s" select="substring($s, 2)" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$s" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="right-trim">
		<xsl:param name="s" />
		<xsl:choose>
			<xsl:when test="substring($s, 1, 1) = ''">
				<xsl:value-of select="$s" />
			</xsl:when>
			<xsl:when test="normalize-space(substring($s, string-length($s))) = ''">
				<xsl:call-template name="right-trim">
					<xsl:with-param name="s"
						select="substring($s, 1, string-length($s) - 1)" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$s" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="trim">
		<xsl:param name="s" />
		<xsl:call-template name="right-trim">
			<xsl:with-param name="s">
				<xsl:call-template name="left-trim">
					<xsl:with-param name="s" select="$s" />
				</xsl:call-template>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="sqrt">
		<xsl:param name="num" select="0" />  <!-- The number you want to find the square root of -->
		<xsl:param name="try" select="1" />  <!-- The current 'try'.  This is used internally. -->
		<xsl:param name="iter" select="1" />
		<!--
			The current iteration, checked against maxiter to limit loop count
		-->
		<xsl:param name="maxiter" select="10" />  <!-- Set this up to insure against infinite loops -->
		<!--
			This template was written by Nate Austin using Sir Isaac Newton's
			method of finding roots
		-->
		<xsl:choose>
			<xsl:when test="$try * $try = $num or $iter &gt; $maxiter">
				<xsl:value-of select="$try" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="sqrt">
					<xsl:with-param name="num" select="$num" />
					<xsl:with-param name="try"
						select="$try - (($try * $try - $num) div (2 * $try))" />
					<xsl:with-param name="iter" select="$iter + 1" />
					<xsl:with-param name="maxiter" select="$maxiter" />
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>