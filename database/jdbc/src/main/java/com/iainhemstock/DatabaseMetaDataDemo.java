/**
 * DatabaseMetaData, as the name suggests, provides meta data about the database currently connected
 * to. An instance is obtained through the Connection object.
 */

package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Set;
import java.util.LinkedHashSet;

public class DatabaseMetaDataDemo {

    private static String catalog = null;
    private static String schema = null;
    private static String table = "transactions";
    private static String columnNamePattern = null;
    private static String tableNamePattern = null;
    private static String[] types = null;

    public static void main(String[] args) {

        try (Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD)) {

            //======================================================================================
            // Obtain meta data
            //======================================================================================
            DatabaseMetaData dbmd = cxn.getMetaData();

            //======================================================================================
            // Get all table names
            //======================================================================================
            Set<String> tableNames = getTableNames(dbmd);

            //======================================================================================
            // Get all column names in table
            //======================================================================================
            Set<String> colNames = getColumnNames(dbmd);

            //======================================================================================
            // Get the Connection, URL and username
            //======================================================================================
            Connection conn = dbmd.getConnection();
            String dbURL = dbmd.getURL();
            String dbUserName = dbmd.getUserName();

            //======================================================================================
            // Database details
            //======================================================================================
            int dbMajorVersion = dbmd.getDatabaseMajorVersion();
            int dbMinorVersion = dbmd.getDatabaseMinorVersion();
            String dbProductName = dbmd.getDatabaseProductName();
            String dbProductVersion = dbmd.getDatabaseProductVersion();

            //======================================================================================
            // JDBC driver details
            //======================================================================================
            int jdbcDriverMajorVersion = dbmd.getDriverMajorVersion();
            int jdbcDriverMinorVersion = dbmd.getDriverMinorVersion();
            String jdbcDriverName = dbmd.getDriverName();
            String jdbcDriverVersion  = dbmd.getDriverVersion();

            //======================================================================================
            // Primary key
            // If a single column is the pk then the set will contain a single entry. However if a
            // pk is a compound pk then the set will contain all columns that make up the pk.
            //======================================================================================
            Set<String> primaryKeys = getPrimaryKeys(dbmd);

            //======================================================================================
            // Many boolean-returning supports*() methods
            //======================================================================================
            dbmd.supportsBatchUpdates();
            dbmd.supportsOuterJoins();
            dbmd.supportsUnion();
            dbmd.supportsStoredProcedures();
            dbmd.supportsTransactions();
            // ... etc, etc
        }
        catch (SQLException ex) { ex.printStackTrace(); }

        System.exit(0);
    }

    /**
     *
     */
    static Set<String> getTableNames(DatabaseMetaData dbmd) throws SQLException {
        Set<String> tableNames = new LinkedHashSet<>();
        ResultSet rs = dbmd.getTables(catalog, schema, tableNamePattern, types);

        while (rs.next()) {
            // col 3 of the result set contains the col names, see java docs for getTables()
            tableNames.add(rs.getString(3));
        }

        return tableNames;
    }

    /**
     *
     */
    static Set<String> getColumnNames(DatabaseMetaData dbmd) throws SQLException {
        Set<String> columnNames = new LinkedHashSet<>();
        ResultSet rs = dbmd.getColumns(catalog, schema, table, columnNamePattern);

        while (rs.next()) {
            // col 4 of the result set contains the col names, see java docs for getColumns()
            columnNames.add(rs.getString(4));
        }

        return columnNames;
    }

    /**
     *
     */
    static Set<String> getPrimaryKeys(DatabaseMetaData dbmd) throws SQLException {
        Set<String> primaryKeys = new LinkedHashSet<>();
        ResultSet rs = dbmd.getPrimaryKeys(catalog, schema, table);

        while (rs.next()) {
            // col 4 of the result set contains the col names, see java docs for getPrimaryKeys()
            primaryKeys.add(rs.getString(4));
        }

        return primaryKeys;
    }

}
