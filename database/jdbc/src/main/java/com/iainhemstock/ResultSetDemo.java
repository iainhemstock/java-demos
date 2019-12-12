/**
 * The ResultSet represents the results of executing a Statement. Each result (row) can be iterated
 * through by calling next().
 *
 * A ResultSet cannot be asked how many rows it contains. To find out you would have to iterate through
 * the results with next() and keep count. Bear in mind that if the ResultSet is forward only then it
 * is impossible to go back to the beginning and iterate the rows again.
 *
 * To get actual values in certain columns the get*() methods are called where * is a primitive type
 * (and String too). For example, getInt(), getBoolean(), getDouble(), getString() etc. There are
 * Java classes that represent all of the SQL types so the following is possible too:
 *      Blob blob = rs.getBlob(colName);
 *      Time time = rs.getTime(colName);
 *      Timestamp timestamp = rs.getTimestamp(colName);
 *      Date date = rs.getDate(colName); // note that this java.sql.Date and not java.util.Date!
 *
 * To identify the required column either its name or its index can be specified. If the column names
 * are specified in the select statement then index 1 refers to the first column name specified,
 * index 2 refers to the second column and so on:
 *      'select transaction_date, id, transaction_amount from transactions'
 * Index 1 would be transaction_date, index 2 would be id, index 3 would be transaction_amount.
 * When a wildcard is used in the select statement:
 *      'select * from transactions'
 * then the index corresponds to the order of the columns in the actual database table.
 *
 * A ResultSet is of a certain type. It is either a FORWARD_ONLY, SCROLL_INSENSITIVE or SCROLL_SENSITIVE.
 * A FORWARD_ONLY type means that it can only be traversed in a forward direction and it cannot go
 * back to a previous row.
 * A SCROLL_INSENSITIVE type means that it is possible to traverse it forwards and backwards and that
 * the result set IS NOT affected if the underlying database is altered by another thread whilst it
 * is being read. It can also move the current position to another relative or absolute position.
 * A SCROLL_SENSITIVE type means that it is possible to traverse it forwards and backwards and that
 * the result set IS affected if the underlying database is altered by another thread whilst it is
 * being read. It can also move the current position to another relative or absolute position.
 */
