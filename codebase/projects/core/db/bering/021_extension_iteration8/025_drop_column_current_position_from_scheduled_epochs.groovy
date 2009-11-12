class DropColumnCurrentPositionFromScheduledEpochs extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	dropColumn('scheduled_epochs','current_position')
	}

	void down() {
    }
}
