package dk.martinersej.theapi.database;

import dk.martinersej.theapi.TheAPI;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public interface DAO<K, V> {

    String getTableName();

    void createTable();

    void deleteTable();

    default public V getOrPersist(String key, Object keyValue, V createIfAbsent) {
        Optional<V> existing = this.get(key, keyValue);
        if (existing.isPresent()) {
            return existing.get();
        }
        this.persist(createIfAbsent);
        return createIfAbsent;
    }

    default void persist(V value) {
        try {
            createOrUpdate(value);
        } catch (Exception exception) {
            TheAPI.getPlugin().getLogger().log(Level.SEVERE, "Failed to create/update " + value.toString(), exception);
        }
    }

    default Optional<V> get(String key, Object keyValue) {
        return getAll(key, keyValue).stream().findAny();
    }

    void createOrUpdate(V value);

    List<V> queryForEq(String key, Object value);

    default List<V> getAll(String key, Object keyValue) {
        try {
            return queryForEq(key, keyValue);
        } catch (Exception exception) {
            TheAPI.getPlugin().getLogger().log(Level.SEVERE, "Failed to get object with key " + key + " = " + keyValue, exception);
        }
        return Collections.emptyList();
    }

    default List<V> getAll() {
        try {
            return queryForAll();
        } catch (Exception exception) {
            TheAPI.getPlugin().getLogger().log(Level.SEVERE, "Failed to get all objects", exception);
        }
        return Collections.emptyList();
    }

    default Optional<V> get(K key) {
        try {
            return Optional.ofNullable(queryForId(key));
        } catch (Exception exception) {
            TheAPI.getPlugin().getLogger().log(Level.SEVERE, "Failed to get " + key, exception);
        }
        return Optional.empty();
    }

    V queryForId(K key);

    List<V> queryForAll();

    void deleteById(K key);

    default void delete(K key) {
        try {
            deleteById(key);
        } catch (Exception exception) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to delete " + key, exception);
        }
    }
}
