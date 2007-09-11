package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class StratificationTab extends RegistrationTab<StudySubject>{

	public StratificationTab() {
		super("Stratify", "Stratify","registration/reg_stratify");
	}
	@Override
	public Map<String, Object> referenceData(StudySubject command) {
		Map ref=new HashMap();
		boolean requiresStratification=false;
		if(command.getIfTreatmentScheduledEpoch())
			if(((TreatmentEpoch)command.getScheduledEpoch().getEpoch()).getStratificationCriteria().size()>0)
				requiresStratification=true;
		ref.put("requiresStratification", requiresStratification);
		return ref;
	}
}
