package com.millcreeksoftware.dbrest.crud;

import com.millcreeksoftware.dbrest.scanner.DatabaseTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Response {
    private DatabaseTable databaseTable;
    private List<Map<String, String>> data = new ArrayList<>();
}
