package org.apache.jackrabbit.core.fs.db;

import java.util.Properties;

import edu.duke.cabig.c3pr.service.impl.RulesDelegationServiceImpl;

public class RulesVersioningSQLServerDBFileSystem extends DbFileSystem {

	public RulesVersioningSQLServerDBFileSystem() {

		Properties props = new Properties();
        RulesDelegationServiceImpl.loadConfiguration(props);
        
        this.driver = props.getProperty("datasource.driver");
        this.password = props.getProperty("datasource.password");
        this.user = props.getProperty("datasource.username");
        this.schema = "mssql";
        this.schemaObjectPrefix = "rep_ver_";
        this.url = props.getProperty("datasource.url");
        
        /*this.driver = "oracle.jdbc.OracleDriver";
        this.password = "oracle";
        this.user = "oracle";
        this.schema = "oracle";
        this.schemaObjectPrefix = "rep_";
        this.url = "jdbc:postgresql://localhost:5432/c3pr_svn";*/
    }
}
