package io.myztic.core.database.sql;

import io.myztic.core.bukkit.LoggerUtils;
import io.myztic.core.config.ConfigProvider;

import java.sql.*;

public class MySQL implements SQL {

    private static MySQL mySQL = null;
    private Connection con = null;

    public static MySQL getInst() {
        if(mySQL == null)
            mySQL = new MySQL();
        return mySQL;
    }

    @Override
    public Connection getConnection() {
        return con;
    }

    @Override
    public void setConnection(String host, String user, String password, String database, String port) {
        if (host == null || user == null || password == null || database == null)
            return;
        disconnect();
        try {
            String driver = ConfigProvider.getInst().SQL_DRIVER;
            if (driver.length() == 0) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: Driver is blank");
            } else {
                String tlsVersion = ConfigProvider.getInst().SQL_TLS_VERSION;
                con = DriverManager.getConnection("jdbc:" + driver + "://" + host + ":" + port + "/" + database + "?autoReconnect=true&maxReconnects=10" + ((tlsVersion != null && tlsVersion
                        .length() > 0) ? ("&enabledTLSProtocols=TLSv" + tlsVersion) : "") + "&useSSL=" +
                        ConfigProvider.getInst().SQL_USE_SSL, user, password);
                LoggerUtils.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL connected");
            }
        } catch (Exception e) {
            LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Connect Error: " + e.getMessage(), e);
        }
    }

    @Override
    public void connect() {
        if(ConfigProvider.getInst().SQL_USE) {
            String host = ConfigProvider.getInst().SQL_HOST;
            String user = ConfigProvider.getInst().SQL_USER;
            String password = ConfigProvider.getInst().SQL_PASSWORD;
            String database = ConfigProvider.getInst().SQL_DB;
            String port = String.valueOf(ConfigProvider.getInst().SQL_PORT);
            if (isConnected()) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Connect Error: Already connected");
            } else if (host.length() == 0) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: Host is blank");
            } else if (user.length() == 0) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: User is blank");
            } else if (password.length() == 0) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: Password is blank");
            } else if (database.length() == 0) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: Database is blank");
            } else if (port.length() == 0) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: Port is blank");
            } else {
                setConnection(host, user, password, database, port);
            }
        } else {
            LoggerUtils.logDebugInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL is disabled", ConfigProvider.getInst().DEBUG_MODE);
        }
    }

    @Override
    public void disconnect() {
        if(ConfigProvider.getInst().SQL_USE) {
            try {
                if (isConnected()) {
                    con.close();
                    LoggerUtils.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL disconnected");
                } else {
                    LoggerUtils.logWarning(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Disconnect Error: No existing connection");
                }
            } catch (Exception e) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Disconnect Error: " + e.getMessage(), e);
            }
            con = null;
        } else {
            LoggerUtils.logDebugInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL is disabled", ConfigProvider.getInst().DEBUG_MODE);
        }
    }

    @Override
    public void reconnect() {
        disconnect();
        connect();
    }

    @Override
    public boolean isConnected() {
        if (con != null)
            try {
                return !con.isClosed();
            } catch (Exception e) {
                LoggerUtils.logDebugError(
                        ConfigProvider.getInst().PLUGIN_PREFIX,
                        "SQL Connection Error >> " + e.getMessage(),
                        ConfigProvider.getInst().DEBUG_MODE,
                        e);
            }
        return false;
    }

    @Override
    public boolean update(String command) {
        if (command == null)
            return false;
        boolean result = false;
        LoggerUtils.logDebugInfo(
                ConfigProvider.getInst().PLUGIN_PREFIX,
                "Update statement to be executed: " + command,
                ConfigProvider.getInst().DEBUG_MODE);
        Statement st = null;
        try {
            connect();
            if (con != null) {
                st = con.createStatement();
                st.executeUpdate(command);
                st.close();
                result = true;

            }
        } catch (Exception e) {
            String message = e.getMessage();
            if (message != null) {
                LoggerUtils.logDebugError(
                        ConfigProvider.getInst().PLUGIN_PREFIX,
                        "SQL Update command: " + command + " >> " + e.getMessage(),
                        ConfigProvider.getInst().DEBUG_MODE,
                        e);
            }
        } finally {
            try {
                if(st != null && !st.isClosed()) {
                    st.close();
                }
            } catch (SQLException e) {
                LoggerUtils.logDebugError(
                        ConfigProvider.getInst().PLUGIN_PREFIX,
                        "SQL Error >> " + e.getMessage(),
                        ConfigProvider.getInst().DEBUG_MODE,
                        e);
            }
            disconnect();
        }
        return result;
    }

    @Override
    public ResultSet query(String command) {
        return null;
    }
}
