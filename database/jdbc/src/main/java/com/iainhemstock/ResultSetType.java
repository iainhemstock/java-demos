package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.DatabaseMetaData;

public class ResultSetType {

    ResultSetType() {

        //======================================================================================
        // Find what result set type the current database is
        //======================================================================================
        try (Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD)) {
            boolean isSupported;
            DatabaseMetaData dbmd = cxn.getMetaData();
            isSupported = dbmd.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY);
            isSupported = dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
            isSupported = dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
        }
        catch(SQLException ex) { ex.printStackTrace(); }
    }

    public static void main(String[] args) {
        new ResultSetType();
        System.exit(0);
    }
}
