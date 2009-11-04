class CompanionStudySiteAssociation extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			addColumn("STUDY_ORGANIZATIONS","comp_assoc_id", "integer")			
	}

	void down(){
	        dropColumn("STUDY_ORGANIZATIONS","comp_assoc_id")
	}
}
