class AddOffEpochDateAndOffEpochReasonTextToScheduledEpochs extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        addColumn('scheduled_epochs','off_epoch_date','date');
        addColumn('scheduled_epochs','off_epoch_reason_text','string',limit: 2000);
    }

    void down() {
    	 dropColumn('scheduled_epochs','off_epoch_date');
    	 dropColumn('scheduled_epochs','off_epoch_reason_text');
    }
}