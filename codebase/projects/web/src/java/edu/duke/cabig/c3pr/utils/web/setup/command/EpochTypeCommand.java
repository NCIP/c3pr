package edu.duke.cabig.c3pr.utils.web.setup.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.apache.commons.collections15.list.LazyList;

import edu.duke.cabig.c3pr.domain.Epoch;

public class EpochTypeCommand {

	List<Epoch> epochs = LazyList.decorate(new ArrayList<Epoch>(), new InstantiateFactory<Epoch>(Epoch.class));

	public List<Epoch> getEpochs() {
		return epochs;
	}

	public void setEpochs(List<Epoch> epochs) {
		this.epochs = epochs;
	}

}
