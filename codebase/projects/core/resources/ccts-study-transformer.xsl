<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:c3pr="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <xsl:output method="xml" indent="no"   />
    <xsl:template match="/c3pr:study">
        <study xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:attribute name="gridId">
                <xsl:value-of select="@gridId"/>
            </xsl:attribute>
            <xsl:copy-of select="./c3pr:blindedIndicator"/>
            <xsl:copy-of select="./c3pr:multiInstitutionIndicator"/>
            <xsl:copy-of select="./c3pr:randomizedIndicator"/>
            <xsl:copy-of select="./c3pr:shortTitleText"/>
            <xsl:copy-of select="./c3pr:longTitleText"/>
            <xsl:copy-of select="./c3pr:descriptionText"/>
            <xsl:copy-of select="./c3pr:precisText"/>
            <xsl:copy-of select="./c3pr:phaseCode"/>
            <xsl:choose>
                <xsl:when test="c3pr:coordinatingCenterStudyStatus='OPEN'">
                    <coordinatingCenterStudyStatus xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">ACTIVE</coordinatingCenterStudyStatus>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:copy-of select="./c3pr:coordinatingCenterStudyStatus"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:copy-of select="./c3pr:type"/>
            <xsl:copy-of select="./c3pr:targetAccrualNumber"/>
            <xsl:apply-templates select="./c3pr:identifier"/>
            <xsl:apply-templates select="./c3pr:epoch" />
            <xsl:apply-templates select="./c3pr:studyOrganization" />
        </study>
    </xsl:template>
    <xsl:template match="c3pr:identifier">
        <identifier xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:attribute name="xsi:type">
                <xsl:value-of select="@xsi:type"/>
            </xsl:attribute>
            <xsl:copy-of select="./c3pr:type"/>
            <xsl:copy-of select="./c3pr:value"/>
            <xsl:copy-of select="./c3pr:primaryIndicator"/>
            <xsl:choose>
                <xsl:when test="@xsi:type='OrganizationAssignedIdentifierType'">
                    <xsl:apply-templates select="./c3pr:healthcareSite"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:copy-of select="./c3pr:systemName"/>        
                </xsl:otherwise>
            </xsl:choose>
        </identifier>
    </xsl:template>
    <xsl:template match="c3pr:epoch">
        <epoch xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain" xsi:type="TreatmentEpochType">
            <xsl:attribute name="gridId">
                <xsl:value-of select="@gridId"/>
            </xsl:attribute>
            <xsl:copy-of select="./c3pr:name"/>
            <xsl:copy-of select="./c3pr:descriptionText"/>
            <xsl:copy-of select="./c3pr:arm"/>
        </epoch>
    </xsl:template>
    <xsl:template match="c3pr:studyOrganization">
        <studyOrganization xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:attribute name="xsi:type">
                <xsl:value-of select="@xsi:type"/>
            </xsl:attribute>
            <xsl:apply-templates select="c3pr:healthcareSite"/>
            <xsl:apply-templates select="c3pr:studyIvestigator"/>
            <xsl:if test="@xsi:type='StudySiteType'">
                <xsl:copy-of select="./c3pr:name"/>
                <xsl:copy-of select="./c3pr:siteStudyStatus"/>
            </xsl:if>
        </studyOrganization>
    </xsl:template>
    <xsl:template match="c3pr:healthcareSite">
        <healthcareSite xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:copy-of select="./c3pr:name"/>
            <xsl:copy-of select="./c3pr:descriptionText"/>
            <xsl:copy-of select="./c3pr:address"/>
            <nciInstituteCode>
                <xsl:value-of select="./c3pr:identifier[@xsi:type='OrganizationAssignedIdentifierType' and c3pr:type='CTEP']/c3pr:value"/>
            </nciInstituteCode>
        </healthcareSite>
    </xsl:template>
    <xsl:template match="c3pr:studyIvestigator">
        <studyInvestigator xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:copy>
                <xsl:copy-of select="@*"/>
                <xsl:apply-templates/>
            </xsl:copy>
        </studyInvestigator>
    </xsl:template>
</xsl:stylesheet>