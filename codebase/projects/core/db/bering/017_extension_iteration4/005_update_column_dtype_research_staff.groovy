class UpdateColumnsDtypeResearchStaff extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       	execute("update research_staff set dtype='Local' where dtype='local'");
    }
  
    void down() {
    	execute("update research_staff set dtype='local' where dtype='Local'");
    }
}
