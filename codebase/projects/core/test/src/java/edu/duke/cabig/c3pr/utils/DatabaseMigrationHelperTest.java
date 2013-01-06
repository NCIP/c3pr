package edu.duke.cabig.c3pr.utils;

import java.util.List;

import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.domain.Study;


public class DatabaseMigrationHelperTest extends DaoTestCase{

	private DatabaseMigrationHelper databaseMigrationHelper;
	private EpochDao epochDao;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		databaseMigrationHelper = (DatabaseMigrationHelper) getApplicationContext().getBean("databaseMigrationHelper");
		epochDao = (EpochDao) getApplicationContext().getBean("epochDao");
	}
	
	public void testIsDatabaseMigrationNeededTrue(){
		assertEquals(true, databaseMigrationHelper.isDatabaseMigrationNeeded());
	}
	
	public void testUpdateEpochWithType(){
		databaseMigrationHelper.updateEpochWithType(1000, EpochType.RESERVING);
		databaseMigrationHelper.updateEpochWithType(1001, EpochType.SCREENING);
		databaseMigrationHelper.updateEpochWithType(1002, EpochType.TREATMENT);
		databaseMigrationHelper.updateEpochWithType(1003, EpochType.FOLLOWUP);
		assertEquals(EpochType.RESERVING, epochDao.getById(1000).getType());
		assertEquals(EpochType.SCREENING, epochDao.getById(1001).getType());
		assertEquals(EpochType.TREATMENT, epochDao.getById(1002).getType());
		assertEquals(EpochType.FOLLOWUP, epochDao.getById(1003).getType());
	}
	
	public void testIsDatabaseMigrationNeededFalse(){
		databaseMigrationHelper.updateEpochWithType(1000, EpochType.RESERVING);
		databaseMigrationHelper.updateEpochWithType(1001, EpochType.SCREENING);
		databaseMigrationHelper.updateEpochWithType(1002, EpochType.TREATMENT);
		databaseMigrationHelper.updateEpochWithType(1003, EpochType.FOLLOWUP);
		assertEquals(false, databaseMigrationHelper.isDatabaseMigrationNeeded());
	}
	
	public void testGetMigratableStudies(){
		List<Study> studies = databaseMigrationHelper.getMigratableStudies();
		assertEquals(1, studies.size());
		assertEquals(1001, studies.get(0).getId().intValue());
		databaseMigrationHelper.updateEpochWithType(1002, EpochType.RESERVING);
		databaseMigrationHelper.updateEpochWithType(1003, EpochType.SCREENING);
		studies = databaseMigrationHelper.getMigratableStudies();
		assertEquals(0, studies.size());
	}
}
