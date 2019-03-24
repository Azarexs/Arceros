package me.azarex.arceros.database;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public interface Database {

    HikariDataSource hikari();
    RowSetFactory rowSetFactory();

    /**
     * Executes an SQL Statement on the database provided by {@link Database#hikari()}
     * @param sqlStatement Statement to be executed
     * @return {@code CompletableFuture<Void>} for the purpose of
     * getting whenever it's done.
     */
    default CompletableFuture<Void> execute(String sqlStatement) {
        return CompletableFuture.runAsync(() -> {
            try (var connection = hikari().getConnection();
                 var statement = connection.prepareStatement(sqlStatement)) {
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Executes an SQL Update to the databases provided by {@link Database#hikari()}
     * @param sqlStatement Statement to be executed as an update
     * @return {@code CompletableFuture<Void>} for the purpose of
     * getting it whenever it's done
     */
    default CompletableFuture<Void> update(String sqlStatement) {
        return CompletableFuture.runAsync(() -> {
            try (var connection = hikari().getConnection();
                 var statement = connection.prepareStatement(sqlStatement)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Executes an SQL Query to the database provided by {@link Database#hikari()}
     * @param sqlStatement Statement to be queried
     * @return {@code CompletableFuture<RowSet>} for the purpose of
     * returning the result and knowing when it's done.
     */
    default CompletableFuture<RowSet> query(String sqlStatement) {
        return CompletableFuture.supplyAsync(() -> {
            try (var connection = hikari().getConnection();
                 var statement = connection.prepareStatement(sqlStatement);
                 var resultSet = statement.executeQuery()) {

                CachedRowSet rowSet = rowSetFactory().createCachedRowSet();
                rowSet.populate(resultSet);

                return rowSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    default CompletableFuture<Long> apply(String statement, int index) {
        return query(statement)
                .thenApply(rowSet -> {
                    try {
                        return rowSet.getLong(index);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}
