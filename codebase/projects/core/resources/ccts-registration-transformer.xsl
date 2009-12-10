<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:c3pr="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <xsl:output method="xml" indent="no"   />
    <xsl:template match="/c3pr:registration">
        <registration xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:attribute name="gridId">
                <xsl:value-of select="@gridId"/>
            </xsl:attribute>
            <xsl:apply-templates select="./c3pr:site" />
            <xsl:apply-templates select="./c3pr:studyIdentifiers" />
            <xsl:apply-templates select="./c3pr:subject"/>
            <xsl:copy-of select="./c3pr:startDate"/>
            <informedConsentFormSignedDate>
                <xsl:value-of select="c3pr:informedConsent[1]/c3pr:informedConsentFormSignedDate"/>
            </informedConsentFormSignedDate>
            <informedConsentVersion>
                <xsl:value-of select="c3pr:informedConsent[1]/c3pr:consent/c3pr:name"/>
            </informedConsentVersion>
            <xsl:apply-templates select="./c3pr:identifier"/>
            <xsl:apply-templates select="./c3pr:scheduledEpoch"/>
            <xsl:copy-of select="./c3pr:offStudyReasonText"/>
            <xsl:copy-of select="./c3pr:offStudyDate"/>
        </registration>
    </xsl:template>
    <xsl:template match="c3pr:site">
        <studySite xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <healthcareSite>
                <xsl:if test="./c3pr:externalId">
                    <xsl:attribute name="gridId">
                        <xsl:value-of select="./c3pr:externalId"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:copy-of select="./c3pr:name"/>
                <xsl:copy-of select="./c3pr:descriptionText"/>
                <xsl:copy-of select="./c3pr:address"/>
                <nciInstituteCode>
                    <xsl:value-of select="c3pr:identifier[@xsi:type='OrganizationAssignedIdentifierType' and c3pr:type='CTEP']/c3pr:value"/>
                </nciInstituteCode>
            </healthcareSite>
        </studySite>
    </xsl:template>
    <xsl:template match="c3pr:studyIdentifiers">
        <studyRef xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:apply-templates select="./c3pr:identifier"/>
        </studyRef>
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
    <xsl:template match="c3pr:subject">
        <participant xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain">
            <xsl:attribute name="gridId">
                <xsl:value-of select="@gridId"/>
            </xsl:attribute>
            <xsl:copy-of select="./c3pr:firstName"/>
            <xsl:copy-of select="./c3pr:lastName"/>
            <xsl:copy-of select="./c3pr:address"/>
            <xsl:copy-of select="./c3pr:administrativeGenderCode"/>
            <xsl:copy-of select="./c3pr:birthDate"/>
            <xsl:copy-of select="./c3pr:ethnicGroupCode"/>
            <xsl:copy-of select="./c3pr:raceCode"/>
            <xsl:apply-templates select="./c3pr:identifier"/>
        </participant>
    </xsl:template>
    <xsl:template match="c3pr:scheduledEpoch">
        <scheduledEpoch
            xmlns="gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain"
            xsi:type="ScheduledTreatmentEpochType">
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
            </epoch>
            <xsl:copy-of select="./c3pr:scheduledArm"/>
            <xsl:copy-of select="./c3pr:eligibilityIndicator"/>
        </scheduledEpoch>
    </xsl:template>
</xsl:stylesheet>