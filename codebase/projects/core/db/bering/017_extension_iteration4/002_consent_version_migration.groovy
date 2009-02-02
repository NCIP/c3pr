class ConsentVersionMigration extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        execute("insert into consent_history ('stu_sub_id','consent_version','consent_signed_date') select 'id','informed_consent_version','informed_consent_signed_date' from study_subjects")
    }

    void down() {
    }
}
