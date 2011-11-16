class AddAdditionalColumnsToCorrespondences extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn('correspondences','follow_up_needed','boolean');
		addColumn('correspondences','resolved','boolean');
		addColumn('correspondences','person_spoken_to_id','integer');
		addColumn('correspondences','time_zone','string');
		addColumn('correspondences','start_time_hours','integer');
		addColumn('correspondences','start_time_minutes','integer');
		addColumn('correspondences','start_time_am_pm','string');
		addColumn('correspondences','end_time_hours','integer');
		addColumn('correspondences','end_time_minutes','integer');
		addColumn('correspondences','end_time_am_pm','string');
		
		execute("ALTER TABLE correspondences ADD CONSTRAINT FK_COR_PER_USR FOREIGN KEY (person_spoken_to_id) REFERENCES persons_users(ID)");
	}
	void down() {
    }
}
