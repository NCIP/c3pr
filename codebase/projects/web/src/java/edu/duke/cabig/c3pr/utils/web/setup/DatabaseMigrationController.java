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
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.setup.SetupStatus;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.DatabaseMigrationHelper;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.utils.web.setup.command.EpochTypeCommand;

/**
 * @author Kruttik Aggarwal
 */
public class DatabaseMigrationController extends SimpleFormController {
	
	private SetupStatus setupStatus;
	
	private Configuration configuration;
	
	private DatabaseMigrationHelper databaseMigrationHelper;

	public void setDatabaseMigrationHelper(
			DatabaseMigrationHelper databaseMigrationHelper) {
		this.databaseMigrationHelper = databaseMigrationHelper;
	}

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(EpochType.class, new EnumByNameEditor(EpochType.class));
	}
	
	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		Map<String, List<Study>> map = new HashMap<String, List<Study>>();
		map.put("studies", databaseMigrationHelper.getMigratableStudies());
		return map;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o, BindException e)
			throws Exception {
		EpochTypeCommand epochTypeCommand = (EpochTypeCommand)o;
		for(Epoch epoch : epochTypeCommand.getEpochs()){
			databaseMigrationHelper.updateEpochWithType(epoch.getId(), epoch.getType());
		}
		setupStatus.recheck();
		return new ModelAndView(getSuccessView());
	}

	@Required
	public void setSetupStatus(SetupStatus setupStatus) {
		this.setupStatus = setupStatus;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}
