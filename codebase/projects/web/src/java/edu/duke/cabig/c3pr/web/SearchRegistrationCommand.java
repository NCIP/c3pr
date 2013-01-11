/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web;

/**
 * @author Priyatam
 */

public class SearchRegistrationCommand extends SearchCommand {

    private String select;

    private String subjectOption;

    private String studyOption;

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getStudyOption() {
        return studyOption;
    }

    public void setStudyOption(String studyOption) {
        this.studyOption = studyOption;
    }

    public String getSubjectOption() {
        return subjectOption;
    }

    public void setSubjectOption(String subjectOption) {
        this.subjectOption = subjectOption;
    }

}
