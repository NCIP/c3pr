class CaptureNamePrefixAndSuffix extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn('participants','name_prefix','string');
		addColumn('participants','name_suffix','string');
		addColumn('stu_sub_demographics','name_prefix','string');
		addColumn('stu_sub_demographics','name_suffix','string');
		addColumn('investigators','name_prefix','string');
		addColumn('investigators','name_suffix','string');
		addColumn('persons_users','name_prefix','string');
		addColumn('persons_users','name_suffix','string');
	}
	void down() {
    }
}
