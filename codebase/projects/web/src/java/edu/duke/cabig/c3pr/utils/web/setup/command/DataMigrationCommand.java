/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.setup.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.apache.commons.collections15.list.LazyList;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.OffEpochReason;
import edu.duke.cabig.c3pr.domain.OffTreatmentReason;

public class DataMigrationCommand {

	public static final int EPOCH_TYPE_EMPTY = 1;
	
	public static final int OFF_EPOCH_REASON_EMPTY = 2;
	
	private int migrationType;
	
	List<Epoch> epochs = LazyList.decorate(new ArrayList<Epoch>(), new InstantiateFactory<Epoch>(Epoch.class));
	
	List<OffEpochReason> offEpochReasons = LazyList.decorate(new ArrayList<OffEpochReason>(), new InstantiateFactory<OffEpochReason>(OffEpochReason.class){
																										@Override
																										public OffEpochReason create() {
																											OffEpochReason offEpochReason = super.create();
																											offEpochReason.setReason(new OffTreatmentReason());
																											return offEpochReason;
																										}
																									});

	public List<OffEpochReason> getOffEpochReasons() {
		return offEpochReasons;
	}

	public void setOffEpochReasons(List<OffEpochReason> offEpochReasons) {
		this.offEpochReasons = offEpochReasons;
	}

	public List<Epoch> getEpochs() {
		return epochs;
	}

	public void setEpochs(List<Epoch> epochs) {
		this.epochs = epochs;
	}

	public int getMigrationType() {
		return migrationType;
	}

	public void setMigrationType(int migrationType) {
		this.migrationType = migrationType;
	}

}
