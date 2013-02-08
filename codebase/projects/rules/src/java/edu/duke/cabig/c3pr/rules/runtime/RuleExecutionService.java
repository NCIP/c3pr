/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.rules.runtime;

import gov.nih.nci.cabig.c3pr.rules.domain.AdverseEventSDO;
import gov.nih.nci.cabig.c3pr.rules.domain.StudySDO;

import java.rmi.RemoteException;
import java.util.List;

public interface RuleExecutionService {

    /*
     * public abstract void fireRules(RuleExecutionInfo ruleSetInfo) throws RemoteException;
     *
     */

    /**
     * Fire all the rules in the RuleExecutionSet referred by the bindingUri.
     *
     */
    public abstract void fireRules(String bindingUri, StudySDO study,
                    List<AdverseEventSDO> inputObjects) throws RemoteException;

    // public abstract void fireRules(String bindingUri, List<? extends Object> inputObjects) throws
    // RemoteException;

    /**
     * Fire all the rules in the RuleExecutionSet referred by the bindingUri.
     *
     */
    /*
     * public abstract List<Object> fireRules(String bindingUri, List<Object> inputObjects, Map<String,
     * Object> properties) throws RemoteException;
     */
    /**
     * Fire all the rules in the RuleExecutionSet referred by the bindingUri.
     *
     */
    /*
     * public abstract void fireRules(String bindingUri, List<Object> inputObjects, ObjectFilter
     * objectFilter) throws RemoteException;
     */
    /*
     * public abstract String fireRules(String bindUri);
     */
}
