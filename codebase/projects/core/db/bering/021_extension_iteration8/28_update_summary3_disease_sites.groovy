class UpdateSummary3DiseaseSites extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("update summ3_rep_disease_sites set name='Non-Hodgkin's Lymphoma' where id=35;");
    	execute("update summ3_rep_disease_sites set name='Hodgkin's Lymphoma' where id=36");
    }

    void down() {
    }
}