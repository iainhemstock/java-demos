/**
 * Once a ResultSet has provided the results of a query it is possible to update a row in the
 * database through the ResultSet.
 */

package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

public class ResultSetDatabaseUpdate {

    public static void main(String[] args) {

        String sql = "select * from transactions";
        try (
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            Statement statement = cxn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                            ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(sql))
        {
            //======================================================================================
            // Create a statment that can update the database.
            // Must call updateRow() or else the changes are not committed to the database.
            //======================================================================================
            rs.absolute(1); // update the first row
            rs.updateDouble("transaction_amount", 555.66);
            rs.updateRow();
        }
        catch (SQLException ex) { ex.printStackTrace(); }

        System.exit(0);
    }
}
