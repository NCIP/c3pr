package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.util.WebUtils;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class ManageEpochTab<C extends StudySubject> extends InPlaceEditableTab<C>{

	public ManageEpochTab() {
		super("Manage Epochs", "Manage Epochs", "registration/reg_manage_epochs");
	}

	private EpochDao epochDao;
	public EpochDao getEpochDao() {
		return epochDao;
	}

	public void setEpochDao(EpochDao epochDao) {
		this.epochDao = epochDao;
	}

	@Override
	protected String postProcessAsynchronously(HttpServletRequest request, C command, Errors error) {
		// TODO Auto-generated method stub
		int id=-1;
		if(WebUtils.hasSubmitParameter(request, "epochId")){
			id=Integer.parseInt(request.getParameter("epochId"));
			Epoch epoch=epochDao.getById(id);
			for(ScheduledEpoch scheduledEpoch:command.getScheduledEpochs()){
				if(scheduledEpoch.getEpoch().getId()==epoch.getId())
					return new AjaxResponseText(true,false,id).toString();
			}
			if (epoch instanceof TreatmentEpoch) {
				return new AjaxResponseText(false,true,epoch.getId()).toString();
			}
		}
		return new AjaxResponseText(false,false,id).toString();
	}

	@Override
	protected void postProcessSynchronous(HttpServletRequest request, C command, Errors error) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	class AjaxResponseText{
		private boolean requiresStratification;
		private boolean alreadyRegistered;
		private int epochId;
		public AjaxResponseText(boolean alreadyRegistered, boolean requiresStratification, int epochId) {
			this.requiresStratification = requiresStratification;
			this.alreadyRegistered = alreadyRegistered;
			this.epochId = epochId;
		}
		public AjaxResponseText(){}
		public boolean isAlreadyRegistered() {
			return alreadyRegistered;
		}

		public void setAlreadyRegistered(boolean alreadyRegistered) {
			this.alreadyRegistered = alreadyRegistered;
		}

		public int getEpochId() {
			return epochId;
		}

		public void setEpochId(int epochId) {
			this.epochId = epochId;
		}

		public boolean isRequiresStratification() {
			return requiresStratification;
		}

		public void setRequiresStratification(boolean requiresStratification) {
			this.requiresStratification = requiresStratification;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return alreadyRegistered+"||"+requiresStratification+"||"+epochId+"||";
		}
	}
}
