package edu.duke.cabig.c3pr.esb;


public interface MessageBroadcastService {
	
	public void broadcast(String message) throws BroadcastException;
	
	public void getBroadcastStatus();

}
