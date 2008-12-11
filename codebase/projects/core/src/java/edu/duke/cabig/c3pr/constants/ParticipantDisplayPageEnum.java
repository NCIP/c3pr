package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum ParticipantDisplayPageEnum implements CodedEnum<String>{
	
	PARTICIPANY_DETAIL("participant details"), CONTACT_INFO("contact information");
		
		private String code;

	    private ParticipantDisplayPageEnum(String code) {
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

	    public static ParticipantDisplayPageEnum getByCode(String code) {
	        return getByClassAndCode(ParticipantDisplayPageEnum.class, code);
	    }
	}