/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.rules.runtime;

import edu.duke.cabig.c3pr.audit.RuleWorkingMemoryLogger;
import edu.duke.cabig.c3pr.rules.exception.RuleException;
import edu.duke.cabig.c3pr.rules.repository.RepositoryServiceImpl;
import edu.duke.cabig.c3pr.rules.repository.RuleExecutionSetRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleExecutionSetNotFoundException;
import javax.rules.StatelessRuleSession;

import org.apache.log4j.Logger;
import org.drools.FactException;
import org.drools.WorkingMemory;
import org.drools.jsr94.rules.admin.RuleExecutionSetImpl;

/**
 * StatelessRuleSession implementation supporting a custom repository.
 *
 * Since our custom repository has handle to the compiled package, its possible for us to execute
 * individual rules.
 */
public class StatelessRuleSessionImpl extends AbstractRuleSessionImpl implements
                StatelessRuleSession {

    private String bindUri;
    private static Logger logger = Logger.getLogger(RepositoryServiceImpl.class);


    /**
     * Gets the <code>RuleExecutionSet</code> for this URI and associates it with a RuleBase.
     *
     * @param bindUri
     *                the URI the <code>RuleExecutionSet</code> has been bound to
     * @param properties
     *                additional properties used to create the <code>RuleSession</code>
     *                implementation.
     *
     * @throws RuleExecutionSetNotFoundException
     *                 if there is no rule set under the given URI
     */
    StatelessRuleSessionImpl(final String bindUri, final Map properties,
                    final RuleExecutionSetRepository repository)
                    throws RuleExecutionSetNotFoundException {
        super(repository);
        this.setProperties(properties);
        
        try{
        	final RuleExecutionSetImpl ruleSet = (RuleExecutionSetImpl) repository.getRuleExecutionSet(bindUri);
        	
            if (ruleSet == null) {
                throw new RuleExecutionSetNotFoundException("RuleExecutionSet unbound: " + bindUri);
            }

            this.setRuleExecutionSet(ruleSet);
            this.bindUri = bindUri;
        }catch(RuleException re){
        	logger.error(re.getMessage());
        }
        

    }

    /**
     * Executes the rules in the bound rule execution set using the supplied list of objects. A
     * <code>List</code> is returned containing the objects created by (or passed into the rule
     * session) the executed rules that pass the filter test of the default
     * <code>RuleExecutionSet</code>
     * <code>ObjectFilter</code> (if present). <p/> The returned
     * list may not neccessarily include all objects passed, and may include <code>Object</code>s
     * created by side-effects. The execution of a <code>RuleExecutionSet</code> can add, remove
     * and update objects. Therefore the returned object list is dependent on the rules that are
     * part of the executed <code>RuleExecutionSet</code> as well as Drools specific rule engine
     * behavior.
     *
     * @param objects
     *                the objects used to execute rules.
     *
     * @return a <code>List</code> containing the objects as a result of executing the rules.
     *
     * @throws InvalidRuleSessionException
     *                 on illegal rule session state.
     */
    public List executeRules(final List objects) throws InvalidRuleSessionException {
        return this.executeRules(objects, this.getRuleExecutionSet().getObjectFilter());
    }

    /**
     * Executes the rules in the bound rule execution set using the supplied list of objects. A
     * <code>List</code> is returned containing the objects created by (or passed into the rule
     * engine) the executed rules and filtered with the supplied object filter. <p/> The returned
     * list may not neccessarily include all objects passed, and may include <code>Object</code>s
     * created by side-effects. The execution of a <code>RuleExecutionSet</code> can add, remove
     * and update objects. Therefore the returned object list is dependent on the rules that are
     * part of the executed <code>RuleExecutionSet</code> as well as Drools specific rule engine
     * behavior.
     *
     * @param objects
     *                the objects used to execute rules.
     * @param filter
     *                the object filter.
     *
     * @return a <code>List</code> containing the objects as a result of executing rules, after
     *         passing through the supplied object filter.
     *
     * @throws InvalidRuleSessionException
     *                 on illegal rule session state.
     */
    @SuppressWarnings("unchecked")
    public List executeRules(final List objects, final ObjectFilter filter)
                    throws InvalidRuleSessionException {
        final WorkingMemory workingMemory = this.newWorkingMemory();
        RuleWorkingMemoryLogger ruleLogger;

        try {
            ruleLogger = new RuleWorkingMemoryLogger(workingMemory, bindUri);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new InvalidRuleSessionException(e.getMessage(), e);
        }

        try {
            for (final Iterator objectIter = objects.iterator(); objectIter.hasNext();) {
        //        workingMemory.assertObject(objectIter.next());
//            	//4.0.6
            	workingMemory.insert(objectIter.next());
            }

            workingMemory.fireAllRules();
        } catch (final FactException e) {
            throw new InvalidRuleSessionException(e.getMessage(), e);
        }

        final List results = new ArrayList();
        Iterator itr = workingMemory.iterateObjects((org.drools.ObjectFilter) filter);//getObjects();

        //this.applyFilter(results, filter);

        while (itr.hasNext()) {
        	results.add(itr.next());
        }

        try {

            ruleLogger.logExecutionSummary(objects);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new InvalidRuleSessionException(e.getMessage(), e);
        }

        return results;
    }

}
