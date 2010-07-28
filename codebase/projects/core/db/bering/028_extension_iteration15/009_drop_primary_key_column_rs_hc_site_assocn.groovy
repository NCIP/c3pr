class dropIdColumnResearchStaffHealthcareSiteAssociationTable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	   dropColumn('rs_hc_site_assocn','id');
    }

    void down() {
    	addColumn('rs_hc_site_assocn','id','integer');
    }
}
