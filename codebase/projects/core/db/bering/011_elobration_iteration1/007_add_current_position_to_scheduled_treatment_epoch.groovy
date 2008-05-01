class addCurrentPositionToScheduledTreatmentEpoch extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        addColumn('scheduled_epochs','current_position','integer');
    }

    void down() {
       dropColumn('scheduled_epochs','current_position');
    }
}