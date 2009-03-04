class SiteBanner extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			execute("update configuration set value='/c3pr/images/nci_logo_35pxh.png' where prop='siteName'");
	}

	void down(){
	}
}
