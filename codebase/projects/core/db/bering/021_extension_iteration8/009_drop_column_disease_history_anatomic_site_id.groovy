class DropColumnDiseaseHistoryAnatomicSiteId extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	 execute("alter table disease_history drop column anatomic_site_id");
    	 execute("drop table anatomic_sites cascade");
    	 execute("drop sequence anatomic_sites_id_seq cascade");
    } 

	void down() {
	}
}