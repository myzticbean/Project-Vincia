package io.myztic.core.database.sql;

import io.myztic.core.MyzticCore;
import io.myztic.core.bukkit.BukkitUtil;
import io.myztic.core.config.coreconfig.ConfigProvider;
import io.myztic.core.logging.LogUtil;
import org.jetbrains.annotations.Nullable;

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

    @Override @Nullable
    public Connection getConnection() {
        return con;
    }

    @Override
    public void setConnectionAsync(String host, String user, String password, String database, String port) {
        BukkitUtil.runOnAsyncThread(MyzticCore.getInst(), () -> {
            if (host == null || user == null || password == null || database == null)
                return;
            disconnect();
            try {
                String driver = ConfigProvider.getInst().SQL_DRIVER;
                if (driver.length() == 0) {
                    LogUtil.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: Driver is blank");
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
                    LogUtil.logDebugInfo(
                            ConfigProvider.getInst().PLUGIN_PREFIX,
                            conUrlSb.toString() + " | username=" + user + " | password=" + password,
                            ConfigProvider.getInst().DEBUG_MODE);
                    con = DriverManager.getConnection(conUrlSb.toString(), user, password);
                    LogUtil.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL connection established");
                }
            } catch (SQLException e) {
                LogUtil.logDebugError(ConfigProvider.getInst().PLUGIN_PREFIX, SQL_CONN_ERROR_MSG + e.getMessage(), ConfigProvider.getInst().DEBUG_MODE, e);
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
                LogUtil.logError(ConfigProvider.getInst().PLUGIN_PREFIX, SQL_CONN_ERROR_MSG + "Already connected");
            } else if (host.length() == 0) {
                LogUtil.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: Host is blank");
            } else if (user.length() == 0) {
                LogUtil.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: User is blank");
            } else if (password.length() == 0) {
                LogUtil.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: Password is blank");
            } else if (database.length() == 0) {
                LogUtil.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: Database is blank");
            } else if (port.length() == 0) {
                LogUtil.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Config Error: Port is blank");
            } else {
                setConnectionAsync(host, user, password, database, port);
            }
        } else {
            LogUtil.logDebugInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL is disabled", ConfigProvider.getInst().DEBUG_MODE);
        }
    }

    @Override
    public void disconnect() {
        if(ConfigProvider.getInst().SQL_USE) {
            try {
                if (isConnected()) {
                    con.close();
                    LogUtil.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL disconnected");
                } else {
                    LogUtil.logWarning(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Disconnect Error: No existing connection");
                }
            } catch (Exception e) {
                LogUtil.logDebugError(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL Disconnect Error: " + e.getMessage(),ConfigProvider.getInst().DEBUG_MODE, e);
            }
            con = null;
        } else {
            LogUtil.logDebugInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL is disabled", ConfigProvider.getInst().DEBUG_MODE);
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
                LogUtil.logDebugError(
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
        LogUtil.logDebugInfo(
                ConfigProvider.getInst().PLUGIN_PREFIX,
                "Update statement to be executed: " + command,
                ConfigProvider.getInst().DEBUG_MODE);
        connect();
        if (con != null) {
            try (Statement st = con.createStatement()) {
                st.executeUpdate(command);
                result = true;
            } catch (Exception e) {
                String message = e.getMessage();
                if (message != null) {
                    LogUtil.logDebugError(
                            ConfigProvider.getInst().PLUGIN_PREFIX,
                            "SQL Update command: " + command + " >> " + e.getMessage(),
                            ConfigProvider.getInst().DEBUG_MODE,
                            e);
                }
            } finally {
                disconnect();
            }
        }
        return result;
    }

    @Override @Nullable
    public ResultSet query(String command) {
        if (command == null)
            return null;
        connect();
        ResultSet rs = null;
        if (con != null) {
            try (Statement st = con.createStatement()) {
                rs = st.executeQuery(command);
            } catch (Exception e) {
                String message = e.getMessage();
                if (message != null) {
                    LogUtil.logDebugError(
                            ConfigProvider.getInst().PLUGIN_PREFIX,
                            "SQL Query command: " + command + " >> " + e.getMessage(),
                            ConfigProvider.getInst().DEBUG_MODE,
                            e);
                }
            } finally {
                disconnect();
            }
        }
        return rs;
    }
}
