/**
 * A transaction is used to group togther statements that either all successfully execute or none of
 * them do.
 *
 * The often used example is in a banking application where money is transferred from one
 * account to anther. First, a statement would be executed that removed the money from the first
 * account then the second statement would execute that credits the money to the second account.
 * However, without operating within a transaction, if the first statement failed aftr the money had
 * been deducted from the first account's balance then the second statement would never run and the
 * money would have vanished.
 */

package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Transactions {

    public static void main(String[] args) {

        Connection cxn = null;
        PreparedStatement statement = null;

        try {
            cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            // By default a Connection commits to the database after each statement is executed.
            // By switching this off we can explicitly commit when we want to.
            cxn.setAutoCommit(false);

            String sql =
                "insert into ? " +
                "(transaction_amount, transaction_date) " +
                "values (?, ?)";
            double amount = 100.0;
            Timestamp now = new Timestamp(System.currentTimeMillis());
            statement = cxn.prepareStatement(sql);

            // deduct the amount from the first account
            statement.setString(1, "account1");
            statement.setDouble(2, -amount);
            statement.setTimestamp(3, now);
            statement.executeUpdate(); // won't actually execute until commit is called later

            // credit the ammount to the second account
            statement.setString(1, "account2");
            statement.setDouble(2, amount);
            statement.setTimestamp(3, now);
            statement.executeUpdate(); // won't actually execute until commit is called later

            // Explicitly tell the connection to execute the statements.
            // Should any of them fail then the exception will be caught and the changes rolled back.
            cxn.commit();
            cxn.setAutoCommit(true);
        }
        catch (SQLException ex) {
            if (cxn != null) {
                try { cxn.rollback(); } // rolls back any changes that were made prior to the failure
                catch (SQLException exc) { exc.printStackTrace(); }
            }
        }
        finally {
            try {
                if (statement != null) statement.close();
                if (cxn != null) cxn.close();
            }
            catch (SQLException ex) { ex.printStackTrace(); }
        }

        System.exit(0);
    }
}
