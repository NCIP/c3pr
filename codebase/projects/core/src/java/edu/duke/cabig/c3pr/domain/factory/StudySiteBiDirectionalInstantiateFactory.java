package edu.duke.cabig.c3pr.domain.factory;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;


public class StudySiteBiDirectionalInstantiateFactory extends ParameterizedBiDirectionalInstantiateFactory<StudySite> {

	public StudySiteBiDirectionalInstantiateFactory( Class<StudySite> classToInstantiate, Object parent) {
		super(classToInstantiate, parent);
	}
	
	@Override
	public StudySite create() {
		 StudySite studySite = super.create();
		 studySite.setup((Study)parent);
		 return studySite;
	}

}