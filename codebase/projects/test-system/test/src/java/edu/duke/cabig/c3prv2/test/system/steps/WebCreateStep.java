/*
 * Created on Oct 25, 2006
 */
package edu.duke.cabig.c3prv2.test.system.steps;

import com.atomicobject.haste.framework.Step;

import edu.duke.cabig.c3prv2.web.Dummy;

/**
 * This step creates a new Dummy object.
 * @author Patrick McConnell
 */
public class WebCreateStep
	extends Step
{
	private Dummy dummy;

	public WebCreateStep()
	{
		super();
	}
	
	public void runStep()
	{
		this.dummy = new Dummy();
	}
	
	public Dummy getDummy()
	{
		return dummy;
	}
}
