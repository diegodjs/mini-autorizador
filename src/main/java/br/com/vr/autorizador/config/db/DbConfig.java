package br.com.vr.autorizador.config.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * DbConfig - Classe de configuracao para o banco de dados
 * @author Diego
 */
@Configuration
@EnableTransactionManagement
public class DbConfig {

	@Bean(name = "springDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource springDataSource() {
		return DataSourceBuilder
				.create()
				.build();
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("springDataSource") DataSource springDataSource) {
		return builder.dataSource(springDataSource).packages("br.com.vr.autorizador.config.db")
				.persistenceUnit("springdb").build();
	}
}