package io.myztic.core.database.nosql;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import io.myztic.core.bukkit.LoggerUtils;
import io.myztic.core.config.ConfigProvider;

public class MongoDBImpl {

    private final String MONGO_CONN_ERROR_MSG = "MongoDB connection error: ";

    public void connect() {
//        MongoClient mongoClient = new MongoClient("localhost", 27017);
//        DB database = mongoClient.getDB("myMongoDb");
//        boolean auth = database.authenticate("username", "pwd".toCharArray());

        StringBuilder uriSB = new StringBuilder("mongodb+srv://").append(ConfigProvider.getInst().NOSQL_USER)
                .append(":").append(ConfigProvider.getInst().NOSQL_PASSWORD)
                .append("@");
        String uri = "mongodb://user:pass@sample.host:27017/?maxPoolSize=20&w=majority";



        ConnectionString connectionString = new ConnectionString("mongodb+srv://myzticb:<password>@mcluster0.q0w9ywk.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString).build();
//                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
//        MongoClient mongoClient = (MongoClient) MongoClients.create(settings);
//        MongoDatabase database = mongoClient.getDatabase(ConfigProvider.getInst().NOSQL_DB);


    }

    private boolean validateMongoDBConfigProperties() {
        if(ConfigProvider.getInst().NOSQL_USE) {
            String host = ConfigProvider.getInst().NOSQL_HOST;
            String user = ConfigProvider.getInst().NOSQL_USER;
            String password = ConfigProvider.getInst().NOSQL_PASSWORD;
            String database = ConfigProvider.getInst().NOSQL_DB;
            String port = String.valueOf(ConfigProvider.getInst().NOSQL_PORT);
            if (isConnected()) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, MONGO_CONN_ERROR_MSG + "Already connected");
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
//                setConnectionAsync(host, user, password, database, port);
            }
        } else {
            LoggerUtils.logDebugInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "SQL is disabled", ConfigProvider.getInst().DEBUG_MODE);
        }
        return true;
    }

    private boolean isConnected() {
        return false;
    }

}
