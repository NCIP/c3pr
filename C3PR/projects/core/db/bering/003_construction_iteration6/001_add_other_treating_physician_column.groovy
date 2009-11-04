class AddOtherTreatingPhysicianColumn extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
         addColumn('study_subjects','other_treating_physician','string');
    }

    void down() {
        dropColumn('study_subjects','other_treating_physician');
    }
}

