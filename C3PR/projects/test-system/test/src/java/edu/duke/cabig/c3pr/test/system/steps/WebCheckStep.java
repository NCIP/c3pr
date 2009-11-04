/*
 * Created on Oct 25, 2006
 */
package edu.duke.cabig.c3pr.test.system.steps;

import com.atomicobject.haste.framework.Step;

/**
 * This step checks that the Dummy object is performing correctly
 * @author Patrick McConnell
 */
public class WebCheckStep
	extends Step
{
	private WebCreateStep createStep;

	public WebCheckStep(WebCreateStep createStep)
	{
		super();
		
		this.createStep = createStep;
	}
	
	public void runStep()
	{
		//assertEquals("please delete me", createStep.getDummy().dummy());
	}
}
