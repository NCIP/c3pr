class SiteBannerDefaultImageUrl extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"siteName", value:"http://www.cancer.gov/images/banner_nci_logo_1.gif" ], primaryKey: false)
	}

}
