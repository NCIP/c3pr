class UpdateColumnsDtypeInvestigators extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       	execute("update investigators set dtype='Local'");
    }
  
    void down() {
    	execute("update investigators set dtype=''");
    }
}
