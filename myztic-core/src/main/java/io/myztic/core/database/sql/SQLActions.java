package io.myztic.core.database.sql;

import java.util.ArrayList;

public interface SQLActions {

    boolean tableExists(String table);
    boolean insertData(String columns, String values, String table);
    boolean deleteData(String column, String logicGate, String data, String table);
    boolean exists(String column, String data, String table);
    boolean deleteTable(String table);
    boolean truncateTable(String table);
    boolean createTable(String table, String columns);
    boolean upsert(String selected, Object object, String column, String data, String table);
    boolean set(String selected, Object object, String column, String logicGate, String data, String table);
    boolean set(String selected, Object object, String[] whereArguments, String table);
    Object get(String selected, String[] whereArguments, String table);
    ArrayList<Object> listGet(String selected, String[] whereArguments, String table);
    Object get(String selected, String column, String logicGate, String data, String table);
    ArrayList<Object> listGet(String selected, String column, String logicGate, String data, String table);
    int countRows(String table);
}
