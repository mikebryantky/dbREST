package com.millcreeksoftware.dbrest.scanner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DatabaseHolderService {
    private List<DatabaseTable> tables = new ArrayList<>();

    public DatabaseTable get(String tableName) {
        return tables.stream().filter(
                databaseTable -> tableName.equalsIgnoreCase(databaseTable.getName()))
                .findFirst()
                .orElse(new DatabaseTable());
    }

    public List<DatabaseTable> getAll() {
        return tables;

    }

    public void setTables(List<DatabaseTable> tables) {
        this.tables = tables;
    }

}
