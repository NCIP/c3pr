/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.rules.runtime;

public enum Global {

    RULE_CONTEXT("ruleContext", "",
                    "Contextual Information which is shared across all the Rules being fired."), ACTION_DISPATCHER(
                    "actionDispatcher", "", ""), ADVERSE_EVENT_RESULT(
                    "adverseEventEvaluationResult", "", "");

    private String code;

    private String displayResourceUri;

    private String description;

    Global(String code) {
        this(code, "", "");
    }

    Global(String code, String displayResourceUri) {
        this(code, "", displayResourceUri);
    }

    Global(String code, String displayResourceUri, String description) {
        this.code = code;
        this.displayResourceUri = displayResourceUri;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayResourceUri() {
        return displayResourceUri;
    }

}
