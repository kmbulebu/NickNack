package com.oakcity.nicknack.server;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile(value="heroku")
public class HerokuDatabaseConfiguration {
	
	@Value("${DATABASE_URL}")
	private String databaseUrl;
	
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public DataSource dataSource() throws Exception {
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(databaseUrl);
		return ds;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("com.oakcity.nicknack.server.model");
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);


		Properties jpaProperties = new Properties();

		jpaProperties.put(WebConfiguration.PROPERTY_NAME_HIBERNATE_DIALECT, org.hibernate.dialect.PostgreSQL9Dialect.class.getName());
		jpaProperties.put(WebConfiguration.PROPERTY_NAME_HIBERNATE_FORMAT_SQL, "true");
		jpaProperties.put(WebConfiguration.PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, ImprovedNamingStrategy.class.getName());
		jpaProperties.put(WebConfiguration.PROPERTY_NAME_HIBERNATE_SHOW_SQL, "true");
		jpaProperties.put(WebConfiguration.PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "create");

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

}
