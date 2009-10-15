package edu.duke.cabig.c3pr.esb;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum OperationNameEnum implements CodedEnum<String> {
	NA("NA"), 
	getPerson("getPerson"), 
	getById("getById"),
	createPerson("createPerson"),
	validate("validate"),
	search("search"),
	create("create"),
	updatePerson("updatePerson"),
	updatePersonStatus("updatePersonStatus"),
	getOrganization("getOrganization"),
	createOrganization("createOrganization"),
	updateOrganization("updateOrganization"),
	updateOrganizationStatus("updateOrganizationStatus"),
	getCorrelation("getCorrelation"),
	getCorrelations("getCorrelations"),
	createCorrelation("createCorrelation"),
	updateCorrelation("updateCorrelation"),
	updateCorrelationStatus("updateCorrelationStatus"),
	getPersonByCTEPId("getPersonByCTEPId"),
	getByPlayerIds("getByPlayerIds"),
	//PA
	get("get"),
	getByPlannedActivity("getByPlannedActivity"),
	getByStudyProtocol("getByStudyProtocol"),
	getDocumentsByStudyProtocol("getDocumentsByStudyProtocol"),
	getCurrentByStudyProtocol("getCurrentByStudyProtocol"),
	getByArm("getByArm"),
	getPlannedEligibilityCriterion("getPlannedEligibilityCriterion"),
	getPlannedEligibilityCriterionByStudyProtocol("getPlannedEligibilityCriterionByStudyProtocol"),
	getByStudyProtocolAndRole("getByStudyProtocolAndRole"),
	getByStudyProtocolAndRoles("getByStudyProtocolAndRoles"),
	isOnhold("isOnhold"),
	getByStudySite("getByStudySite"),
	getStudyProtocol("getStudyProtocol"),
	getInterventionalStudyProtocol("getInterventionalStudyProtocol"),
	getStudyResourceByID("getStudyResourceByID"),
	getStudyResourceByStudyProtocol("getStudyResourceByStudyProtocol"),
	getSummaryForReportedResource("getSummaryForReportedResource"),
	getStudySiteAccrualStatus("getStudySiteAccrualStatus"),
	getStudySiteAccrualStatusByStudyParticipation("getStudySiteAccrualStatusByStudyParticipation"),
	getCurrentStudySiteAccrualStatusByStudyParticipation("getCurrentStudySiteAccrualStatusByStudyParticipation"),

	//Inter-Operability
	PROCESS("PROCESS");
	
    private String code;

    private OperationNameEnum(String code) {
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

    public static OperationNameEnum getByCode(String code) {
        return getByClassAndCode(OperationNameEnum.class, code);
    }
}
