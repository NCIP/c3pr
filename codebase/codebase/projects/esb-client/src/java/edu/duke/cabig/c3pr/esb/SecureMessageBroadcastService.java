package edu.duke.cabig.c3pr.esb;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Dec 14, 2007
 * Time: 5:06:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SecureMessageBroadcastService extends MessageBroadcastService {

    public void setDelegatedCredentialProvider(DelegatedCredentialProvider delegatedCredentialProvider);


}
