package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum ScheduledEpochWorkFlowStatus implements CodedEnum<String> {
     PENDING_ON_EPOCH("Pending On-Epoch"), PENDING_RANDOMIZATION_ON_EPOCH("Pending Randomizaiton On-Epoch"),ON_EPOCH("On-Epoch"),
     OFF_EPOCH("Off-Epoch");

    private String code;

    private ScheduledEpochWorkFlowStatus(String code) {
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

    public static ScheduledEpochWorkFlowStatus getByCode(String code) {
        return getByClassAndCode(ScheduledEpochWorkFlowStatus.class, code);
    }
}
