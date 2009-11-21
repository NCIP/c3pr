class UpdateSummary3DiseaseSites extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("update summ3_rep_disease_sites set name='Non-Hodgkins Lymphoma' where id=35");
    	execute("update summ3_rep_disease_sites set name='Hodgkins Lymphoma' where id=36");
    }

    void down() {
    }
}