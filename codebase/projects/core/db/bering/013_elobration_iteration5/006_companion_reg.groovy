class columnToTrackRequiredChildReg extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		addColumn('stu_sub_associations','reg_from_parent_indicator', 'boolean');
		if (databaseMatches('oracle')) {
        	execute("ALTER TABLE stu_sub_associations ALTER COLUMN reg_from_parent_indicator SET DEFAULT 0")
        } else if (databaseMatches('postgresql')){
        	execute("ALTER TABLE stu_sub_associations ALTER COLUMN reg_from_parent_indicator SET DEFAULT false")
        } else {
        	execute("ALTER TABLE stu_sub_associations ALTER COLUMN reg_from_parent_indicator SET DEFAULT false")
        }
	}

	void down(){
		dropColumn('stu_sub_associations','reg_from_parent_indicator');
	}
}
