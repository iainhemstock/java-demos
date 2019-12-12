package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Timestamp;

public class ResultSetReverseIteration {

    private int id;
    private double transactionAmount;
    private Timestamp transactionDate;
    private int colIndex;

    public static void main(String[] args) {

        String sql = "select id, transaction_amount, transaction_date from transactions";
        try (
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            Statement statement = cxn.createStatement();
            ResultSet rs = statement.executeQuery(sql))
        {
            //======================================================================================
            // Loop backwards through the results in the ResultSet
            //======================================================================================
            rs.afterLast(); // sets the position to one after the last row

            while (rs.previous()) {
                System.out.printf("Currently processing row %d ---> ", rs.getRow());
            }
        }
        catch(SQLException ex) { ex.printStackTrace(); }

        System.exit(0);
    }
}
