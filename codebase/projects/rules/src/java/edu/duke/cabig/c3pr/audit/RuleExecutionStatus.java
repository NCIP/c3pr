/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.audit;

public class RuleExecutionStatus {

    private String ruleName;

    private boolean conditionMet;

    private boolean fired;

    public RuleExecutionStatus() {

    }

    public boolean isConditionMet() {
        return conditionMet;
    }

    public void setConditionMet(boolean conditionMet) {
        this.conditionMet = conditionMet;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

}
