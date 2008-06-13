package edu.duke.cabig.c3pr.web.study;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionAnswerCombination;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.web.beans.DefaultObjectPropertyReader;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:28:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyStratificationTab extends StudyTab {

    public List<List<StratumGroup>> tEpochsListForReorderedGroups = new ArrayList<List<StratumGroup>>();

    public StudyStratificationTab() {
        super("Stratification Factors", "Stratification", "study/study_stratifications");
    }

    @Override
    public Map referenceData(HttpServletRequest request, Study study) {
        Map<String, Object> refdata = super.referenceData(study);
        boolean isAdmin = isAdmin();
        if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow")
                        .toString().equals("true"))
                        || (request.getAttribute("editFlow") != null && request.getAttribute(
                                        "editFlow").toString().equals("true"))) {
            if (request.getSession().getAttribute(DISABLE_FORM_STRATIFICATION) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(
                                DISABLE_FORM_STRATIFICATION));
            }
            else {
                refdata.put("disableForm", new Boolean(false));
                refdata.put("mandatory", "true");
            }
        }
        return refdata;
    }

    /*
     * This method gets the serialized string that has the order of the re-ordered Stratum Groups.
     * Use this to update the str grp number of the str groups in the collection. We chose to do
     * this instead of deleting and creating new ones as this would cascade and delete the book
     * randomization entries.
     */
    public ModelAndView reorderStratumGroups(HttpServletRequest request, Object commandObj,
                    Errors error) {

        Study study = (Study) commandObj;
        String serializedString = request.getParameter("serializedString").toString();
        String[] serStrArr = serializedString.split("&");
        int indexOfEpochNumber = new Integer(String.valueOf(serStrArr[0].charAt(serStrArr[0]
                        .indexOf("_") + 1))).intValue();
        TreatmentEpoch tEpoch = study.getTreatmentEpochs().get(indexOfEpochNumber);

        List<Integer> positionList = new ArrayList<Integer>();
        for (int i = 0; i < serStrArr.length; i++) {
            positionList.add(i, new Integer(String.valueOf(serStrArr[i].charAt(serStrArr[i]
                            .length() - 1))));
        }
        // so if the order initially was 0123 and was changed to 1023 then the position list
        // will contain 1023.

        List<StratumGroup> sgList = new ArrayList<StratumGroup>();
        // create a new list of stratum groups that contain dummy sg objects
        // which only have stratumGroupNumber and AnswerCOmbinations as attributes.
        // This sgList will have the cloned groups in the new order.
        // this list is re-created everytime the user updates (i.e reorders) the groups.
        // It is then stored in the tEpochsListForReorderedGroups list which is maintained as an
        // instance var.
        // on save the studyObject(command obj) is updated with the final order which is maintained
        // in the sglist
        // in the tEpochsListForReorderedGroups
        for (int i = 0; i < positionList.size(); i++) {
            // get the group and put it as per the new order in the sg list
            sgList.add(i, tEpoch.getStratumGroupByNumber(positionList.get(i)).clone());
        }
        if (tEpochsListForReorderedGroups.size() > indexOfEpochNumber
                        && tEpochsListForReorderedGroups.get(indexOfEpochNumber) != null) {
            tEpochsListForReorderedGroups.remove(indexOfEpochNumber);
        }
        tEpochsListForReorderedGroups.add(indexOfEpochNumber, sgList);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(this.getFreeTextModelName(), serializedString);
        return new ModelAndView("", map);
    }

    /*
     * In this method we update the stratumGroupsNumbers based on the new order(after re-ordering)
     * that is maintained in the tEpochsListForReorderedGroups list.
     */
    public void finalizeReoderedGroups(Study study, boolean isCreate) {
        List<StratumGroup> sgList;
        TreatmentEpoch tEpoch;
        StratumGroup sg = null;
        List<StratumGroup> sgListTemp = new ArrayList<StratumGroup>();
        for (int i = 0; i < tEpochsListForReorderedGroups.size(); i++) {
            tEpoch = study.getTreatmentEpochs().get(i);
            sgList = tEpochsListForReorderedGroups.get(i);
            for (int j = 0; j < sgList.size(); j++) {
                // get the group and update its number as per the for loop iter
                sg = tEpoch.getStratumGroupForAnsCombination(sgList.get(j)
                                .getStratificationCriterionAnswerCombination());
                if (sg != null) {
                    sg.setStratumGroupNumber(new Integer(j));
                }
                sgListTemp.add(sg);
            }
            Collections.sort(tEpoch.getStratumGroups());
        }
    }

    /*
     * Clear the stratum groups and the book entries (if any)corresponding to the tratemtn epoch.
     * called by the rowInserter from the row manager
     */
    public ModelAndView clearStratumGroups(HttpServletRequest request, Object commandObj,
                    Errors error) {
        String message = "";
        Study study = (Study) commandObj;
        int epochCountIndex = Integer.parseInt(request.getParameter("epochCountIndex"));
        TreatmentEpoch te = study.getTreatmentEpochs().get(epochCountIndex);
        if (!te.getStratumGroups().isEmpty()) {
            // clearing the bre's
            Randomization randomization = te.getRandomization();
            if (randomization instanceof BookRandomization) {
                BookRandomization bRandomization = (BookRandomization) randomization;
                bRandomization.getBookRandomizationEntry().clear();
            }
            // clearing the stratum groups.
            te.getStratumGroups().clear();

            // reassociating the object.(Should try to remove this from here.)
            if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow")
                            .toString().equals("true"))
                            || (request.getAttribute("editFlow") != null && request.getAttribute(
                                            "editFlow").toString().equals("true"))) {
                if (study != null) {
                    getStudyRepository().reassociate(study);
                    getStudyRepository().refresh(study);
                }
            }
            message = "Generate stratum groups again.";
        }

        Map map = new HashMap();
        map.put(getFreeTextModelName(), message);
        return new ModelAndView("", map);
    }

    /*
     * Clear the stratum groups and the book entries (if any)corresponding to the tratemtn epoch.
     * Call by the deleteRow of the row manager.
     */
    @Override
    public ModelAndView deleteRow(HttpServletRequest request, Object command, Errors error)
                    throws Exception {

        String listPath = request.getParameter(getCollectionParamName());
        // run this piece of code only if str Qs or Ans are being deleted.

        listPath = listPath.substring(0, listPath.indexOf("."));
        TreatmentEpoch te = (TreatmentEpoch) new DefaultObjectPropertyReader(command, listPath)
                        .getPropertyValueFromPath();

        // Clearing the book entries from Randomization object.
        Randomization randomization = te.getRandomization();
        if (randomization instanceof BookRandomization) {
            BookRandomization bRandomization = (BookRandomization) randomization;
            bRandomization.getBookRandomizationEntry().clear();
        }

        if (request.getParameter(getCollectionParamName()).toString().endsWith("stratumGroups")) {
            // clearing all the Book entries from the scac so that they are not re-saved by cascade
            List<StratumGroup> sgList = te.getStratumGroups();
            for (StratumGroup sg : sgList) {
                sg.getBookRandomizationEntry().clear();
            }
        }
        else if (!te.getStratumGroups().isEmpty()) {
            // clearing all the qs and ans references from the scac so that they are not re-saved by
            // cascade
            List<StratumGroup> sgList = te.getStratumGroups();
            for (StratumGroup sg : sgList) {
                List<StratificationCriterionAnswerCombination> scacList = sg
                                .getStratificationCriterionAnswerCombination();
                for (StratificationCriterionAnswerCombination scac : scacList) {
                    scac.setStratificationCriterion(null);
                    scac.setStratificationCriterionPermissibleAnswer(null);
                }
            }
            // finally clearing the stratum groups
            te.getStratumGroups().clear();
        }

        return super.deleteRow(request, command, error);
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest req, Study study, Errors errors) {
        int epochCountIndex = -1;
        super.postProcessOnValidation(req, study, errors);
        if (req.getParameter("epochCountIndex") != null
                        && !req.getParameter("generateGroups").toString().equalsIgnoreCase("false")) {
            epochCountIndex = Integer.parseInt(req.getParameter("generateGroups"));
            generateStratumGroups(req, study, errors, epochCountIndex);
        }
        boolean isCreate = false;

        if (req.getParameter("flowType") != null
                        && req.getParameter("flowType").toString().equalsIgnoreCase("CREATE_STUDY")) {
            isCreate = true;
        }
        finalizeReoderedGroups(study, isCreate);

        // just renumber the groups as something may have been deleted.
        List<TreatmentEpoch> teList = study.getTreatmentEpochs();
        for (TreatmentEpoch te : teList) {
            List<StratumGroup> sgList = te.getStratumGroups();
            for (int i = 0; i < sgList.size(); i++) {
                sgList.get(i).setStratumGroupNumber(new Integer(i));
            }
        }

    }

    public void generateStratumGroups(HttpServletRequest request, Object commandObj, Errors error,
                    int epochCountIndex) {
        int scSize;
        TreatmentEpoch te;
        ArrayList<StratumGroup> sgList;
        Study study = (Study) commandObj;
        // int epochCountIndex = Integer.parseInt(request.getParameter("epochCountIndex"));
        request.setAttribute("epochCountIndex", epochCountIndex);

        // creating groups from the questions & answers for the selected treatemtn epoch
        te = study.getTreatmentEpochs().get(epochCountIndex);

        // clear the existing groups first.(incase the user cilcks on generate twice)
        te.getStratumGroups().clear();

        scSize = te.getStratificationCriteria().size();
        if (scSize > 0) {
            StratificationCriterionPermissibleAnswer doubleArr[][] = new StratificationCriterionPermissibleAnswer[scSize][];
            List<StratificationCriterionPermissibleAnswer> tempList;

            // creating a 2d array of answers for every treatment epoch
            for (int i = 0; i < scSize; i++) {
                tempList = te.getStratificationCriteria().get(i).getPermissibleAnswers();
                doubleArr[i] = new StratificationCriterionPermissibleAnswer[tempList.size()];
                for (int j = 0; j < tempList.size(); j++) {
                    doubleArr[i][j] = tempList.get(j);
                }
            }

            sgList = comboGenerator(te, doubleArr, 0, new ArrayList<StratumGroup>(),
                            new ArrayList<StratificationCriterionAnswerCombination>());
            te.getStratumGroups().addAll(sgList);
        }
        if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow")
                        .toString().equals("true"))
                        || (request.getAttribute("editFlow") != null && request.getAttribute(
                                        "editFlow").toString().equals("true"))) {
            if (study != null) {
                getStudyRepository().reassociate(study);
                getStudyRepository().refresh(study);
            }
        }
    }

    // recursive method which computes all possible combinations
    // of sc and scpa and creates a list of stratum Groups for the same.
    public ArrayList<StratumGroup> comboGenerator(TreatmentEpoch te,
                    StratificationCriterionPermissibleAnswer[][] myArr, int intRecurseLevel,
                    ArrayList<StratumGroup> sgList,
                    ArrayList<StratificationCriterionAnswerCombination> strLine) {
        StratificationCriterionAnswerCombination strPositionVal;// = new
                                                                // StratificationCriterionAnswerCombination();
        ArrayList<StratificationCriterionAnswerCombination> strMyLine;// = new
                                                                        // ArrayList<StratificationCriterionAnswerCombination>();
        int numOfSG = 0;
        for (int i = 0; i < myArr[intRecurseLevel].length; i++) {
            strPositionVal = new StratificationCriterionAnswerCombination();
            strMyLine = new ArrayList<StratificationCriterionAnswerCombination>();
            strPositionVal.setStratificationCriterionPermissibleAnswer(myArr[intRecurseLevel][i]);
            strPositionVal.setStratificationCriterion(te.getStratificationCriteria().get(
                            intRecurseLevel));

            if (!strLine.isEmpty()) {
                strMyLine.addAll(strLine);
            }
            strMyLine.add(strPositionVal);

            if (intRecurseLevel < myArr.length - 1) {
                // stepping into the next question
                sgList = comboGenerator(te, myArr, intRecurseLevel + 1, sgList, strMyLine);
            }
            else {
                // ran out of questions and hence now i have a combination of answers to save.
                // StratumGroup sg = new StratumGroup();
                // sg.getStratificationCriterionAnswerCombination().addAll(strMyLine);
                // sgList.add(sg);

                numOfSG = sgList.size();
                sgList.add(numOfSG, new StratumGroup());
                sgList.get(numOfSG).getStratificationCriterionAnswerCombination().addAll(
                                cloneScac(strMyLine));
                sgList.get(numOfSG).setStratumGroupNumber(numOfSG);
            }
        }
        return sgList;
    }

    public boolean hasBlankQuestionOrAnswer(TreatmentEpoch te) {
        List<StratificationCriterion> scList = te.getStratificationCriteria();
        List<StratificationCriterionPermissibleAnswer> scpaList;
        Iterator scIter = scList.iterator();
        Iterator scpaIter;
        StratificationCriterion sc;
        StratificationCriterionPermissibleAnswer scpa;
        while (scIter.hasNext()) {
            sc = (StratificationCriterion) scIter.next();
            if (StringUtils.isEmpty(sc.getQuestionText())) {
                return true;
            }
            scpaList = sc.getPermissibleAnswers();
            scpaIter = scpaList.iterator();
            while (scpaIter.hasNext()) {
                scpa = (StratificationCriterionPermissibleAnswer) scpaIter.next();
                if (StringUtils.isEmpty(scpa.getPermissibleAnswer())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<StratificationCriterionAnswerCombination> cloneScac(
                    List<StratificationCriterionAnswerCombination> scacList) {

        List<StratificationCriterionAnswerCombination> clonedList = new ArrayList<StratificationCriterionAnswerCombination>();
        Iterator<StratificationCriterionAnswerCombination> iter = scacList.iterator();
        while (iter.hasNext()) {
            clonedList.add(new StratificationCriterionAnswerCombination(iter.next()));
        }
        return clonedList;
    }

}
