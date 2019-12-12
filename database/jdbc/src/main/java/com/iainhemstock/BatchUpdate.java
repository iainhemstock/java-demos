/**
 * Batch updates is where multiple update statements are grouped together and sent to the database
 * in one go. Network traffic is reduced since only one group of statements are sent rather multiple
 * individual statements.
 *
 * It os possible to batch update, insert and delete statements using either Statement or
 * PreparedStatement. It is not possible to have a SELECT statement in a batch.
 *
 * One important thing to remember is that the database still executes each statement in a batch
 * individually and if one of the later statements fails for some reason the previous statements have
 * still been applied which will leave the database in an invalid and inconsistant state. To work
 * around this the statements in the batch can be executed within a transaction meaning that if any
 * of the statements fail it is possible to perform a rollback putting the database's state back to
 * where it was before any of the batch statements were executed.
 */

package com.iainhemstock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchUpdate {

    private static final int FIRST_PARAM = 1;
    private static final int SECOND_PARAM = 2;
    private static final String SQL_UPDATE_TRANSACTION_AMOUNT =
        "update transactions " +
        "set transaction_amount=? " +
        "where id=?";


    public static void main(String[] args) {

        try (
            Connection cxn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
            Statement statement = cxn.createStatement();
            PreparedStatement pstatement = cxn.prepareStatement(SQL_UPDATE_TRANSACTION_AMOUNT))
        {

            //======================================================================================
            // Batch update with Statement
            //======================================================================================
            statement.addBatch("update transactions set transaction_amount=1 where id=341");
            statement.addBatch("update transactions set transaction_amount=2 where id=342");
            statement.addBatch("update transactions set transaction_amount=3 where id=343");

            // executeBatch() returns an int array where each entry tells how many rows were
            // affected by each sql statement in the batch.
            int[] recordsAffected = statement.executeBatch();

            //======================================================================================
            // Batch update with PreparedStatement.
            // After each parameter has been set the statement is added to the batch. Once all
            // statements have been prepared the batch is executed.
            //======================================================================================
            pstatement.setDouble(FIRST_PARAM, 1);
            pstatement.setInt(SECOND_PARAM, 341);
            pstatement.addBatch();

            pstatement.setDouble(FIRST_PARAM, 2);
            pstatement.setInt(SECOND_PARAM, 342);
            pstatement.addBatch();

            pstatement.setDouble(FIRST_PARAM, 3);
            pstatement.setInt(SECOND_PARAM, 343);
            pstatement.addBatch();

            recordsAffected = pstatement.executeBatch();

            //======================================================================================
            // Preparing a batch update inside a loop.
            //======================================================================================
            int[] rowIds = { 341, 342, 343 };
            double[] updatedTransactionAmounts = { 123.4, 234.5, 345.6 };

            for (int i = 0; i < rowIds.length; ++i) {
                pstatement.setDouble(FIRST_PARAM, updatedTransactionAmounts[i]);
                pstatement.setInt(SECOND_PARAM, rowIds[i]);
                pstatement.addBatch();
            }

            recordsAffected = pstatement.executeBatch();
        }
        catch (SQLException ex) { ex.printStackTrace(); }

        System.exit(0);
    }
}
