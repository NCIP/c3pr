/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.factory;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;


public class StudySiteBiDirectionalInstantiateFactory extends ParameterizedBiDirectionalInstantiateFactory<StudySite> {

	public StudySiteBiDirectionalInstantiateFactory( Class<StudySite> classToInstantiate, Object parent) {
		super(classToInstantiate, parent);
	}
	
	public StudySiteBiDirectionalInstantiateFactory(Class<StudySite> classToInstantiate, Object parent,
            String biDirectionalPropertyName, Class biDirectionalClassName){
		super(classToInstantiate, parent, biDirectionalPropertyName, parent.getClass());
	}
	
	@Override
	public StudySite create() {
		 StudySite studySite = super.create();
		 studySite.setup((Study)parent);
		 return studySite;
	}

}
