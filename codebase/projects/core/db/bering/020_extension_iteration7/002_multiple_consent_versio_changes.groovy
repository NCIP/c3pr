class MultipleConsentVersionChanges extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	  dropColumn('studies', 'consent_validity_period')
          addColumn('studies','consent_validity_period','integer');
          addColumn('consents','mandatory_indicator','boolean');
          addColumn('studies','consent_required', 'string');
        
    }

    void down() {
      dropColumn('studies', 'consent_validity_period')
      addColumn('studies','consent_validity_period','string');
      dropColumn('consents','mandatory_indicator');
      dropColumn('studies','consent_required');
    }
}