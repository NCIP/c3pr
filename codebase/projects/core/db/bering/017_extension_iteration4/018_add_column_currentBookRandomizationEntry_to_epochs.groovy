class AddColumnCurrentBookRandomizationEntryToEpochs extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       	addColumn("epochs","current_book_randomization_entry","integer");
    }
  
    void down() {
        dropColumn("epochs","current_book_randomization_entry");
    }
}

