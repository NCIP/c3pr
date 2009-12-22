class UpdateSequencesNewTablesForStudyVersioning extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
      if (databaseMatches('postgres')){
	    	select setVal('consents_id_seq',max(id)) from consents;
			select setVal('study_versions_id_seq',max(id)) from study_versions;
			select setVal('study_subject_consents_id_seq',max(id)) from study_subject_consents;
			select setVal('study_site_versions_id_seq',max(id)) from study_site_versions;
			select setVal('study_subject_versions_id_seq',max(id)) from study_subject_versions;
			select setVal('stu_site_status_history_id_seq',max(id)) from stu_site_status_history;
		}
	}

	void down() {
    }
}
