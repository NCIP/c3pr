class companionProtocolAmendmentUpdate extends edu.northwestern.bioinformatics.bering.Migration {
    void down() {
    	dropColumn("study_amendments", "comp_changed_indicator")
    }
  
    void up() {
    	addColumn("study_amendments", "comp_changed_indicator", "boolean" )
    }
}
