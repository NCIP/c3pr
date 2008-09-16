package org.apache.jackrabbit.core.persistence.db;


import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.rules.repository.RepositoryServiceImpl;

public class RulesOracleDBPersistanceManager extends OraclePersistenceManager {

	private static Logger logger = Logger.getLogger(RepositoryServiceImpl.class);
	
    public RulesOracleDBPersistanceManager() {

        Properties props = new Properties();
        try{
        	props.load(this.getClass().getClassLoader().getResourceAsStream("context/datasource.properties"));
        }catch(IOException ioe){
        	logger.error(ioe.getMessage());
        }

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