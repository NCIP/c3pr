class AddCommentStudySubjectRegistryStatus extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn('STU_SUB_REG_STATUSES','comment_text','string');
	}
	void down() {
		dropColumn('STU_SUB_REG_STATUSES','comment_text');
    }
}
