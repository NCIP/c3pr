class DeleteFromICD9DiseaseSitesUpdateDiseaseHistory extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute('update disease_history set icd9_disease_site_id=null');
    	execute('delete from icd9_disease_sites');
	}

	void down() {
    }
}
