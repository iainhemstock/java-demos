package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Timestamp;

public class ResultSetAbsolutePositioning {

    private int id;
    private double transactionAmount;
    private Timestamp transactionDate;
    private int colIndex;

    ResultSetAbsolutePositioning() {

        String sql = "select id, transaction_amount, transaction_date from transactions";
        try (
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            Statement statement = cxn.createStatement();
            ResultSet rs = statement.executeQuery(sql))
        {
            //======================================================================================
            // Access each row with absolute position
            // Accesses the 3rd, 1st and then 2nd row in that order
            //======================================================================================
            Integer[] rowOrder = { 3, 1, 2 };
            for (int i = 0; i < rowOrder.length; ++i) {

                rs.absolute(rowOrder[i]); // move to the ith row

                System.out.printf("Currently processing row %d ---> ", rs.getRow());

                id = rs.getInt("id");
                transactionAmount = rs.getDouble("transaction_amount");
                colIndex = rs.findColumn("transaction_date");
                transactionDate = rs.getTimestamp(colIndex);

                System.out.printf("%d %f %s %n", id, transactionAmount, transactionDate.toString());
            }
        }
        catch(SQLException ex) { ex.printStackTrace(); }
    }

    public static void main(String[] args) {
        new ResultSetAbsolutePositioning();
        System.exit(0);
    }
}
