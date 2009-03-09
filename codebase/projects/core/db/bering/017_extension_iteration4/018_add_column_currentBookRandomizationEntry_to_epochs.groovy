class AddColumnCurrentBookRandomizationEntryToEpochs extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       	addColumn("epochs","current_bk_rand_entry","integer");
    }
  
    void down() {
        dropColumn("epochs","current_bk_rand_entry");
    }
}

