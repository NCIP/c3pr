class CreateTableSiteInvestigatorGroupAffiliations extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    execute("alter table investigator_groups add constraint FK_ING_ORG FOREIGN KEY (hcs_id) REFERENCES organizations(ID)")
	    execute("alter table org_inv_gr_affiliations add constraint FK_SIG_ING FOREIGN KEY (ing_ID) REFERENCES investigator_groups(ID)")
	    execute("alter table org_inv_gr_affiliations add constraint FK_SIG_ORG FOREIGN KEY (hsi_ID) REFERENCES hc_site_investigators(ID)")
    }
    void down() {
	    execute("alter table org_inv_gr_affiliations drop constraint FK_SIG_ORG")
	    execute("alter table org_inv_gr_affiliations drop constraint FK_SIG_ING")
	    execute("alter table investigator_groups drop constraint FK_ING_ORG")
    }
}
