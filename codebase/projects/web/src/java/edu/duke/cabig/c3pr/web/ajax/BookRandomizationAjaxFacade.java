package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
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

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.StringUtils;

public class BookRandomizationAjaxFacade {

    private static Log log = LogFactory.getLog(BookRandomizationAjaxFacade.class);

    protected StudyDao studyDao;

    /*
     * This is the method that is called from the study_randomization.jsp on clicking the "Upload
     * Randomization Book" button. This is also consequently called on clicking any of the table
     * buttons like pagination. As this does all the spring binding of uploaded data, the
     * implementation of postProcess() in StudyRandomizationTab.java contains nothing.
     * 
     * epochIndexString - This is the index of the epoch being dealt with. content - This is the
     * data pasted in the textarea by the user. parameterMap - this is used by the table to run its
     * own functionality like pagination.
     */
    public String getTable(Map<String, List> parameterMap, String content, String epochIndexString,
                    HttpServletRequest req, String flowType) {
        Context context;
        if (parameterMap != null) {
            context = new HttpServletRequestContext(req, parameterMap);
        }
        else {
            context = new HttpServletRequestContext(req);
        }

        String action = "/pages/study/createStudy";
        Study study = (Study) req.getSession().getAttribute(
                        "edu.duke.cabig.c3pr.web.study.CreateStudyController.FORM.command");
        if (study == null) {
            if (flowType.equals("AMEND_STUDY")) {
                study = (Study) req.getSession().getAttribute(
                                "edu.duke.cabig.c3pr.web.study.AmendStudyController.FORM.command");
                action = "/pages/study/amendStudy";
            }
            else {
                study = (Study) req.getSession().getAttribute(
                                "edu.duke.cabig.c3pr.web.study.EditStudyController.FORM.command");
                action = "/pages/study/editStudy";
            }
            if (study != null && study instanceof Study) {
                studyDao.reassociate(study);
            }
        }
        TableModel model = new TableModelImpl(context);
        Epoch tEpoch = null;
        if (study != null && study.getRandomizationType() != null
                        && study.getRandomizationType().equals(RandomizationType.BOOK)) {
            String bookRandomizations;
            int selectedEpoch = StringUtils.getBlankIfNull(epochIndexString).equals("") ? -1
                            : Integer.parseInt(epochIndexString);
            tEpoch = study.getEpochs().get(selectedEpoch);
            bookRandomizations = StringUtils.getBlankIfNull(content);
            if (!StringUtils.isEmpty(bookRandomizations)) {
                if (tEpoch != null) {
                    try {
                    	if (study.getStratificationIndicator()){
                    		parseBookRandomization(bookRandomizations, tEpoch);
                    	}else {
                    		parseBookRandomizationWithoutStratification(bookRandomizations, tEpoch);
                    	}
                    }
                    catch (Exception e) {
                        log.error("Error while calling parseBookRandomization: " + e.getMessage());
                        return "<br/><div class='error'>Incorrect format. Please try again.</div>";
                    }
                    if (study.getStratificationIndicator()){
                    	validatePositions(((BookRandomization) tEpoch.getRandomization())
                                    .getBookRandomizationEntry());
                    }else {
                    	validatePositionsWithoutStratification(((BookRandomization) tEpoch.getRandomization())
                                .getBookRandomizationEntry());
                    }
                    
                }
                else {
                    log.error("Invalid epoch Index");
                }
            }

            if (tEpoch != null && tEpoch.getRandomization()!=null) {
                List<BookRandomizationEntry> breList = ((BookRandomization) tEpoch
                                .getRandomization()).getBookRandomizationEntry();
                try {
                	if (study.getStratificationIndicator()){
                		return build(model, breList, "Book Randomization :" + selectedEpoch, action,
                                    flowType).toString();
                	}else {
                		return buildWithoutStratification(model, breList, "Book Randomization :" + selectedEpoch, action,
                                flowType).toString();
                	}
                }
                catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        return "";
    }

    /*
     * This method goes through the positions and ensures that they start with 0 and increment by 1
     * for every stratum group number. if any incorrect number is found it is replaced by null. the
     * buildTable replaces blank string with "Invalid Entry".
     */
    void validatePositions(List<BookRandomizationEntry> breList) {
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
    void validatePositionsWithoutStratification(List<BookRandomizationEntry> breList) {
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
            Randomization randomization = tEpoch.getRandomization();
            BookRandomization bRandomization = new BookRandomization();

            BookRandomizationEntry bookRandomizationEntry = new BookRandomizationEntry();
            ArrayList<BookRandomizationEntry> breList = new ArrayList<BookRandomizationEntry>();

            StringTokenizer outer = new StringTokenizer(bookRandomizations, "\n");
            String entry;
            String[] entries;
            Arm arm;
            StratumGroup sGroup;
            while (outer.hasMoreElements()) {
                entry = outer.nextToken();
                if (entry.trim().length() > 0) {
                    entries = entry.split(",");
                    // find the stratum group with this id and set it here even if its null
                    sGroup = getStratumGroupByNumber(tEpoch, entries[0].trim());
                    bookRandomizationEntry.setStratumGroup(sGroup);
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
                    // for while generating the table and
                    // replaced with a string "Invalid Entry" so the user can see which entries are
                    // incorrect.
                    arm = getArmByName(tEpoch, entries[2].trim());
                    bookRandomizationEntry.setArm(arm);
                    // we add the entry to the list
                    breList.add(bookRandomizationEntry);
                    bookRandomizationEntry = new BookRandomizationEntry();
                }
            }
            // In order to ensure that we do not re-generate results in cases where the user may
            // click the button twice,
            // we clear the entries in the randomization every time and generate fresh results.
            bRandomization.getBookRandomizationEntry().clear();
            bRandomization.getBookRandomizationEntry().addAll(breList);

            if (randomization == null) {
                tEpoch.setRandomization(bRandomization);
            }
            else if (breList != null && breList.size() > 0) {
                // bRandomization = (BookRandomization)randomization;

                // ensuring the cascade doesnt re-save the book Entries
                tEpoch.setRandomization(null);
                tEpoch.setRandomization(bRandomization);
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
		Randomization randomization = tEpoch.getRandomization();
		BookRandomization bRandomization = new BookRandomization();
		
		BookRandomizationEntry bookRandomizationEntry = new BookRandomizationEntry();
		ArrayList<BookRandomizationEntry> breList = new ArrayList<BookRandomizationEntry>();
		
		StringTokenizer outer = new StringTokenizer(bookRandomizations, "\n");
		String entry;
		String[] entries;
		Arm arm;
	//	StratumGroup sGroup;
		while (outer.hasMoreElements()) {
		entry = outer.nextToken();
		if (entry.trim().length() > 0) {
		    entries = entry.split(",");
		    // find the stratum group with this id and set it here even if its null
	//	    sGroup = getStratumGroupByNumber(tEpoch, entries[0].trim());
	//	    bookRandomizationEntry.setStratumGroup(sGroup);
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
		    // for while generating the table and
		    // replaced with a string "Invalid Entry" so the user can see which entries are
		    // incorrect.
		    arm = getArmByName(tEpoch, entries[1].trim());
		    bookRandomizationEntry.setArm(arm);
		    // we add the entry to the list
		    breList.add(bookRandomizationEntry);
		    bookRandomizationEntry = new BookRandomizationEntry();
		}
		}
		// In order to ensure that we do not re-generate results in cases where the user may
		// click the button twice,
		// we clear the entries in the randomization every time and generate fresh results.
		bRandomization.getBookRandomizationEntry().clear();
		bRandomization.getBookRandomizationEntry().addAll(breList);
		
		if (randomization == null) {
		tEpoch.setRandomization(bRandomization);
		}
		else if (breList != null && breList.size() > 0) {
		// bRandomization = (BookRandomization)randomization;
		
		// ensuring the cascade doesnt re-save the book Entries
		tEpoch.setRandomization(null);
		tEpoch.setRandomization(bRandomization);
		}
		
		}
		catch (Exception e) {
		log.error("parseBookRandomizatrion Failed");
		log.error(e.getMessage());
		throw e;
		}
		}

    /*
     * Takes the treatementEpoch and arm.name and returns the arm (from that epoch)which has that
     * name. returns null if no arm is found
     */
    public Arm getArmByName(Epoch tEpoch, String armName) {
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
    public StratumGroup getStratumGroupByNumber(Epoch tEpoch, String sgPos) {
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
        log.debug("Invalid Stratum Group Number Entered.");
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
        table.setRowsDisplayed(15);
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
        table
                        .setOnInvokeAction("uploadBook('" + tableId + "', '" + index + "','"
                                        + flowType + "')");
        table.setShowPagination(true);
        table.setFilterable(false);
        table.setImagePath(model.getContext().getContextPath() + "/images/table/*.gif");
        table.setRowsDisplayed(15);
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

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }
}
