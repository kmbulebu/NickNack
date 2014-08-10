package com.oakcity.nicknack.server;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class H2DatabaseConfiguration {

	protected static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	protected static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	protected static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
	protected static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	protected static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	
	@Value(value = "${nicknack.db.path:./db/nicknack}")
	private String dbPathProperty;
	
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public DataSource dataSource() throws Exception {
		final Path absoluteDbPath = Paths.get(dbPathProperty).toAbsolutePath();
		
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:" + absoluteDbPath.toString());
		ds.setUser("sa");
		ds.setPassword("sa");
		return ds;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("com.oakcity.nicknack.server.model");
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);


		Properties jpaProperties = new Properties();

		jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, org.hibernate.dialect.H2Dialect.class.getName());
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, "true");
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, ImprovedNamingStrategy.class.getName());
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "false");
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "update");

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

}
