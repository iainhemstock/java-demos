/**
 * Streams were added in Java 8.
 *
 * A stream represents a sequence of objects and operates on a data source such as an array or collection.
 * It can perform things such as filtering, sorting and other such processing. It generally does not
 * modify the underlying data source, rather it returns a new stream with the modifications.
 *
 * A stream's methods can either be intermediate or terminal. If it is intermediate then a new stream
 * reflecting the last operation is returned and more operations can occur on the stream. If it is
 * terminal then a value is typically returned and the stream cannot be accessed anymore. Terminal
 * operations are known as reduction operations as they reduce the stream to a single value, for example,
 * min() will condense the stream and return the smallest value in the stream.
 */
