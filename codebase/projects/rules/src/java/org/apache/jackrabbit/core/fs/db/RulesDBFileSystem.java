package org.apache.jackrabbit.core.fs.db;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.rules.repository.RepositoryServiceImpl;

public class RulesDBFileSystem extends DbFileSystem {

	private static Logger logger = Logger.getLogger(RepositoryServiceImpl.class);

    public RulesDBFileSystem() {

        /*Properties props = new Properties();        
        try{
        	props.load(this.getClass().getClassLoader().getResourceAsStream("datasource.properties"));
        }catch(IOException ioe){
        	logger.error(ioe.getMessage());
        }        

        this.driver = props.getProperty("datasource.driver");
        this.password = props.getProperty("datasource.password");
        this.user = props.getProperty("datasource.username");
        this.schema = props.getProperty("datasource.schema");
        this.schemaObjectPrefix = "rep_";
        this.url = props.getProperty("datasource.url");*/
        
        this.driver = "org.postgresql.Driver";
        this.password = "postgres";
        this.user = "postgres";
        this.schema = "postgresql";
        this.schemaObjectPrefix = "rep_";
        this.url = "jdbc:postgresql://localhost:5432/c3pr_svn";
    }

}
