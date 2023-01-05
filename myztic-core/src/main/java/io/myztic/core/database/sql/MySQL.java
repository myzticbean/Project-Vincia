package io.myztic.core.database.sql;

import io.myztic.core.MyzticCore;
import io.myztic.core.bukkit.LoggerUtils;
import io.myztic.core.config.ConfigProvider;
import io.myztic.core.exceptions.CoreException;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL implements SQL {

    private static MySQL mySQL = null;
    private Connection con = null;
    private final String SQL_CONN_ERROR_MSG = "SQL connection error: ";

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
    public void setConnectionAsync(String host, String user, String password, String database, String port) {
        Bukkit.getScheduler().runTaskAsynchronously(MyzticCore.getInst(), () -> {
            if (host == null || user == null || password == null || database == null)
                return;
            disconnect();
            try {
                String driver = ConfigProvider.getInst().SQL_DRIVER;
                if (driver.length() == 0) {
                    LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: Driver is blank");
                } else {
                    driver = driver.toLowerCase();
                    String tlsVersion = ConfigProvider.getInst().SQL_TLS_VERSION;
                    StringBuilder conUrlSb = new StringBuilder()
                        .append("jdbc:").append(driver.toLowerCase())
                        .append("://").append(host).append(":").append(port)
                        .append("/").append(database)
                        .append("?autoReconnect=true&maxReconnects=10")
                        .append(((tlsVersion != null && tlsVersion.length() > 0) ? ("&enabledTLSProtocols=TLSv" + tlsVersion) : ""))
                        .append("&useSSL=").append(ConfigProvider.getInst().SQL_USE_SSL);
                    LoggerUtils.logDebugInfo(ConfigProvider.getInst().PLUGIN_PREFIX, conUrlSb.toString() + " | username=" + user + " | password=" + password, ConfigProvider.getInst().DEBUG_MODE);
                    con = DriverManager.getConnection(conUrlSb.toString(), user, password);
                    LoggerUtils.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL connection established");
                }
            } catch (SQLException e) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, SQL_CONN_ERROR_MSG + e.getMessage(), e);
            }
        });
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
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, SQL_CONN_ERROR_MSG + "Already connected");
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
                setConnectionAsync(host, user, password, database, port);
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
                LoggerUtils.logDebugError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Disconnect Error: " + e.getMessage(),ConfigProvider.getInst().DEBUG_MODE, e);
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
        if (command == null)
            return null;
        connect();
        ResultSet rs = null;
        Statement st =null;
        try {
            if (con != null) {
                st = con.createStatement();
                rs = st.executeQuery(command);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            if (message != null) {
                LoggerUtils.logDebugError(
                        ConfigProvider.getInst().PLUGIN_PREFIX,
                        "SQL Query command: " + command + " >> " + e.getMessage(),
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
        return rs;
    }
}
