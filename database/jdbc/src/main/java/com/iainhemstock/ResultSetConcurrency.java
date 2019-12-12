package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

public class ResultSetConcurrency {

    public static void main(String[] args) {

        try (Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD)) {

            //======================================================================================
            // Find out whether the database supports read only ResultSets and updatable ResultSets.
            //======================================================================================
            DatabaseMetaData dbmd = cxn.getMetaData();
            boolean supportsReadOnly = dbmd.supportsResultSetConcurrency(
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
            boolean supportsUpdatable = dbmd.supportsResultSetConcurrency(
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);

            System.out.println(supportsReadOnly);
            System.out.println(supportsUpdatable);
        }
        catch (SQLException ex) { ex.printStackTrace(); }

        System.exit(0);
    }
}
