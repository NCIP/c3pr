class linkScheduledNotificationToStudySite extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	
		addColumn("schld_notfns", "study_org_id", "integer", nullable : true)
		execute("ALTER TABLE schld_notfns ADD CONSTRAINT FK_SCNOT_STO_ASSOC FOREIGN KEY (study_org_id) REFERENCES study_organizations (ID)");
	}

	void down(){
		execute(" ALTER TABLE schld_notfns drop CONSTRAINT FK_SCNOT_STO_ASSOC");
		dropColumn("schld_notfns", "study_org_id");
	}
}
