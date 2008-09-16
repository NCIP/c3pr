package org.apache.jackrabbit.core.persistence.db;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.rules.repository.RepositoryServiceImpl;

public class RulesVersioningDBPersistanceManager extends SimpleDbPersistenceManager {

	private static Logger logger = Logger.getLogger(RepositoryServiceImpl.class);
	
    public RulesVersioningDBPersistanceManager() {


        Properties props = new Properties();
        try{
        	props.load(this.getClass().getClassLoader().getResourceAsStream("context/datasource.properties"));
        }catch(IOException ioe){
        	logger.error(ioe.getMessage());
        }    
        
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