class MigrateToStudyVersionFropColumns1 extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
      	addColumn('study_versions', 'name', 'string');
    }

	void down() {
	    dropColumn('study_versions', 'name');
    }
} 