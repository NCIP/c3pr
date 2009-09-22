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
	getByPlayersId("getByPlayersId "),
	//PA
	get("get"),
	getByPlannedActivity("getPersonByCTEPId"),
	getByStudyProtocol("getPersonByCTEPId"),
	getDocumentsByStudyProtocol("getPersonByCTEPId"),
	getCurrentByStudyProtocol("getPersonByCTEPId"),
	getByArm("getPersonByCTEPId"),
	getPlannedEligibilityCriterion("getPersonByCTEPId"),
	getPlannedEligibilityCriterionByStudyProtocol("getPersonByCTEPId"),
	getByStudyProtocolAndRole("getPersonByCTEPId"),
	getByStudyProtocolAndRoles("getPersonByCTEPId"),
	isOnhold("getPersonByCTEPId"),
	getByStudyParticipation("getPersonByCTEPId"),
	getStudyProtocol("getPersonByCTEPId"),
	getInterventionalStudyProtocol("getPersonByCTEPId"),
	getStudyResourceByID("getPersonByCTEPId"),
	getStudyResourceByStudyProtocol("getPersonByCTEPId"),
	getSummaryForReportedResource("getPersonByCTEPId"),
	getStudySiteAccrualStatus("getPersonByCTEPId"),
	getStudySiteAccrualStatusByStudyParticipation("getPersonByCTEPId"),
	getCurrentStudySiteAccrualStatusByStudyParticipation("getPersonByCTEPId");

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
