package com.millcreeksoftware.dbrest.scanner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class DBMapper {

    private final DataSource dataSource;
    private final DatabaseHolderService databaseHolderService;

    public DBMapper(DataSource dataSource, DatabaseHolderService databaseHolderService) {
        this.dataSource = dataSource;
        this.databaseHolderService = databaseHolderService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void map() {
        log.info("Mapping database.");

        try {
            Object object = JdbcUtils.extractDatabaseMetaData(
                    dataSource,
                    new DatabaseMetaDataCallbackHandler());

            log.info("DB:" + object);

            databaseHolderService.setTables( (List<DatabaseTable>) object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

