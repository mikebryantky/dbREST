package com.millcreeksoftware.dbrest.crud;

import com.millcreeksoftware.dbrest.scanner.DatabaseHolderService;
import com.millcreeksoftware.dbrest.scanner.DatabaseTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DatabaseCrudService {
    private final JdbcTemplate jdbcTemplate;
    private final DatabaseHolderService databaseHolderService;


    public DatabaseCrudService(JdbcTemplate jdbcTemplate, DatabaseHolderService databaseHolderService) {
        this.jdbcTemplate = jdbcTemplate;
        this.databaseHolderService = databaseHolderService;
    }

    public Response get(String tableName, String sortBy, String sortOrder) {
        Response response = new Response();

        DatabaseTable databaseTable = databaseHolderService.get(tableName);
        response.setDatabaseTable(databaseTable);

        sortBy = StringUtils.isBlank(sortBy)
                ? databaseTable.getColumns().get(0).getName()
                : sortBy;

        String query = "select * from "
                + tableName
                + " order by "
                + sortBy
                + " "
                + sortOrder;

        log.info(query);

        jdbcTemplate.query(
                query,
                new RowCallbackHandler() {
                    public void processRow(ResultSet resultSet) {
                        Map<String, String> rowData = new HashMap<>();
                        databaseTable.getColumns().forEach(databaseColumn -> {
                            try {
                                log.debug("{} = {}", databaseColumn.getName(), resultSet.getObject(databaseColumn.getName()));
                                rowData.put(databaseColumn.getName(), "" + resultSet.getObject(databaseColumn.getName()));
                            } catch (SQLException e) {
                                log.error(e.getMessage(), e);
                            }
                        });
                        response.getData().add(rowData);
                    }
                }
        );

        return response;
    }

}
