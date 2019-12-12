/**
 * A CallableStatement is a way to call stored procedures in the database. It is similar to a
 * PreparedStatement in that it has parameters which can be supplied to it.
 */

package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CallableStatementDemo {

    public static void main(String[] args) {

        try (
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            CallableStatement statement = cxn.prepareCall("{call calculateStatistics(?, ?)}"))
        {

            //======================================================================================
            // Query using stored procedure.
            // Calls a stored procedure named calculateStatistics.
            // Throws if stored procedure doesn't exist in db.
            //======================================================================================
            statement.setInt(1, 123);
            statement.setString(2, "A");

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    // do something with results
                }
            }

            //======================================================================================
            // Update using stored procedure.
            // Calls a stored procedure named calculateStatistics.
            // Throws if stored procedure doesn't exist in db.
            //======================================================================================
            statement.setInt(1, 123);
            statement.setString(2, "A");

            int rowsAffected = statement.executeUpdate();

            //======================================================================================
            // Calls a stored procedure in batch update.
            // Throws if stored procedure doesn't exist in db.
            //======================================================================================
            statement.setInt(1, 123);
            statement.setString(2, "A");
            statement.addBatch();

            statement.setInt(1, 456);
            statement.setString(2, "B");
            statement.addBatch();

            statement.setInt(1, 789);
            statement.setString(2, "C");
            statement.addBatch();

            int[] recordsAffected = statement.executeBatch();

            //======================================================================================
            // A stored procedure can return OUT parameters in addition to (or instead of) a
            // result set. The have to be registered with the CallableStatement before it executes
            // and can be accessed afterwards.
            // Throws if stored procedure doesn't exist in db.
            //======================================================================================
            statement.setInt(1, 123);
            statement.setString(2, "A");

            statement.registerOutParameter(1, java.sql.Types.INTEGER);
            statement.registerOutParameter(2, java.sql.Types.VARCHAR);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    // do something with results
                }
                int out1Param = statement.getInt(1);
                String out2Param = statement.getString(2);
            }
        }
        catch (SQLException ex) { ex.printStackTrace(); }
    }

}
