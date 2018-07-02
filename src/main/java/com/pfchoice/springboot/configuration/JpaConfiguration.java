package com.pfchoice.springboot.configuration;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.pfchoice.springboot.repositories", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
@EnableTransactionManagement
@EnableSpringDataWebSupport
public class JpaConfiguration {

	@Autowired
	private Environment environment;

	@Value("${spring.datasource.hikari.maxPoolSize}")
	private int maxPoolSize;
	
	@Value("${spring.datasource.hikari.minimumIdle}")
	private int minimumIdle;
	
	@Value("${spring.datasource.hikari.idleTimeout}")
	private int idleTimeout;
	
	@Value("${spring.datasource.hikari.connectionTimeout}")
	private int connectionTimeout;
	
	@Value("${spring.datasource.hikari.maxLifeTime}")
	private int maxLifeTime;
	
	@Value("${spring.datasource.hikari.poolName}")
	private String poolName;
	
	@Value("${spring.datasource.hikari.isAutoCommit}")
	private boolean isAutoCommit;

	/*
	 * Populate SpringBoot DataSourceProperties object directly from
	 * application.yml based on prefix.Thanks to .yml, Hierachical data is
	 * mapped out of the box with matching-name properties of
	 * DataSourceProperties object].
	 */
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource.leadManagement")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	/*
	 * Configure HikariCP pooled DataSource.
	 */
	@Bean
	public DataSource dataSource() {
		DataSourceProperties dataSourceProperties = dataSourceProperties();
		HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create(dataSourceProperties.getClassLoader())
				.driverClassName(dataSourceProperties.getDriverClassName()).url(dataSourceProperties.getUrl())
				.username(dataSourceProperties.getUsername()).password(dataSourceProperties.getPassword())
				.type(HikariDataSource.class).build();
		dataSource.setMaximumPoolSize(maxPoolSize);
		dataSource.setMinimumIdle(minimumIdle);
		dataSource.setIdleTimeout(idleTimeout);
		dataSource.setConnectionTimeout(connectionTimeout);
		dataSource.setMaxLifetime(maxLifeTime);
		dataSource.setPoolName(poolName);
		dataSource.setAutoCommit(isAutoCommit);
		return dataSource;
	}

	/*
	 * Entity Manager Factory setup.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] { "com.pfchoice.springboot.model" });
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(jpaProperties());
		return factoryBean;
	}

	/*
	 * Provider specific adapter.
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		return hibernateJpaVendorAdapter;
	}

	/*
	 * Here you can specify any provider specific properties.
	 */
	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto",
				environment.getRequiredProperty("spring.jpa.properties.hibernate.hbm2ddl.method"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("spring.jpa.properties.hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("spring.jpa.properties.hibernate.format_sql"));
		properties.put("hibernate.enable_lazy_load_no_trans", environment.getRequiredProperty("spring.jpa.properties.hibernate.enable_lazy_load_no_trans"));
		
		if (StringUtils.isNotEmpty(environment.getRequiredProperty("spring.datasource.defaultSchema"))) {
			properties.put("hibernate.default_schema", environment.getRequiredProperty("spring.datasource.defaultSchema"));
		}
		return properties;
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

}
