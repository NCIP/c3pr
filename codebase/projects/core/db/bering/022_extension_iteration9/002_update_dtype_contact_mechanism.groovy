class UpdateDtypeContactMechanism extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       	execute("update contact_mechanisms set dtype='Local' where dtype is null");
    }
  
    void down() {
    	execute("update contact_mechanisms set dtype='' where dtype='Local'");
    }
}