package dk.martinersej.theapi.database;

import dk.martinersej.theapi.TheAPI;
import dk.martinersej.theapi.exceptions.DatabaseActionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

public interface Database {

    Lock getLock();

    Connection getConnection();

    void setConnection(Connection connection);

    default void connect(Consumer<Connection> callback) {
        async(() -> callback.accept(getConnection()));
    }

    default void disconnect() {
        try {
            if (isConnected()) {
                getConnection().close();
            }
        } catch (SQLException e) {
            throw DatabaseActionException.of(e);
        }
    }

    boolean isConnected();

    String getHost();

    String getPort();

    String getDatabase();

    String getUsername();

    String getPassword();

    String getPrefix();

    String getURL();

    String getDriver();

    default boolean hasDriver() {
        try {
            Class.forName(getDriver());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    Properties getProperties();

    void createTables();

    default void async(Runnable task) {
        TheAPI.getPlugin().getServer().getScheduler().runTaskAsynchronously(TheAPI.getPlugin(), () -> {
            getLock().lock();
            try {
                task.run();
            } finally {
                getLock().unlock();
            }
        });
    }

    default void sync(Consumer<Connection> callback) {
        getLock().lock();
        try {
            callback.accept(getConnection());
        } finally {
            getLock().unlock();
        }
    }
}
