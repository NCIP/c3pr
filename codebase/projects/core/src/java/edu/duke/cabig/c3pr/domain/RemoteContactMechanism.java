/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.semanticbits.coppa.domain.annotations.RemoteEntity;
/**
 * This class was created for testing the collection handling capapbility of the COPPA 
 * interceptor framework. This functionality will be addressed in a future release. 
 * 
 * Although remote contacts will use this contact mechanism, it will not be marked as a remoteEntity(in app*-core settings).
 * It wont have a resolver or an external Id and the interceptor will not treat it as a remote entity. 
 * Meaning in order to update it, the parent Resolver (like InvestigatorResolver) will have to set it explicitly.
 * 
 * @author Vinay Gangoli
 */
@Entity
@DiscriminatorValue("Remote")
@RemoteEntity
public class RemoteContactMechanism extends ContactMechanism {


}
