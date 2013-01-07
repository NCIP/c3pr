package org.apache.jackrabbit.core.persistence.db;


import java.util.Properties;

import edu.duke.cabig.c3pr.service.impl.RulesDelegationServiceImpl;

public class RulesOracleDBPersistanceManager extends OraclePersistenceManager {

    public RulesOracleDBPersistanceManager() {

    	Properties props = new Properties();
        RulesDelegationServiceImpl.loadConfiguration(props);

        this.driver = props.getProperty("datasource.driver");
        this.password = props.getProperty("datasource.password");
        this.user = props.getProperty("datasource.username");
        this.schema = "oracle";
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