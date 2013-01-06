<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:c3pr="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
    <xsl:output method="xml" indent="no"  />
    <xsl:template match="*">
        <xsl:copy>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="c3pr:scheduledEpoch">
        <scheduledEpoch
            xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:type="ScheduledEpochType">
            <xsl:attribute name="gridId">
                <xsl:value-of select="@gridId"/>
            </xsl:attribute>
            <xsl:copy-of select="./c3pr:startDate"/>
            <epoch xsi:type="TreatmentEpochType">
                <xsl:attribute name="gridId">
                    <xsl:value-of select="@gridId"/>
                </xsl:attribute>
                <xsl:copy-of select="./c3pr:epoch/c3pr:name"/>
                <xsl:copy-of select="./c3pr:epoch/c3pr:descriptionText"/>
                <xsl:copy-of select="./c3pr:epoch/c3pr:arm"/>
                <xsl:copy-of select="./c3pr:epoch/c3pr:accrualCeiling"/>
                <xsl:copy-of select="./c3pr:epoch/c3pr:accrualIndicator"/>
                <xsl:copy-of select="./c3pr:epoch/c3pr:reservationIndicator"/>
                <xsl:copy-of select="./c3pr:epoch/c3pr:enrollmentIndicator"/>
            </epoch>
            <xsl:copy-of select="./c3pr:scheduledArm"/>
            <xsl:copy-of select="./c3pr:eligibilityIndicator"/>
        </scheduledEpoch>
    </xsl:template>
</xsl:stylesheet>