<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:c3pr="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
    <xsl:output method="xml" indent="no"  />
    <xsl:template match="*">
        <xsl:copy>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="c3pr:studyOrganization/c3pr:siteStudyStatus"></xsl:template>
    <xsl:template match="c3pr:epoch">
        <xsl:choose>
            <xsl:when test="./c3pr:treatmentIndicator = 'true'">
                <epoch xsi:type="TreatmentEpochType" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
                    <xsl:attribute name="gridId">
                        <xsl:value-of select="@gridId"/>
                    </xsl:attribute>
                    <xsl:copy-of select="./c3pr:name"/>
                    <xsl:copy-of select="./c3pr:descriptionText"/>
                    <xsl:copy-of select="./c3pr:arm"/>
                    <xsl:copy-of select="./c3pr:eligibilityCriteria"/>
                    <xsl:copy-of select="./c3pr:stratificationCriteria"/>
                </epoch>
            </xsl:when>
            <xsl:when test="./c3pr:treatmentIndicator = 'false'">
                <epoch xsi:type="NonTreatmentEpochType"
                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
                    <xsl:attribute name="gridId">
                        <xsl:value-of select="@gridId"/>
                    </xsl:attribute>
                    <xsl:copy-of select="./c3pr:name"/>
                    <xsl:copy-of select="./c3pr:descriptionText"/>
                    <xsl:copy-of select="./c3pr:accrualCeiling"/>
                    <xsl:copy-of select="./c3pr:accrualIndicator"/>
                    <xsl:copy-of select="./c3pr:reservationIndicator"/>
                    <xsl:copy-of select="./c3pr:enrollmentIndicator"/>
                </epoch>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>