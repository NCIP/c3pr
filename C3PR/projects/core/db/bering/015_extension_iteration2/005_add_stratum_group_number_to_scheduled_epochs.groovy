class  AddStratumGroupNumberToScheduledEpochs extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    	addColumn("scheduled_epochs","stratum_group_number","integer");
    }
  
    void down() {
    		dropColumn("scheduled_epochs","stratum_group_number");
    }
}
