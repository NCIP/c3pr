/*
 * Created on Oct 25, 2006
 */
package edu.duke.cabig.c3prv2.web;

import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * This class validates the functionality of the Dummy class.
 * @testType unit
 * @author Patrick McConnell
 */
public class DummyTest
	extends TestCase
{
	public DummyTest(String name)
	{
		super(name);
	}
	
	public void testDummy()
	{
		assertEquals("please delete me", new Dummy().dummy());
	}
	
	public static void main(String[] args) throws Exception
	{
		TestRunner runner = new TestRunner();
		TestResult result = runner.doRun(new TestSuite(DummyTest.class));
		System.exit(result.errorCount() + result.failureCount());
	}
}
