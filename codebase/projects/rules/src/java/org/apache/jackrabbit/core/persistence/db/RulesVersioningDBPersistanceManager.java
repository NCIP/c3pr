/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package org.apache.jackrabbit.core.persistence.db;

import java.util.Properties;

import edu.duke.cabig.c3pr.service.impl.RulesDelegationServiceImpl;

public class RulesVersioningDBPersistanceManager extends SimpleDbPersistenceManager {

    public RulesVersioningDBPersistanceManager() {

        Properties props = new Properties();
        RulesDelegationServiceImpl.loadConfiguration(props);
        
        this.driver = props.getProperty("datasource.driver");
        this.password = props.getProperty("datasource.password");
        this.user = props.getProperty("datasource.username");
        this.schema = "postgresql";
        this.schemaObjectPrefix = "rep_ver_";
        this.url = props.getProperty("datasource.url");
        this.externalBLOBs = false;
    	
    	/*this.driver = driverClassName; //"org.postgresql.Driver";
        this.password = password;//"postgres";
        this.user = username;//"postgres";
        this.schema = "postgresql";
        this.schemaObjectPrefix = "rep_ver_";
        this.url = url;//"jdbc:postgresql://localhost:5432/c3pr_svn";
        this.externalBLOBs = false;*/
    }

}
