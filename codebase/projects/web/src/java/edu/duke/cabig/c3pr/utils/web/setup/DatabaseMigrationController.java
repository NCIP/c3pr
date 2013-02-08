/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.dao.ReasonDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.OffEpochReason;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.setup.SetupStatus;
import edu.duke.cabig.c3pr.utils.DatabaseMigrationHelper;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.utils.web.setup.command.DataMigrationCommand;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

/**
 * @author Kruttik Aggarwal
 */
public class DatabaseMigrationController extends SimpleFormController {
	
	private SetupStatus setupStatus;
	
	private DatabaseMigrationHelper databaseMigrationHelper;
	
	private ReasonDao reasonDao;

	public void setReasonDao(ReasonDao reasonDao) {
		this.reasonDao = reasonDao;
	}

	public void setDatabaseMigrationHelper(
			DatabaseMigrationHelper databaseMigrationHelper) {
		this.databaseMigrationHelper = databaseMigrationHelper;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		DataMigrationCommand dataMigrationCommand = (DataMigrationCommand)super.formBackingObject(request);
		List<Study> migratableStudies = databaseMigrationHelper.getMigratableStudies();
		List<StudySubject> migratableStudySubjects = databaseMigrationHelper.getMigratableStudySubjects();
		if(migratableStudies.size()>0){
			request.setAttribute("studies", migratableStudies);
			setFormView("setup/emptyEpochTypeMigration");
			dataMigrationCommand.setMigrationType(DataMigrationCommand.EPOCH_TYPE_EMPTY);
		}else{
			request.setAttribute("studySubjects", migratableStudySubjects);
			request.setAttribute("offTreatmentReasons", reasonDao.getOffTreatmentReasons());
			request.setAttribute("offScreeningReasons", reasonDao.getOffScreeningReasons());
			request.setAttribute("offReservingReasons", reasonDao.getOffReservingReasons());
			request.setAttribute("offFollowupReasons", reasonDao.getOffFollowupReasons());
			request.setAttribute("offStudyReasons", reasonDao.getOffStudyReasons());
			setFormView("setup/emptyOffEpochReasonMigration");
			dataMigrationCommand.setMigrationType(DataMigrationCommand.OFF_EPOCH_REASON_EMPTY);
		}
		return dataMigrationCommand;
	}

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(EpochType.class, new EnumByNameEditor(EpochType.class));
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o, BindException e)
			throws Exception {
		DataMigrationCommand dataMigrationCommand = (DataMigrationCommand)o;
		switch (dataMigrationCommand.getMigrationType()) {
		case DataMigrationCommand.EPOCH_TYPE_EMPTY:
			for(Epoch epoch : dataMigrationCommand.getEpochs()){
				databaseMigrationHelper.updateEpochWithType(epoch.getId(), epoch.getType());
			}
			break;
		case DataMigrationCommand.OFF_EPOCH_REASON_EMPTY:
			for(OffEpochReason offEpochReason : dataMigrationCommand.getOffEpochReasons()){
				databaseMigrationHelper.updateOffEpochReasonWithReason(offEpochReason.getId(), offEpochReason.getReason().getId());
			}
			databaseMigrationHelper.updateOffStudyWithNotRegistered();
			break;
		}
		setupStatus.recheck();
		return new ModelAndView("redirect:../pages/dashboard");
	}

	@Required
	public void setSetupStatus(SetupStatus setupStatus) {
		this.setupStatus = setupStatus;
	}

}
