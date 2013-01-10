/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package org.apache.jackrabbit.core.fs.db;


import java.util.Properties;

import edu.duke.cabig.c3pr.service.impl.RulesDelegationServiceImpl;

public class RulesOracleDBFileSystem extends OracleFileSystem {

	public RulesOracleDBFileSystem() {

		Properties props = new Properties();
        RulesDelegationServiceImpl.loadConfiguration(props);

        this.driver = props.getProperty("datasource.driver");
        this.password = props.getProperty("datasource.password");
        this.user = props.getProperty("datasource.username");
        this.schema = "oracle";
        this.schemaObjectPrefix = "rep_";
        this.url = props.getProperty("datasource.url");
    }
}
