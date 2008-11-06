class CompanionStudySiteAssociation extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			addColumn('study_sites','comp_assoc_id')			
	}

	void down(){
	        dropColumn('study_sites','comp_assoc_id')
	}
}
