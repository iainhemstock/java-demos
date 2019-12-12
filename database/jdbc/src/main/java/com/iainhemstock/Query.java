package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Query {
    public static void main( String[] args )
    {
        String sql = "select * from transactions";
        try (
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            Statement statement = cxn.createStatement();
            ResultSet rs = statement.executeQuery(sql))
        {
            while (rs.next()) {
                System.out.printf("Currently processing row %d ---> ", rs.getRow());

                int id = rs.getInt("id");
                double transactionAmount = rs.getDouble("transaction_amount");
                Date transactionDate = rs.getDate("transaction_date");

                System.out.printf("id: %d transaction: %f date: %s %n",
                                        id, transactionAmount, transactionDate.toString());
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        System.exit(0);
    }
}
