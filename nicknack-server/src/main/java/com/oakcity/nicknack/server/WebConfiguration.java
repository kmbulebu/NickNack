package com.oakcity.nicknack.server;

import java.util.Properties;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.oakcity.nicknack.core.providers.ProviderService;
import com.oakcity.nicknack.core.providers.ProviderServiceImpl;

@EnableWebMvc
@EnableEntityLinks
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@EnableJpaRepositories
@EnableTransactionManagement
@EnableSpringDataWebSupport
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}

	@Bean
	public ProviderService providerService() {
		return ProviderServiceImpl.getInstance();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public DataSource dataSource() throws Exception {
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:~/.nicknack/nicknack");
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
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "true");
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "create");

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

}
