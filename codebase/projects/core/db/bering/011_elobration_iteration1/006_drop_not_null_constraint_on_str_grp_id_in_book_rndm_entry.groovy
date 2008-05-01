class dropNotNullConstraintOnStratumGroupIdInBookRandomizationEntry extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       setNullable('book_rndm_entry','str_grp_id', true);
    }

    void down() {
    }
}