class UpdateRetiredIndicatorOrganizationsIdentifiers extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       	execute("update organizations set retired_indicator='false' where retired_indicator is null or retired_indicator like ''");
       	execute("update identifiers set hcs_id =16831 where dtype like 'OAI' and hcs_id is null");
    }
  
    void down() {
    }
}