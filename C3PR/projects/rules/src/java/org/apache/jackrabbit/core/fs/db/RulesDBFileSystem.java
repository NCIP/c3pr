package org.apache.jackrabbit.core.fs.db;

import java.util.Properties;

import edu.duke.cabig.c3pr.service.impl.RulesDelegationServiceImpl;

public class RulesDBFileSystem extends DbFileSystem {

    public RulesDBFileSystem() {

    	Properties props = new Properties();
        RulesDelegationServiceImpl.loadConfiguration(props); 

        this.driver = props.getProperty("datasource.driver");
        this.password = props.getProperty("datasource.password");
        this.user = props.getProperty("datasource.username");
        this.schema = "postgresql";
        this.schemaObjectPrefix = "rep_";
        this.url = props.getProperty("datasource.url");
        
        /*this.driver = driverClassName; //"org.postgresql.Driver";
        this.password = password;//"postgres";
        this.user = username;//"postgres";
        this.schema = "postgresql";
        this.schemaObjectPrefix = "rep_";
        this.url = url;//"jdbc:postgresql://localhost:5432/c3pr_svn";
*/    }

}
