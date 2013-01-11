/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;

import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.study.AmendCompanionStudyController;
import edu.duke.cabig.c3pr.web.study.AmendStudyController;
import edu.duke.cabig.c3pr.web.study.CreateCompanionStudyController;
import edu.duke.cabig.c3pr.web.study.CreateStudyController;
import edu.duke.cabig.c3pr.web.study.EditCompanionStudyController;
import edu.duke.cabig.c3pr.web.study.EditStudyController;
import edu.duke.cabig.c3pr.web.study.StudyController;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

public class BookRandomizationAjaxFacade {

    private static Log log = LogFactory.getLog(BookRandomizationAjaxFacade.class);

    protected StudyDao studyDao;

    /*
     * This is the method that is called from the study_randomization.jsp on clicking the "Upload
     * Randomization Book" (text upload using dwr)
     */
    public String getTable(Map<String, List> parameterMap, String content, String epochIndexString,
                    HttpServletRequest req, String flowType) {
    		return getTableForWrapper(parameterMap, content, epochIndexString, req, flowType, null);
    }
    
    
    /*
     * This is the method that is called from the study_randomization for the file upload and
     * the text upload. This is also consequently called on clicking any of the table
     * buttons like pagination. As this does all the binding of uploaded data, the
     * implementation of postProcess() in StudyRandomizationTab.java contains a call to this and 
     * itself is activated in turn for file upload.
     * 
     * epochIndexString - This is the index of the epoch being dealt with. content - This is the
     * data pasted in the textarea by the user. parameterMap - this is used by the table to run its
     * own functionality like pagination.
     */
    public String getTableForWrapper(Map<String, List> parameterMap, String content,
			String epochIndexString, HttpServletRequest req, String flowType, StudyWrapper wrapper) {
		Context context;
		if (parameterMap != null) {
			context = new HttpServletRequestContext(req, parameterMap);
		} else {
			context = new HttpServletRequestContext(req);
		}

		//the wrapper is passed in in case of File upload and refData but its null in case of text upload.
		if(wrapper == null){
			wrapper = getCommandOnly(flowType, req);
		}
    	String action = getAction(flowType, wrapper);
    	
		TableModel model = new TableModelImpl(context);
		Epoch tEpoch = null;
		if (wrapper != null
				&& wrapper.getStudy().getRandomizationType() != null
				&& wrapper.getStudy().getRandomizationType().equals(
						RandomizationType.BOOK)) {
			String bookRandomizations;
			int selectedEpoch = StringUtils.getBlankIfNull(epochIndexString)
					.equals("") ? -1 : Integer.parseInt(epochIndexString);
			tEpoch = wrapper.getStudy().getEpochs().get(selectedEpoch);
			bookRandomizations = StringUtils.getBlankIfNull(content);
			if (!StringUtils.isEmpty(bookRandomizations)) {
				if (tEpoch != null) {
					try {
						if (tEpoch.getStratificationIndicator()) {
							parseBookRandomization(bookRandomizations, tEpoch);
						} else {
							parseBookRandomizationWithoutStratification(
									bookRandomizations, tEpoch);
						}
					} catch (Exception e) {
						log
								.error("Error while calling parseBookRandomization: "
										+ e.getMessage());
						return "<br/><div class='error'>Incorrect format. Please try again.</div>";
					}
					if (tEpoch.getStratificationIndicator()) {
						validatePositions(((BookRandomization) tEpoch
								.getRandomization())
								.getBookRandomizationEntry());
					} else {
						validatePositionsWithoutStratification(((BookRandomization) tEpoch
								.getRandomization())
								.getBookRandomizationEntry());
					}
				} else {
					log.error("Invalid epoch Index");
				}
			}

			if (tEpoch != null && tEpoch.getRandomization() != null) {
				List<BookRandomizationEntry> breList = ((BookRandomization) tEpoch
						.getRandomization()).getBookRandomizationEntry();
				try {
					if (tEpoch.getStratificationIndicator()) {
						return build(model, breList,
								"Book Randomization :" + selectedEpoch, action,
								flowType).toString();
					} else {
						return buildWithoutStratification(model, breList,
								"Book Randomization :" + selectedEpoch, action,
								flowType).toString();
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
		return "";
	}
    
    
    private StudyWrapper getCommandOnly(String flowType, HttpServletRequest req){
    	StudyWrapper wrapper = null;
    	if (flowType.equals(StudyController.CREATE_STUDY)) {
    		return (StudyWrapper) req.getSession().getAttribute(CreateStudyController.class.getName() + ".FORM.command");
    	}
    	if (flowType.equals(StudyController.CREATE_COMPANION_STUDY)) {
    		return (StudyWrapper) req.getSession().getAttribute(CreateCompanionStudyController.class.getName() + ".FORM.command");
		}
    	if (flowType.equals(StudyController.AMEND_STUDY)) {
    		return (StudyWrapper) req.getSession().getAttribute(AmendStudyController.class.getName() + ".FORM.command");
        } 
    	if(flowType.equals(StudyController.AMEND_COMPANION_STUDY)){
    		return (StudyWrapper) req.getSession().getAttribute(AmendCompanionStudyController.class.getName() + ".FORM.command");
        }
    	if(flowType.equals(StudyController.EDIT_STUDY)){
    		return (StudyWrapper) req.getSession().getAttribute(EditStudyController.class.getName() + ".FORM.command");
        }
    	if(flowType.equals(StudyController.EDIT_COMPANION_STUDY)){
    		return (StudyWrapper) req.getSession().getAttribute(EditCompanionStudyController.class.getName() + ".FORM.command");
        }
    	return wrapper;
    }
    
    
    private String getAction(String flowType, StudyWrapper wrapper){
    	String action ="";
    	if (flowType.equals(StudyController.CREATE_STUDY)) {
    		return "/pages/study/createStudy";
    	}
    	if (flowType.equals(StudyController.CREATE_COMPANION_STUDY)) {
    		return "/pages/study/createCompanionStudy";
		}
    	if (flowType.equals(StudyController.AMEND_STUDY)) {
    		return "/pages/study/amendStudy";
        } 
    	if(flowType.equals(StudyController.AMEND_COMPANION_STUDY)){
    		return "/pages/study/amendCompanionStudy";
        }
    	if(flowType.equals(StudyController.EDIT_STUDY)){
    		return "/pages/study/editStudy";
        }
    	if(flowType.equals(StudyController.EDIT_COMPANION_STUDY)){
    		return "/pages/study/editCompanionStudy";
        }
    	return action;
    }

    /*
	 * This method goes through the positions and ensures that they start with 0
	 * and increment by 1 for every stratum group number. if any incorrect
	 * number is found it is replaced by null. the buildTable replaces blank
	 * string with "Invalid Entry".
	 */
    private void validatePositions(List<BookRandomizationEntry> breList) {
        Iterator iter = breList.iterator();
        BookRandomizationEntry bre;
        int i = 0;
        int strGroupNumber = 0;
        while (iter.hasNext()) {
            bre = (BookRandomizationEntry) iter.next();
            if (bre.getStratumGroup() != null
                            && bre.getStratumGroup().getStratumGroupNumber() != null
                            && bre.getStratumGroup().getStratumGroupNumber().intValue() != strGroupNumber) {
                i = 0;
            }
            strGroupNumber = bre.getStratumGroup().getStratumGroupNumber().intValue();
            if (bre.getPosition() != null && bre.getPosition().intValue() != i) {
                bre.setPosition(null);
            }
            ++i;
        }
    }
    
    /*
     * This method goes through the positions and ensures that they start with 0 and increment by 1
     * for every stratum group number. if any incorrect number is found it is replaced by null. the
     * buildTable replaces blank string with "Invalid Entry".
     */
    private void validatePositionsWithoutStratification(List<BookRandomizationEntry> breList) {
        Iterator iter = breList.iterator();
        BookRandomizationEntry bre;
        int i = 0;
        while (iter.hasNext()) {
            bre = (BookRandomizationEntry) iter.next();
            if (bre.getPosition() != null && bre.getPosition().intValue() != i) {
                bre.setPosition(null);
            }
            ++i;
        }
    }

    /*
     * This method accepts the comma seperated string which is the formatted content for book
     * randomization and populated the domain object with the corresponding data. the format is
     * "Stratum Group, Position, Arm Assignment" e.g: 0, 0, A 0, 1, C 1, 0, A 2, 1, B
     */
    private void parseBookRandomization(String bookRandomizations, Epoch tEpoch)
                    throws Exception {

        try {
            // we do not create a new instance of bookRandomization, we use the existing instance
            // which was created in StudyDesignTab.java
            // based on the randomizationType selected on the study_details page.
        	BookRandomization randomization = null;
        	if(tEpoch.getRandomization() instanceof BookRandomization){
        		randomization = (BookRandomization)tEpoch.getRandomization(); 
        	} else {
        		return;
        	}
            
            List<BookRandomizationEntry> breList = randomization.getBookRandomizationEntry();
            //clear existing bookEntries
            breList.clear();
            clearStratumGroups(tEpoch);
            
            StringTokenizer outer = new StringTokenizer(bookRandomizations, "\n");
            String entry;
            String[] entries;
            Arm arm;
            StratumGroup sGroup;
            BookRandomizationEntry bookRandomizationEntry = null;
            while (outer.hasMoreElements()) {
                entry = outer.nextToken();
                if (entry.trim().length() > 0) {
                    entries = entry.split(",");
                    bookRandomizationEntry = breList.get(breList.size());
                    
                    // find the stratum group with this id and set it here even if its null
                    sGroup = getStratumGroupByNumber(tEpoch, entries[0].trim());
                    bookRandomizationEntry.setStratumGroup(sGroup);
                    sGroup.getBookRandomizationEntry().add(bookRandomizationEntry);
                    // set the position
                    Integer position = null;
                    try {
                        position = Integer.valueOf(entries[1].trim());
                    }
                    catch (NumberFormatException nfe) {
                        log.debug("Illegal Position Entered.");
                    }
                    bookRandomizationEntry.setPosition(position);
                    // find the arm with this id and set it here even if its null
                    // Empty stratum groups with negetive stratum Group Numbers and arms are checked
                    // for while generating the table and replaced with the string 
                    // "Invalid Entry" so the user can see which entries are incorrect.
                    arm = getArmByName(tEpoch, entries[2].trim());
                    bookRandomizationEntry.setArm(arm);
                }
            }
        }
        catch (Exception e) {
            log.error("parseBookRandomizatrion Failed");
            log.error(e.getMessage());
            throw e;
        }
    }
    
    private void parseBookRandomizationWithoutStratification(String bookRandomizations, Epoch tEpoch)
    	throws Exception {

		try {
			// we do not create a new instance of bookRandomization, we use the existing instance
			// which was created in StudyDesignTab.java
			// based on the randomizationType selected on the study_details page.
			BookRandomization randomization = null;
        	if(tEpoch.getRandomization() instanceof BookRandomization){
        		randomization = (BookRandomization)tEpoch.getRandomization(); 
        	} else {
        		return;
        	}
            
            List<BookRandomizationEntry> breList = randomization.getBookRandomizationEntry();
            //clear existing bookEntries
            breList.clear();
            clearStratumGroups(tEpoch);
			
			StringTokenizer outer = new StringTokenizer(bookRandomizations, "\n");
			String entry;
			String[] entries;
			Arm arm;
			BookRandomizationEntry bookRandomizationEntry = null;
			while (outer.hasMoreElements()) {
				entry = outer.nextToken();
				if (entry.trim().length() > 0) {
				    entries = entry.split(",");
				    bookRandomizationEntry = breList.get(breList.size());
				    
				    // set the position
				    Integer position = null;
				    try {
				        position = Integer.valueOf(entries[0].trim());
				    }
				    catch (NumberFormatException nfe) {
				        log.debug("Illegal Position Entered.");
				    }
				    bookRandomizationEntry.setPosition(position);
				    // find the arm with this id and set it here even if its null
				    // Empty stratum groups with negetive stratum Group Numbers and arms are checked
				    // for while generating the table and replaced with the string 
				    // "Invalid Entry" so the user can see which entries are incorrect.
				    arm = getArmByName(tEpoch, entries[1].trim());
				    bookRandomizationEntry.setArm(arm);
				}
			}
		}
		catch (Exception e) {
			log.error("parseBookRandomizationWithoutStratification Failed");
			log.error(e.getMessage());
			throw e;
		}
	}

    /*
     * Takes the treatementEpoch and arm.name and returns the arm (from that epoch)which has that
     * name. returns null if no arm is found
     */
    private Arm getArmByName(Epoch tEpoch, String armName) {
        Arm selectedArm = null;
        if (tEpoch != null) {
            List<Arm> armList = tEpoch.getArms();
            for (Arm individualArm : armList) {
                if (individualArm.getName().equals(armName.trim())) {
                    selectedArm = individualArm;
                }
            }
        }
        return selectedArm;
    }

    /*
     * Takes the treatementEpoch and groupNumber and returns the Stratum Group(from that epoch)which
     * has that group number. returns null if no Group is found
     */
    private StratumGroup getStratumGroupByNumber(Epoch tEpoch, String sgPos) {
        StratumGroup selectedStratumGroup = new StratumGroup();
        // returning a negetive group number if entered num is illegal(e.g: a, & etc) or
        // invalid(1008 etc)
        selectedStratumGroup.setStratumGroupNumber(new Integer(-1));
        int sgNumber;
        try {
            sgNumber = Integer.parseInt(sgPos);
        }
        catch (NumberFormatException nfe) {
            log.debug("Illegal Stratum Group Number Entered.");
            return selectedStratumGroup;
        }

        if (tEpoch != null) {
            List<StratumGroup> sgList = tEpoch.getStratumGroups();
            for (StratumGroup individualStratumGroup : sgList) {
                if (individualStratumGroup.getStratumGroupNumber() == sgNumber) {
                    selectedStratumGroup = individualStratumGroup;
                }
            }
        }
        //log.debug("Invalid Stratum Group Number Entered.");
        return selectedStratumGroup;
    }

    /*
     * This is the method that has all the extreme Table API calls.It is set to display 11 rowqs at
     * a time. Also the onclick method call is set to uploadBook() and pagination is enabled. We use
     * the BookRandomizationCustomCell to check for null arms/groups and add the "invalid entry"
     * string in such cases.
     */
    public Object build(TableModel model, Collection bookRandomizationEntries, String title,
                    String action, String flowType) throws Exception {

        String index = title.substring(title.indexOf(":") + 1);
        String tableId = "bookRandomizationEntries" + index;
        Table table = model.getTableInstance();
        table.setTitle("Randomization Book");
        table.setAutoIncludeParameters(true);
        table.setAction(model.getContext().getContextPath() + action);
        table.setTableId(tableId);
        table.setItems(bookRandomizationEntries);
        table
                        .setOnInvokeAction("uploadBook('" + tableId + "', '" + index + "','"
                                        + flowType + "')");
        table.setShowPagination(true);
        table.setFilterable(false);
        table.setImagePath(model.getContext().getContextPath() + "/images/table/*.gif");
        table.setRowsDisplayed(10);
        model.addTable(table);

        Row row = model.getRowInstance();
        row.setHighlightRow(Boolean.TRUE);
        model.addRow(row);

        Column columnStratumGroupNumber = model.getColumnInstance();
        columnStratumGroupNumber.setProperty("stratumGroup.stratumGroupNumber");
        columnStratumGroupNumber.setTitle("Stratum Group Number");
        columnStratumGroupNumber.setCell((BookRandomizationCustomCell.class).getName());
        model.addColumn(columnStratumGroupNumber);

        Column columnPosition = model.getColumnInstance();
        columnPosition.setProperty("position");
        columnPosition.setCell((BookRandomizationCustomCell.class).getName());
        columnPosition.setTitle("Position");
        model.addColumn(columnPosition);

        Column columnArm = model.getColumnInstance();
        columnArm.setProperty("arm.name");
        columnArm.setTitle("Arm Name");
        columnArm.setCell((BookRandomizationCustomCell.class).getName());
        model.addColumn(columnArm);

        return model.assemble();
    }
    
    /*
     * This is the method that has all the extreme Table API calls.It is set to display 11 rowqs at
     * a time. Also the onclick method call is set to uploadBook() and pagination is enabled. We use
     * the BookRandomizationCustomCell to check for null arms/groups and add the "invalid entry"
     * string in such cases.
     */
    public Object buildWithoutStratification(TableModel model, Collection bookRandomizationEntries, String title,
                    String action, String flowType) throws Exception {

        String index = title.substring(title.indexOf(":") + 1);
        String tableId = "bookRandomizationEntries" + index;
        Table table = model.getTableInstance();
        table.setTitle("Randomization Book");
        table.setAutoIncludeParameters(true);
        table.setAction(model.getContext().getContextPath() + action);
        table.setTableId(tableId);
        table.setItems(bookRandomizationEntries);
        table.setOnInvokeAction("uploadBook('" + tableId + "', '" + index + "','"+ flowType + "')");
        table.setShowPagination(true);
        table.setFilterable(false);
        table.setImagePath(model.getContext().getContextPath() + "/images/table/*.gif");
        table.setRowsDisplayed(10);
        model.addTable(table);

        Row row = model.getRowInstance();
        row.setHighlightRow(Boolean.TRUE);
        model.addRow(row);

        Column columnPosition = model.getColumnInstance();
        columnPosition.setProperty("position");
        columnPosition.setCell((BookRandomizationCustomCell.class).getName());
        columnPosition.setTitle("Position");
        model.addColumn(columnPosition);

        Column columnArm = model.getColumnInstance();
        columnArm.setProperty("arm.name");
        columnArm.setTitle("Arm Name");
        columnArm.setCell((BookRandomizationCustomCell.class).getName());
        model.addColumn(columnArm);

        return model.assemble();
    }
    
    
    private void clearStratumGroups(Epoch tEpoch){
    	for(StratumGroup sg: tEpoch.getStratumGroups()){
    		sg.getBookRandomizationEntry().clear();
    	}
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }
}
