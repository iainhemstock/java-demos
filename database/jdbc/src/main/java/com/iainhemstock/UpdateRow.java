package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class UpdateRow {

    public static void main(String[] args) {

        try (
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            Statement statement = cxn.createStatement())
        {
            String sql = "update transactions set transaction_amount=987.65 where id=343";
            int rowsAffected = statement.executeUpdate(sql);
        }
        catch(SQLException ex) { ex.printStackTrace(); }

        System.exit(0);
    }
}
