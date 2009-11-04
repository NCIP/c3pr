class dropIdColumnStudySubjectAssociations extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	   dropColumn('stu_sub_associations','id');
    }

    void down() {
    	addColumn('stu_sub_associations','id','integer');
    }
}
