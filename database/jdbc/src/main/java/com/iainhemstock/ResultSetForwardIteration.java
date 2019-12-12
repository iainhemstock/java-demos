package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Timestamp;

public class ResultSetForwardIteration {

    public static void main(String[] args) {

        //======================================================================================
        // Loop forwards through the results in the ResultSet
        //======================================================================================
        String sql = "select id, transaction_amount, transaction_date from transactions";
        try (
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            Statement statement = cxn.createStatement();
            ResultSet rs = statement.executeQuery(sql))
        {
            while (rs.next()) {
                System.out.printf("Currently processing row %d ---> ", rs.getRow());

                int id = rs.getInt("id");
                double transactionAmount = rs.getDouble("transaction_amount");
                int colIndex = rs.findColumn("transaction_date");
                Timestamp transactionDate = rs.getTimestamp(colIndex);
            }
        }
        catch(SQLException ex) { ex.printStackTrace(); }

        System.exit(0);
    }
}
