package io.myztic.core.database.sql;

import io.myztic.core.bukkit.LoggerUtils;
import io.myztic.core.config.ConfigProvider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MySQLActions implements SQLActions {

    @Override
    public boolean tableExists(String table) {
        try {
            Connection connection = MySQL.getInst().getConnection();
            if (connection == null)
                return false;
            DatabaseMetaData metadata = connection.getMetaData();
            if (metadata == null)
                return false;
            ResultSet rs = metadata.getTables(null, null, table, null);
            if (rs.next())
                return true;
        } catch (Exception exception) {
            LoggerUtils.logDebugError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Error: " + exception.getMessage(), ConfigProvider.getInst().DEBUG_MODE, exception);
        }
        return false;
    }

    @Override
    public boolean insertData(String columns, String values, String table) {
        StringBuilder sb = new StringBuilder()
                .append("INSERT INTO ")
                .append(table)
                .append(" (")
                .append(columns)
                .append(") VALUES (")
                .append(values)
                .append(");");
        return MySQL.getInst().update(sb.toString());
    }

    @Override
    public boolean deleteData(String column, String logicGate, String data, String table) {
        StringBuilder sb = new StringBuilder()
                .append("DELETE FROM ")
                .append(table)
                .append(" WHERE ")
                .append(column)
                .append(logicGate)
                .append((data != null ? "'" + data + "'" : null))
                .append(";");
        return MySQL.getInst().update(sb.toString());
    }

    @Override
    public boolean exists(String column, String data, String table) {
        StringBuilder sb = new StringBuilder()
                .append("SELECT * FROM ")
                .append(table)
                .append(" WHERE ")
                .append(column)
                .append("=")
                .append((data != null ? "'" + data + "'" : null))
                .append(";");
        try {
            ResultSet rs = MySQL.getInst().query(sb.toString());
            if (rs.next())
                return true;
        } catch (Exception exception) {
            LoggerUtils.logDebugError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Error: " + exception.getMessage(), ConfigProvider.getInst().DEBUG_MODE, exception);
        }
        return false;
    }

    @Override
    public boolean deleteTable(String table) {
        StringBuilder sb = new StringBuilder()
                .append("DROP TABLE ")
                .append(table)
                .append(";");
        return MySQL.getInst().update(sb.toString());
    }

    @Override
    public boolean truncateTable(String table) {
        StringBuilder sb = new StringBuilder()
                .append("TRUNCATE TABLE ")
                .append(table)
                .append(";");
        return MySQL.getInst().update(sb.toString());
    }

    @Override
    public boolean createTable(String table, String columns) {
        StringBuilder sb = new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS ")
                .append(table)
                .append(" (")
                .append(columns)
                .append(");");
        return MySQL.getInst().update(sb.toString());
    }

    @Override
    public boolean upsert(String selected, Object object, String column, String data, String table) {
        if (object != null)
            object = "'" + object + "'";
        if (data != null)
            data = "'" + data + "'";
        try {
            StringBuilder selectQuerySB = new StringBuilder()
                    .append("SELECT * FROM ")
                    .append(table)
                    .append(" WHERE ")
                    .append(column)
                    .append("=")
                    .append(data)
                    .append(";");
            ResultSet rs = MySQL.getInst().query(selectQuerySB.toString());
            if (rs.next()) {
                StringBuilder updateQuerySB = new StringBuilder()
                        .append("UPDATE ")
                        .append(table)
                        .append(" SET ")
                        .append(selected)
                        .append("=")
                        .append(object)
                        .append(" WHERE ")
                        .append(column)
                        .append("=")
                        .append(data)
                        .append(";");
                MySQL.getInst().update(updateQuerySB.toString());
            } else {
                insertData(column + ", " + selected, data + ", " + object, table);
            }
        } catch (Exception exception) {
            LoggerUtils.logDebugError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Error: " + exception.getMessage(), ConfigProvider.getInst().DEBUG_MODE, exception);
        }
        return false;
    }

    @Override
    public boolean set(String selected, Object object, String column, String logicGate, String data, String table) {
        if (object != null)
            object = "'" + object + "'";
        if (data != null)
            data = "'" + data + "'";
        StringBuilder updateQuerySB = new StringBuilder()
                .append("UPDATE ")
                .append(table)
                .append(" SET ")
                .append(selected)
                .append("=")
                .append(object)
                .append(" WHERE ")
                .append(column)
                .append(logicGate)
                .append(data)
                .append(";");
        return MySQL.getInst().update(updateQuerySB.toString());
    }

    @Override
    public boolean set(String selected, Object object, String[] whereArguments, String table) {
        StringBuilder argumentsSb = new StringBuilder();
        for (String argument : whereArguments) {
            argumentsSb.append(argument).append(" AND ");
        }
        String arguments = argumentsSb.toString();
        if (arguments.length() <= 5)
            return false;
        arguments = arguments.substring(0, arguments.length() - 5);
        if (object != null)
            object = "'" + object + "'";
        return MySQL.getInst().update("UPDATE " + table + " SET " + selected + "=" + object + " WHERE " + arguments + ";");
    }

    @Override
    public Object get(String selected, String[] whereArguments, String table) {
        StringBuilder argumentsSb = new StringBuilder();
        for (String argument : whereArguments) {
            argumentsSb.append(argument).append(" AND ");
        }
        String arguments = argumentsSb.toString();
        if (arguments.length() <= 5)
            return Boolean.FALSE;
        arguments = arguments.substring(0, arguments.length() - 5);
        try {
            ResultSet rs = MySQL.getInst().query("SELECT * FROM " + table + " WHERE " + arguments + ";");
            if (rs.next())
                return rs.getObject(selected);
        } catch (Exception exception) {
            LoggerUtils.logDebugError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Error: " + exception.getMessage(), ConfigProvider.getInst().DEBUG_MODE, exception);
        }
        return null;
    }

    @Override
    public ArrayList<Object> listGet(String selected, String[] whereArguments, String table) {
        ArrayList<Object> array = new ArrayList<>();
//        String arguments = "";
//        for (String argument : whereArguments)
//            arguments = arguments + argument + " AND ";
        StringBuilder argumentsSb = new StringBuilder();
        for (String argument : whereArguments) {
            argumentsSb.append(argument).append(" AND ");
        }
        String arguments = argumentsSb.toString();
        if (arguments.length() <= 5)
            return array;
        arguments = arguments.substring(0, arguments.length() - 5);
        try {
            ResultSet rs = MySQL.getInst().query("SELECT * FROM " + table + " WHERE " + arguments + ";");
            while (rs.next())
                array.add(rs.getObject(selected));
        } catch (Exception exception) {
            LoggerUtils.logDebugError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Error: " + exception.getMessage(), ConfigProvider.getInst().DEBUG_MODE, exception);
        }
        return array;
    }

    @Override
    public Object get(String selected, String column, String logicGate, String data, String table) {
        if (data != null)
            data = "'" + data + "'";
        try {
            ResultSet rs = MySQL.getInst().query("SELECT * FROM " + table + " WHERE " + column + logicGate + data + ";");
            if (rs.next())
                return rs.getObject(selected);
        } catch (Exception exception) {
            LoggerUtils.logDebugError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Error: " + exception.getMessage(), ConfigProvider.getInst().DEBUG_MODE, exception);
        }
        return null;
    }

    @Override
    public ArrayList<Object> listGet(String selected, String column, String logicGate, String data, String table) {
        ArrayList<Object> array = new ArrayList<>();
        if (data != null)
            data = "'" + data + "'";
        try {
            ResultSet rs = MySQL.getInst().query("SELECT * FROM " + table + " WHERE " + column + logicGate + data + ";");
            while (rs.next())
                array.add(rs.getObject(selected));
        } catch (Exception exception) {
            LoggerUtils.logDebugError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Error: " + exception.getMessage(), ConfigProvider.getInst().DEBUG_MODE, exception);
        }
        return array;
    }

    @Override
    public int countRows(String table) {
        int i = 0;
        if (table == null)
            return i;
        ResultSet rs = MySQL.getInst().query("SELECT * FROM " + table + ";");
        try {
            while (rs.next())
                i++;
        } catch (Exception exception) {
            LoggerUtils.logDebugError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Error: " + exception.getMessage(), ConfigProvider.getInst().DEBUG_MODE, exception);
        }
        return i;
    }
}
