/**
 * A PreparedStatement is a statement that can be reused by allowing parameters to be specified to it
 * each time it is run.
 */

package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.sql.Timestamp;

public class PreparedStatementDemo {

    public static void main(String[] args) {

        // parameters are specified with ?
        String sql = "select * from transactions where transaction_date < ?";

        try (
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            PreparedStatement pstatement = cxn.prepareStatement(sql))
        {
            //======================================================================================
            // First, query the db for all transactions prior to 2010.
            //======================================================================================
            int param = 1; // the first (and only) parameter in the sql statement
            pstatement.setTimestamp(param, Timestamp.valueOf("2010-01-01 00:00:00"));

            try (ResultSet rs = pstatement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    double transactionAmount = rs.getDouble("transaction_amount");
                    Timestamp timestamp = rs.getTimestamp("transaction_date");

                    System.out.printf("%d, %f %s %n", id, transactionAmount, timestamp);
                }
            }

            //==========================================================================
            // Second, using the same PreparedStatement, query the db for all transactions
            // prior to now
            //==========================================================================
            pstatement.setTimestamp(param, new Timestamp(System.currentTimeMillis()));

            try (ResultSet rs2 = pstatement.executeQuery()) {
                while (rs2.next()) {
                    int id = rs2.getInt("id");
                    double transactionAmount = rs2.getDouble("transaction_amount");
                    Timestamp timestamp = rs2.getTimestamp("transaction_date");

                    System.out.printf("%d, %f %s %n", id, transactionAmount, timestamp);
                }
            }
        }
        catch (SQLException ex) { ex.printStackTrace(); }

        System.exit(0);
    }
}
