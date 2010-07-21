class dropNotNullConstraintOnStratumGroupIdInBookRandomizationEntry extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       setNullable('sub_con_que_ans','agreement_indicator', true);
    }

    void down() {
    }
}