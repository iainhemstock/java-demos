package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class DeleteRow {

    public static void main(String[] args) {

        try (Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD)) {
            //==================================================================================
            // initially insert row so that it can be deleted next
            //==================================================================================
            String sql = "insert into transactions (transaction_amount, transaction_date)" +
                            "values(?, ?)";
            try (PreparedStatement insertStatement = cxn.prepareStatement(
                                                            sql, Statement.RETURN_GENERATED_KEYS)) {
                insertStatement.setDouble(1, 7999.23);
                insertStatement.setTimestamp(2, Timestamp.valueOf("2019-12-12 00:00:00"));
                int rowsAffected = insertStatement.executeUpdate();

                ResultSet rs = insertStatement.getGeneratedKeys();
                int lastInsertId = -1;
                if (rs.next())
                    lastInsertId = rs.getInt(1);

                //==================================================================================
                // now delete the last inserted row
                //==================================================================================
                sql = "delete from transactions where id=?";
                try (PreparedStatement deleteStatement = cxn.prepareStatement(sql)) {
                    deleteStatement.setInt(1, lastInsertId);
                    rowsAffected = deleteStatement.executeUpdate();
                }
            }
        }
        catch(SQLException ex) { ex.printStackTrace(); }

        System.exit(0);
    }
}
