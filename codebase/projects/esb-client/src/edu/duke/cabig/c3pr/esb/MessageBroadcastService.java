package edu.duke.cabig.c3pr.esb;

import java.util.Vector;


public interface MessageBroadcastService {
	
	public void broadcast(String message) throws BroadcastException;
	
	public Vector getBroadcastStatus();

}
