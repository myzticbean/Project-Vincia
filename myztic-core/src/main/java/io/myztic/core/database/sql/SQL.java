package io.myztic.core.database.sql;

import java.sql.Connection;
import java.sql.ResultSet;

public interface SQL {

    Connection getConnection();
    void setConnection(String host, String user, String password, String database, String port);
    void connect();
    void disconnect();
    void reconnect();
    boolean isConnected();
    boolean update(String command);
    ResultSet query(String command);

}
