package com.millcreeksoftware.dbrest.scanner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DatabaseMetaDataCallbackHandler implements DatabaseMetaDataCallback {

    @Override
    public Object processMetaData(DatabaseMetaData databaseMetaData) throws SQLException, MetaDataAccessException {
        List<DatabaseTable> tableList = new ArrayList<>();

        ResultSet tables = databaseMetaData.getTables(
                "dbrest",
                null,
                null,
                new String[]{"TABLE"});

        while (tables.next()) {
            DatabaseTable databaseTable = new DatabaseTable();
            String tableName = tables.getString(3);
            databaseTable.setName(tableName);

            ResultSet columns = databaseMetaData.getColumns(
                    "dbrest",
                    null,
                    tableName,
                    null
            );
            while (columns.next()) {
                List<DatabaseColumn> columnList = new ArrayList<>();

                DatabaseColumn databaseColumn = new DatabaseColumn();
                String columnName = columns.getString("COLUMN_NAME");
                databaseColumn.setName(columnName);
                databaseColumn.setType(columns.getString("TYPE_NAME"));
                databaseColumn.setSize(columns.getInt("COLUMN_SIZE"));
                databaseColumn.setNullable("NO".equalsIgnoreCase(columns.getString("IS_NULLABLE")));
                databaseColumn.setNullable("YES".equalsIgnoreCase(columns.getString("IS_AUTOINCREMENT")));

                databaseTable.getColumns().add(databaseColumn);
            }

            tableList.add(databaseTable);
        }

        return tableList;
    }
}
