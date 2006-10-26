/*
 * Created on Oct 25, 2006
 */
package edu.duke.cabig.c3prv2.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A test servlet that should be deleted.
 * @author Patrick McConnell
 */
public class DummyServlet extends HttpServlet
{
	public DummyServlet()
	{
		super();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		out.println("This is DummyServlet.  It really should be deleted.");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		doGet(request, response);
	}
}
