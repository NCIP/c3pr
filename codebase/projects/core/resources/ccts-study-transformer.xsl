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
            <xsl:if test="@externalId">
                <identifier xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain" xsi:type="SystemAssignedIdentifierType">
                    <type xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">COPPA Identifier</type>
                    <value xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain"><xsl:value-of select="@externalId"/></value>
                    <systemName xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">COPPA</systemName>
                </identifier>
            </xsl:if>
            <xsl:apply-templates select="./c3pr:epoch" />
            <xsl:apply-templates select="./c3pr:studyOrganization" />
        </study>
    </xsl:template>
    <xsl:template match="c3pr:identifier">
        <identifier xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:attribute name="xsi:type">
                <xsl:value-of select="@xsi:type"/>
            </xsl:attribute>
            <xsl:choose>
                <xsl:when test="./c3pr:type='COORDINATING_CENTER_IDENTIFIER'">
                    <type xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">Coordinating Center Identifier</type>
                </xsl:when>
                <xsl:when test="./c3pr:type='PROTOCOL_AUTHORITY_IDENTIFIER'">
                    <type xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">Protocol Authority Identifier</type>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:copy-of select="./c3pr:type"/>
                </xsl:otherwise>
            </xsl:choose>
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
            <xsl:apply-templates select="c3pr:studyInvestigator"/>
            <xsl:if test="@xsi:type='StudySiteType'">
                <xsl:copy-of select="./c3pr:name"/>
                <xsl:copy-of select="./c3pr:siteStudyStatus"/>
            </xsl:if>
        </studyOrganization>
    </xsl:template>
    <xsl:template match="c3pr:healthcareSite">
        <healthcareSite xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:if test="./c3pr:externalId">
                <xsl:attribute name="gridId">
                    <xsl:value-of select="./c3pr:externalId"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:copy-of select="./c3pr:name"/>
            <xsl:copy-of select="./c3pr:descriptionText"/>
            <xsl:copy-of select="./c3pr:address"/>
            <nciInstituteCode>
                <xsl:value-of select="./c3pr:identifier[@xsi:type='OrganizationAssignedIdentifierType' and c3pr:type='CTEP']/c3pr:value"/>
            </nciInstituteCode>
        </healthcareSite>
    </xsl:template>
    <xsl:template match="c3pr:studyInvestigator">
        <studyInvestigator xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:copy-of select="./c3pr:roleCode"/>
            <xsl:copy-of select="./c3pr:statusCode"/>
            <xsl:copy-of select="./c3pr:startDate"/>
            <xsl:copy-of select="./c3pr:endDate"/>
            <xsl:apply-templates select="./c3pr:healthcareSiteInvestigator"/>
        </studyInvestigator>
    </xsl:template>
    <xsl:template match="c3pr:healthcareSiteInvestigator">
        <healthcareSiteInvestigator xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:apply-templates select="./c3pr:investigator"/>
        </healthcareSiteInvestigator>
    </xsl:template>
    <xsl:template match="c3pr:investigator">
        <investigator xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
        	<nciIdentifier>
            	<xsl:value-of select="./c3pr:assignedIdentifier"/>
            </nciIdentifier>
            <xsl:copy-of select="./c3pr:firstName"/>
            <xsl:copy-of select="./c3pr:lastName"/>
            <xsl:if test="./c3pr:externalId">
                <xsl:copy-of select="./c3pr:externalId"/>
            </xsl:if>
            <xsl:copy-of select="./c3pr:phoneNumber"/>
            <xsl:copy-of select="./c3pr:faxNumber"/>
        </investigator>
    </xsl:template>
</xsl:stylesheet>