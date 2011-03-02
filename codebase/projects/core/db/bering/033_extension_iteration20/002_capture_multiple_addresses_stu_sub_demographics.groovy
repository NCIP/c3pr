class CaptureMultipleAddressesStuSubDemographics extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn('addresses','stu_sub_demographics_id','integer');
		if (databaseMatches('postgres')){
			execute("update addresses set stu_sub_demographics_id = ssd.id from stu_sub_demographics ssd where addresses.id = ssd.add_id and ssd.add_id is not null");
		}
		
		if (databaseMatches('oracle	')){
			execute("update addresses set stu_sub_demographics_id = (select stu_sub_demographics.id from stu_sub_demographics where addresses.id = stu_sub_demographics.add_id and stu_sub_demographics.add_id is not null)");
		}
		execute("ALTER TABLE addresses ADD CONSTRAINT fk_add_stu_sub_dmghcs FOREIGN KEY (stu_sub_demographics_id) REFERENCES stu_sub_demographics (ID)");
	}
	void down() {
    }
}
