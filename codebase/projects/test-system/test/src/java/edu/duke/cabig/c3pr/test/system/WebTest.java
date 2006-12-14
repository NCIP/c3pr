/*
 * Created on Oct 25, 2006
 */
package edu.duke.cabig.c3pr.test.system;

import java.util.Vector;

import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import com.atomicobject.haste.framework.Story;

import edu.duke.cabig.c3pr.test.system.steps.WebCheckStep;
import edu.duke.cabig.c3pr.test.system.steps.WebCreateStep;

/**
 * This class validates the functionality of the Web Dummy class.
 * @testType integration
 * @steps WebCreateStep, WebCheckStep
 * @author Patrick McConnell
 */
public class WebTest
	extends Story
{
	public WebTest()
	{
		super();
	}

	protected boolean storySetUp() 
		throws Throwable
	{
		return true;
	}

	protected void storyTearDown() 
		throws Throwable
	{
	}
	
	@SuppressWarnings("unchecked")
	protected Vector steps()		
	{
		Vector steps = new Vector();
		
		WebCreateStep createStep = new WebCreateStep();
		steps.add(createStep);
		steps.add(new WebCheckStep(createStep));
		
		return steps;
	}

	public String getDescription()
	{
		return "WebTest";
	}
	
	/**
	 * Used to make sure that if we are going to use a junit testsuite to test
	 * this that the test suite will not error out looking for a single test.
	 */
	public void testDummy() throws Throwable {
	}
	
	public static void main(String[] args) throws Exception
	{
		TestRunner runner = new TestRunner();
		TestResult result = runner.doRun(new TestSuite(WebTest.class));
		System.exit(result.errorCount() + result.failureCount());
	}
}
