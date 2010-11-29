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
		
		if (databaseMatches('oracle')){
		  execute("DROP SEQUENCE study_subject_consents_id_seq");
          execute("CREATE SEQUENCE study_subject_consents_id_seq start with 15000 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");
		  
		  execute("DROP SEQUENCE study_site_versions_id_seq");
	      execute("CREATE SEQUENCE study_site_versions_id_seq start with 15000 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");
            
          execute("DROP SEQUENCE study_subject_versions_id_seq");
          execute("CREATE SEQUENCE study_subject_versions_id_seq start with 15000 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");
            
          execute("DROP SEQUENCE stu_site_status_history_id_seq");
          execute("CREATE SEQUENCE stu_site_status_history_id_seq start with 15000 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");
            
		}
	}

	void down() {
    }
}
