package dk.martinersej.theapi.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryBuilder {

    private String query;

    public QueryBuilder() {
    }

    public static QueryBuilder create() {
        return new QueryBuilder();
    }

    public QueryBuilder dropTable(String table) {
        query = "DROP TABLE IF EXISTS " + table;
        return this;
    }

    public QueryBuilder select(String... columns) {
        query = "SELECT ";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i];
            if (i < columns.length - 1) {
                query += ", ";
            }
        }
        return this;
    }

    public QueryBuilder delete() {
        query = "DELETE";
        return this;
    }

    public QueryBuilder update() {
        query = "UPDATE";
        return this;
    }

    public QueryBuilder delete(String table) {
        query = "DELETE FROM " + table;
        return this;
    }

    public QueryBuilder update(String table) {
        query = "UPDATE " + table;
        return this;
    }

    public QueryBuilder set(String column, Object value) {
        query += " SET " + column + " = " + value;
        return this;
    }

    public QueryBuilder set(String column, String value) {
        query += " SET " + column + " = '" + value + "'";
        return this;
    }

    public QueryBuilder insertInto(String table, String... columns) {
        query = "INSERT INTO " + table + " (";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i];
            if (i < columns.length - 1) {
                query += ", ";
            }
        }
        query += ")";
        return this;
    }


    public QueryBuilder from(String table) {
        query += " FROM " + table;
        return this;
    }

    public PreparedStatement values(Connection connection, Object... values) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
        return statement;
    }

    public QueryBuilder where(String column) {
        query += " WHERE " + column + " = ?";
        return this;
    }

    public QueryBuilder where(String column, String value) {
        query += " WHERE " + column + " = '" + value + "'";
        return this;
    }

    public QueryBuilder where(String column, Object value) {
        query += " WHERE " + column + " = " + value;
        return this;
    }

    public QueryBuilder where(String column, String operator, String value) {
        query += " WHERE " + column + " " + operator + " '" + value + "'";
        return this;
    }

    public QueryBuilder where(String column, String operator, Object value) {
        query += " WHERE " + column + " " + operator + " " + value;
        return this;
    }

    public QueryBuilder and(String column, String value) {
        query += " AND " + column + " = '" + value + "'";
        return this;
    }

    public QueryBuilder and(String column, Object value) {
        query += " AND " + column + " = " + value;
        return this;
    }

    public QueryBuilder and(String column, String operator, String value) {
        query += " AND " + column + " " + operator + " '" + value + "'";
        return this;
    }

    public QueryBuilder and(String column, String operator, Object value) {
        query += " AND " + column + " " + operator + " " + value;
        return this;
    }

    public QueryBuilder or(String column, String value) {
        query += " OR " + column + " = '" + value + "'";
        return this;
    }

    public QueryBuilder or(String column, Object value) {
        query += " OR " + column + " = " + value;
        return this;
    }

    public QueryBuilder or(String column, String operator, String value) {
        query += " OR " + column + " " + operator + " '" + value + "'";
        return this;
    }

    public QueryBuilder or(String column, String operator, Object value) {
        query += " OR " + column + " " + operator + " " + value;
        return this;
    }

    public QueryBuilder orderBy(String column) {
        query += " ORDER BY " + column;
        return this;
    }

    public QueryBuilder orderBy(String column, String order) {
        query += " ORDER BY " + column + " " + order;
        return this;
    }

    public QueryBuilder limit(int limit) {
        query += " LIMIT " + limit;
        return this;
    }

    public QueryBuilder offset(int offset) {
        query += " OFFSET " + offset;
        return this;
    }

    public String build() {
        return query;
    }

    @Override
    public String toString() {
        return query;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (!(obj instanceof QueryBuilder) && !(obj instanceof String)) {
            return false;
        }

        return query.equals(obj.toString());
    }

    public static final class QueryTableBuilder {

        private String query;

        public QueryTableBuilder(String table) {
            query = "CREATE TABLE IF NOT EXISTS " + table;
        }

        public static QueryTableBuilder createTable(String table) {
            return new QueryTableBuilder(table);
        }

        public QueryTableBuilder values(String column, String type) {
            query += " (" + column + " " + type + ")";
            return this;
        }

        public QueryTableBuilder values(String column, String type, Constraint... constraints) {
            query += " (" + column + " " + type;
            for (Constraint constraint : constraints) {
                query += " " + constraint;
            }
            query += ")";
            return this;
        }

        public QueryTableBuilder values(String column, String type, String... constraints) {
            query += " (" + column + " " + type;
            for (String constraint : constraints) {
                query = String.format("%s%s", query, " " + constraint);
            }
            query += ")";
            return this;
        }

        public QueryTableBuilder build() {
            return this;
        }

        @Override
        public String toString() {
            return query;
        }
    }
}
