/**
 * The main methods for serializing are:
 *          String ObjectMapper.writeValueAsString(Object)
 *          byte[] ObjectMapper.writeValueAsBytes(Object)
 *          void writeValue(Writer)
 *          void writeValue(OutputStream)
 *          void writeValue(File)
 *          void writeValue(DataOutput)
 *          void writeValue(JsonGenerator)
 */

package com.iainhemstock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ObjectMapperSerializationTest {

    private ObjectMapper mapper;
    private Mountain mountain;
    private File outputFile;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        mountain = new Mountain("Catbells", 451);
        outputFile = new File("mountain_output.json");
    }

    @After
    public void tearDown() throws Exception {
        deleteFileIfExists(outputFile);
    }

    private void deleteFileIfExists(File file) {
        if (file.exists())
            file.delete();
    }

    /**
     * Serialize a pojo to a string.
     */
    @Test
    public void testWriteObjectToString() throws JsonProcessingException {
        String json = mapper.writeValueAsString(mountain);

        assertEquals(
                "{`mountainName`:`Catbells`,`height`:451}".replace("`", "\""),
                json);
    }

    /**
     * Serialize a pojo to a File.
     */
    @Test
    public void testWriteObjectToFile() throws IOException {
        mapper.writeValue(outputFile, mountain);

        assertTrue(outputFile.exists());
    }

    /**
     * Serialize a pojo to a FileOutputStream.
     */
    @Test
    public void testWriteObjectToFileOutputStream() throws IOException {
        OutputStream output = new FileOutputStream(outputFile);
        mapper.writeValue(output, mountain);

        assertTrue(outputFile.exists());
    }

    /**
     * Serialize a pojo to a Writer.
     */
    @Test
    public void testWriteObjectToWriter() throws IOException {
        Writer writer = new FileWriter(outputFile);
        mapper.writeValue(writer, mountain);

        assertTrue(outputFile.exists());
    }

    /**
     * By default Jackson serializes java.util.Date objects into its primitive long representation.
     * Output would be similar to {"date":1516442298301}.
     * It is possible to format the date into a readable string instead of a long using a DateFormatter. See next test.
     */
    @Test
    public void testWriteObjectToStringWithDateAsLong() throws IOException {
        Date now = new Date();
        Transaction transaction = new Transaction(now);
        String json = mapper.writeValueAsString(transaction);

        assertEquals(
                String.format("{\"date\":%d}", now.getTime()),
                json);
    }

    /**
     * Following on from the previous test it is possible to format the date to a string representation.
     * Output would be similar to {"date":"2019-12-20"}.
     */
    @Test
    public void testWriteObjectToStringWithDateAsFormattedString() throws IOException {
        Date now = new Date();
        Transaction transaction = new Transaction(now);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(dateFormatter);

        String json = mapper.writeValueAsString(transaction);

        assertEquals(
                String.format("{\"date\":\"%s\"}", mapper.getDateFormat().format(now)),
                json);
    }
}
