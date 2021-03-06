/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package org.apache.jackrabbit.core.persistence.db;


import java.util.Properties;

import edu.duke.cabig.c3pr.service.impl.RulesDelegationServiceImpl;

public class RulesSQLServerDBPersistanceManager extends SimpleDbPersistenceManager {

    public RulesSQLServerDBPersistanceManager() {

    	Properties props = new Properties();
        RulesDelegationServiceImpl.loadConfiguration(props);

        this.driver = props.getProperty("datasource.driver");
        this.password = props.getProperty("datasource.password");
        this.user = props.getProperty("datasource.username");
        this.schema = "mssql";
        this.schemaObjectPrefix = "rep_";
        this.url = props.getProperty("datasource.url");
        this.externalBLOBs = false;
    	
        /*this.driver = "org.postgresql.Driver";
        this.password = "postgres";
        this.user = "postgres";
        this.schema = "postgresql";
        this.schemaObjectPrefix = "rep_";
        this.url = "jdbc:postgresql://localhost:5432/c3pr_svn";
        this.externalBLOBs = false;*/
    }

}
