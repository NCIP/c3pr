class UpdateSequencesNewTablesForStudyVersioning extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
      if (databaseMatches('postgres')){
    	  execute("select setVal('consents_id_seq',max(id)) from consents");
		  execute("select setVal('study_versions_id_seq',max(id)) from study_versions");
		  execute("select setVal('study_subject_consents_id_seq',max(id)) from study_subject_consents");
		  execute("select setVal('study_site_versions_id_seq',max(id)) from study_site_versions");
		  execute("select setVal('study_subject_versions_id_seq',max(id)) from study_subject_versions");
		  execute("select setVal('stu_site_status_history_id_seq',max(id)) from stu_site_status_history");
		}
	}

	void down() {
    }
}
