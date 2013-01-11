/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.rules.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.WorkingMemory;

/**
 * A carrier of information when rule is getting fired.
 * 
 * This will be made available as a global. So that any information that needs at action processing
 * can be added to this.
 * 
 * @author Sujith Vellat Thayyilthodi
 */
public class RuleContext {

    public Map eventMap = new HashMap();

    private List inputObjects;

    private WorkingMemory workingMemory;

    public List getInputObjects() {
        return inputObjects;
    }

    public void setInputObjects(List inputObjects) {
        this.inputObjects = inputObjects;
    }

    public WorkingMemory getWorkingMemory() {
        return workingMemory;
    }

    public void setWorkingMemory(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

}
