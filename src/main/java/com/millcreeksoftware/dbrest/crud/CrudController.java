package com.millcreeksoftware.dbrest.crud;

import com.millcreeksoftware.dbrest.scanner.DatabaseHolderService;
import com.millcreeksoftware.dbrest.scanner.DatabaseTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CrudController {
    private final DatabaseCrudService databaseCrudService;
    private final DatabaseHolderService databaseHolderService;

    public CrudController(DatabaseCrudService databaseCrudService, DatabaseHolderService databaseHolderService) {
        this.databaseCrudService = databaseCrudService;
        this.databaseHolderService = databaseHolderService;
    }

    @GetMapping(
            path = "/{tableName}",
            produces = "application/json"
    )
    public ResponseEntity<Response> get(
            @PathVariable() String tableName,
            @RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String sortOrder
    ) {
        return new ResponseEntity<>(databaseCrudService.get(tableName, sortBy, sortOrder), HttpStatus.OK);
    }

    @GetMapping(
            path = "/meta",
            produces = "application/json"
    )
    public ResponseEntity<List<DatabaseTable>> getMeta() {
        return new ResponseEntity<>(databaseHolderService.getAll(), HttpStatus.OK);
    }

    @GetMapping(
            path = "/meta/{tableName}",
            produces = "application/json"
    )
    public ResponseEntity<DatabaseTable> getMetaTable(@PathVariable() String tableName) {
        return new ResponseEntity<>(databaseHolderService.get(tableName), HttpStatus.OK);
    }

}
