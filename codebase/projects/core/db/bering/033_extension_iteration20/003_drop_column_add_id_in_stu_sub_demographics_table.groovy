class DropColumnAddIdInStuSubDemographicsTable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		execute("alter table stu_sub_demographics drop column add_id");
	}
	void down() {
    }
}
