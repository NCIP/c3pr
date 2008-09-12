class LocalNCIInstituteCode extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('organizations', [ id: 16999, nci_institute_code: "NCI", name: "National Cancer Institute" ,address_id: 16999,version:0], primaryKey: false)
	}

	void down(){
        	execute("delete from organizations where id IN (16999)");
	}
}
