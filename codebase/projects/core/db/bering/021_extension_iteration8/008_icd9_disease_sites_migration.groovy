class ICD9DiseaseSitesMigration extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("update disease_history set other_disease_site_code =NULL where other_disease_site_code like '(Begin typing here)' or other_disease_site_code like ''");
    	execute("update disease_history set other_disease_site_code = (select anatomic_sites.name from anatomic_sites where disease_history.anatomic_site_id = anatomic_sites.id)");
		execute("alter table disease_history add icd9_disease_site_id integer");
    	execute("alter table disease_history add constraint FK_DHI_ICD9 FOREIGN KEY (icd9_disease_site_id) REFERENCES icd9_disease_sites(ID)");
    }

	void down() {
	}
}