class DropScheduledEpochsStudySubjectVersionIDNotNullConstraint extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
     if (databaseMatches('postgres')){
       		execute("alter table scheduled_epochs alter column study_subject_ver_id drop not null");
       	}
     if (databaseMatches('oracle')){
       		execute("alter table scheduled_epochs modify study_subject_ver_id null");
       	}
    }
  
    void down() {
    }
}