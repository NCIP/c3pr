class UpdateColumnsDtypeOrganizations extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       	execute("update organizations set dtype='Local'");
    }
  
    void down() {
    	execute("update organizations set dtype=''");
    }
}
