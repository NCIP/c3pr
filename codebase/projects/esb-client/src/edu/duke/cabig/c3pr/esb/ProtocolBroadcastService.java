package edu.duke.cabig.c3pr.esb;
import edu.duke.cabig.c3pr.domain.Study;

public interface ProtocolBroadcastService {
	
	public void broadcast(Study study);
	
	public void getBroadcastStatus();

}
