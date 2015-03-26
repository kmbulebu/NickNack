package com.github.kmbulebu.nicknack.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableNeo4jRepositories
@EnableTransactionManagement
@Configuration
public class DatabaseConfiguration extends Neo4jConfiguration {
		
	public DatabaseConfiguration() {
		super();
		setBasePackage("com.github.kmbulebu.nicknack.server.dbmodel");
		
	}


}
