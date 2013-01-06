class AddEventIdToScheduledNotifications extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		addColumn("schld_notfns","event_id","string");
	}

	void down(){
		removeColumn("schld_notfns","event_id");
	}
}