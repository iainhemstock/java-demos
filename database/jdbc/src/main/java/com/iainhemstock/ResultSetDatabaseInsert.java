/**
 * Once a ResultSet has provided the results of a query it is possible to insert a new row into the
 * database through the ResultSet.
 */

package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.sql.Timestamp;

public class ResultSetDatabaseInsert {

    public static void main(String[] args) {

        String sql = "select * from transactions";
        try (
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            Statement statement = cxn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                            ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(sql))
        {

            //======================================================================================
            // Create a statment that can insert into the database.
            // moveToInsertRow() moves the cursor to a special buffer position where the details of
            // a new row are written. insertRow() commits the new row to the database.
            //======================================================================================
            rs.moveToInsertRow(); // special buffer position
            rs.updateDouble("transaction_amount", 246.80);
            rs.updateTimestamp("transaction_date", new Timestamp(System.currentTimeMillis()));
            rs.insertRow(); // must call this or the new row is not added to the database
        }
        catch (SQLException ex) { ex.printStackTrace(); }

        System.exit(0);
    }
}
