package com.millcreeksoftware.dbrest.scanner;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DatabaseTable {
    private String name;
    private List<DatabaseColumn> columns = new ArrayList<>();
}
