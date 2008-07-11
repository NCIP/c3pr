package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class ManageEpochTab<C extends StudySubject> extends RegistrationTab<C> {

    public ManageEpochTab() {
        super("Manage Epochs", "Change Current Epoch", "registration/reg_manage_epochs");
    }

    private EpochDao epochDao;

    public EpochDao getEpochDao() {
        return epochDao;
    }

    public void setEpochDao(EpochDao epochDao) {
        this.epochDao = epochDao;
    }

    public ModelAndView getEpochSection(HttpServletRequest request, Object commandObj, Errors error) {
        C command = (C) commandObj;
        int id = -1;
        Map<String, Object> map = new HashMap<String, Object>();
        id = Integer.parseInt(request.getParameter("epochId"));
        Epoch epoch = epochDao.getById(id);
        map.put("epoch", epoch);
        map.put("alreadyRegistered", new Boolean(false));
        map.put("notRegistrable", new Boolean(false));
        map.put("requiresEligibility", new Boolean(false));
        map.put("requiresStratification", new Boolean(false));
        map.put("requiresRandomization", new Boolean(false));
        map.put("isCurrentScheduledEpoch", new Boolean(false));
        map.put("requiresArm", new Boolean(false));
        map.put("acrrualCeilingReached", new Boolean(studySubjectRepository
                        .isEpochAccrualCeilingReached(id)));
        if (epoch.getDisplayRole().equalsIgnoreCase("treatment")) {
            map.put("epochType", "Treatment");
            if (epoch.getEligibilityCriteria().size() > 0) {
                map.put("requiresEligibility", new Boolean(true));
            }
            if (epoch.getStratificationCriteria().size() > 0) {
                map.put("requiresStratification", new Boolean(true));
            }
            if (epoch.getRandomizedIndicator()) {
                map.put("requiresRandomization", new Boolean(true));
            }
            if (epoch.getArms().size() > 0) {
                map.put("requiresArm", new Boolean(true));
            }
        }
        for (ScheduledEpoch scheduledEpoch : command.getScheduledEpochs()) {
            if (scheduledEpoch.getEpoch().getId() == epoch.getId()) {
                map.put("alreadyRegistered", new Boolean(true));
            }
        }
        if (command.getCurrentScheduledEpoch().getEpoch().getId() == epoch.getId()) map.put(
                        "isCurrentScheduledEpoch", new Boolean(true));
        if (epoch.getEpochOrder() < command.getCurrentScheduledEpoch().getEpoch().getEpochOrder()) {
            map.put("notRegistrable", new Boolean(true));
        }
        else if (epoch.getEpochOrder() == command.getCurrentScheduledEpoch().getEpoch()
                        .getEpochOrder()
                        && command.getCurrentScheduledEpoch().getEpoch().getId() != epoch.getId()) {
            map.put("notRegistrable", new Boolean(true));
        }
        return new ModelAndView(getAjaxViewName(request), map);
    }
}
