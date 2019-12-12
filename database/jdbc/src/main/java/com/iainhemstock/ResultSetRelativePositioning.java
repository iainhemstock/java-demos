package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Timestamp;

public class ResultSetRelativePositioning {

    private int id;
    private double transactionAmount;
    private Timestamp transactionDate;
    private int colIndex;

    ResultSetRelativePositioning() {

        String sql = "select id, transaction_amount, transaction_date from transactions";
        try (
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            Statement statement = cxn.createStatement();
            ResultSet rs = statement.executeQuery(sql))
        {
            //======================================================================================
            // Access each row with relative position
            // Accesses the 3rd, 1st and then 2nd row in that order
            //======================================================================================
            rs.relative(3); // move forward 3 positions from current position
            System.out.printf("Currently processing row %d ---> ", rs.getRow());

            rs.relative(-2); // move backwards 2 positions from current position
            System.out.printf("Currently processing row %d ---> ", rs.getRow());

            rs.relative(1); // move forwards 1 position from current position
            System.out.printf("Currently processing row %d ---> ", rs.getRow());
        }
        catch(SQLException ex) { ex.printStackTrace(); }
    }

    public static void main(String[] args) {
        new ResultSetRelativePositioning();
        System.exit(0);
    }
}
