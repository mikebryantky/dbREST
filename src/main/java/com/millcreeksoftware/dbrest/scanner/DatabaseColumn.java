package com.millcreeksoftware.dbrest.scanner;

import lombok.Data;

@Data
public class DatabaseColumn {
    private String name;
    private boolean primaryKey;
    private String type;
    private int size;
    private boolean nullable;
}
