class addStratificationIndicatorToStudy extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       addColumn('studies','stratification_indicator','boolean');
    }

    void down() {
       dropColumn('studies','stratification_indicator');
    }
}