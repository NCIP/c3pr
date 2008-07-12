class RenameEpochsTypeAndDropScheduledEpochsDtype extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		renameColumn('epochs', 'type', 'display_role');
		dropColumn('scheduled_epochs','TYPE');
	}

	void down(){
		renameColumn('epochs', 'type', 'display_role');
		addColumn('scheduled_epochs','TYPE',"string");

	}
}
