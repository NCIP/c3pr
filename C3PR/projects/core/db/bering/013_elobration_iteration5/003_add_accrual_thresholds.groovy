class AddAccrualThresholds extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		renameColumn('planned_notfns', 'threshold', 'study_threshold');
		addColumn('planned_notfns','study_site_threshold', 'integer');
	}

	void down(){
		renameColumn('planned_notfns', 'study_threshold', 'threshold');
		dropColumn('planned_notfns','study_site_threshold');

	}
}
