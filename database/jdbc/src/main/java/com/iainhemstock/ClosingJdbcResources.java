/**
 * Taken from https://shinesolutions.com/2007/08/04/how-to-close-jdbc-resources-properly-every-time/
 *
 * There seems to be some debate as to whether JDBC resources and try-with-resources play together
 * nicely or not. Buggy implementations appear to not always close the JDBC resources as you would
 * expect when using a try-with-resources statement.
 *
 * The only way to guarentee that all resources are closed is to not use try-with-resources and
 * just use the regular try, catch and finally instead.
 *
 * The flow goes like this:
 *
 * ---- If the call to DriverManager.getConnection() throws then we never had a Connection object and
 * -- so there is nothing to close. We move into the catch clause to handle the exception.
 *
 * ---- If the call to Connection.createStatement() fails then we never had a Statement object and so
 * -- there is no Statement to close. We move into the finally clause that closes the Connection.
 * -- Then the exception is handled in the catch clause.
 *
 * ---- If the call to Statement.executeQuery() fails then we never had a ResultSet and so there is
 * -- no ResultSet to close. We move into the finally clause that closes the Statement and then move
 * -- into the next finally clause to close the Connection. Then the exception is handled in the catch
 * -- clause.
 *
 * ---- If the call to ResultSet.next() fails then we move into the finally clause that closes the
 * -- ResultSet, then move into the finally clause that closes the Statement and then we move into
 * -- the finally clause that closes the connection. Then the exception is handled in the catch clause.
 */

package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClosingJdbcResources {
    public static void main(String[] args) {

        //==========================================================================================
        // Basic skeleton of closing resoures and exception handling
        //==========================================================================================
        try {
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            try {
                Statement statement = cxn.createStatement();
                try {
                    String sql = "";
                    ResultSet rs = statement.executeQuery(sql);
                    try {
                        while (rs.next()) {
                            // do something with rows in result set
                        }
                    }
                    finally {
                        rs.close();
                    }
                }
                finally {
                    statement.close();
                }
            }
            finally {
                cxn.close();
            }
        }
        catch (SQLException ex) {
            // exception handling
            ex.printStackTrace();
        }

        System.exit(0);
    }
}
