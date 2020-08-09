package br.com.navita.api.configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LiquibaseConfiguration {

	private LiquibaseProperties liquibaseProperties;
	private Property property;

	@Autowired
	public LiquibaseConfiguration(Environment env, LiquibaseProperties liquibaseProperties, Property property) {
		this.liquibaseProperties = liquibaseProperties;
		this.property = property;
	}

	@Bean
	public SpringLiquibase liquibase(DataSource dataSource) {

		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:config/liquibase/master.xml");
		liquibase.setContexts(liquibaseProperties.getContexts());
		liquibase.setShouldRun(property.getDatasource().isExecuteLiquibase());

		releaseLiquibaseLocks(dataSource);
		clearLiquibaseCheckSums(dataSource);

		log.debug("Configuring Liquibase and versioning the database ... Please wait!");

		return liquibase;
	}

	/**
	 * Release all Liquibase locks from a {@link DataSource}.
	 * 
	 * @param ds {@link DataSource}
	 */
	public void releaseLiquibaseLocks(DataSource ds) {

		try (Connection con = ds.getConnection(); Statement st = con.createStatement()) {
			log.info("Releasing Liquibase Locks");
			st.executeUpdate("DELETE FROM DATABASECHANGELOGLOCK");
			con.commit();
		} catch (SQLException e) {
			log.info("Error while trying to delete DATABASECHANGELOGLOCK");
		}
	}

	/**
	 * Clears all Liquibase checksums from a {@link DataSource}.
	 * 
	 * @param ds {@link DataSource}
	 */
	public void clearLiquibaseCheckSums(DataSource ds) {

		try (Connection con = ds.getConnection(); Statement st = con.createStatement()) {
			log.info("Clear Liquibase ChecksSums");
			st.executeUpdate("UPDATE DATABASECHANGELOG SET MD5SUM=NULL");
			con.commit();
		} catch (SQLException e) {
			log.info("Error while trying to delete MD5SUM");
		}
	}
}
