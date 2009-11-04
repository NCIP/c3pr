package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum OrganizationIdentifierTypeEnum implements CodedEnum<String> {
	CTEP("CTEP Identifier"), NCI("NCI Identifier"), AI("Assigned Identifier"), 
	COORDINATING_CENTER_IDENTIFIER("Coordinating Center Identifier"),
	COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER("Coordinating Center Assigned Study Subject Identifier"),
	CLINICAL_TRIALS_GOV_IDENTIFIER("ClinicalTrials.gov Identifier"),
	COOPERATIVE_GROUP_IDENTIFIER("Cooperative Group Identifier"),
	LOCAL("local"),
	STUDY_FUNDING_SPONSOR("Study Funding Sponsor"),
	MRN("MRN"),
	PROTOCOL_AUTHORITY_IDENTIFIER("Protocol Authority Identifier"),
	C3D_IDENTIFIER("C3D Identifier"),
	C3PR("C3PR"),
	SITE_IRB_IDENTIFIER("Site IRB Identifier"),
	SITE_IDENTIFIER("Site Identifier"),
	STUDY_SUBJECT_IDENTIFIER("Study Subject Identifier"),
	GRID_IDENTIFIER("Grid Identifier");

    private String code;

    private OrganizationIdentifierTypeEnum(String code) {
        this.code = code;
        register(this);
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    public String getName() {
        return name();
    }

    public static OrganizationIdentifierTypeEnum getByCode(String code) {
        return getByClassAndCode(OrganizationIdentifierTypeEnum.class, code);
    }
}
