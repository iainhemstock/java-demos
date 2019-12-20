/**
 * ArrayNode is a mutable container that represents an array in json. It can written to and read from.
 *
 * Values can be added to the end of the array with add(value), inserted at a certain index with
 * insert(insertionIndex, newValue), and removed from the array with remove(removalIndex) or removeAll().
 *
 * Individual values can accessed as JsonNodes with get(index), get(fieldName), findValue(fieldName)
 * and path(fieldName). All values can be looped through with elements().
 */

package com.iainhemstock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ArrayNodeTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
    }

    //==============================================================================================
    // Creation of ArrayNode.
    //==============================================================================================
    @Test
    public void testCreateArrayNode() {
        ArrayNode arrayNode = mapper.createArrayNode();

        assertNotNull(arrayNode);
        assertTrue(arrayNode.isArray());
    }

    /**
     * addArray() adds new array at the end of this array
     */
    @Test
    public void testCreateArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add(123);
        arrayNode.addArray()
                    .add("Green Gable")
                    .add("Great Gable")
                    .add("Kirk Fell");

        assertEquals(
                "[123,[\"Green Gable\",\"Great Gable\",\"Kirk Fell\"]]",
                mapper.writeValueAsString(arrayNode));

    }

    //==============================================================================================
    // Reading values in array.
    //==============================================================================================
    /**
     * Get individual values in array.
     */
    @Test
    public void testGetIndividualValuesInArray() throws JsonProcessingException {
        ArrayNode childArrayNode = mapper.createArrayNode()
                .add(4).add(5).add(6);

        ObjectNode childObjectNode = mapper.createObjectNode()
                .put("mountainName", "Catbells")
                .put("height", 451);

        ArrayNode arrayNode = mapper.createArrayNode()

                //number
                .add(1)

                // boolean
                .add(true)

                // string
                .add("string")

                // null value
                .addNull()

                // array value
                .add(childArrayNode)

                // object value
                .add(childObjectNode);

        assertEquals(
                1,
                arrayNode.get(0).asInt());

        assertEquals(
                true,
                arrayNode.get(1).asBoolean());

        assertEquals(
                "string",
                arrayNode.get(2).asText());

        assertEquals(
                mapper.nullNode(),
                arrayNode.get(3));

        assertEquals(
                childArrayNode,
                arrayNode.get(4));

        assertEquals(
                childObjectNode,
                arrayNode.get(5));
    }

    /**
     * Get all values to be looped through with Iterator.
     */
    @Test
    public void testLoopThroughAllValuesInArray() {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add(1)
                .add(2)
                .add(3);

        Iterator<JsonNode> allValues = arrayNode.elements();

        assertEquals(1, allValues.next().asInt());
        assertEquals(2, allValues.next().asInt());
        assertEquals(3, allValues.next().asInt());
    }

    //==============================================================================================
    // add() values to array (adds values to the end of the array).
    // Can also insert() values at given index.
    //==============================================================================================
    /**
     * Write a string to the array
     */
    @Test
    public void testWriteStringValueToArrayNode() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add("Catbells");

        assertEquals(
                "[\"Catbells\"]",
                mapper.writeValueAsString(arrayNode));
    }

    /**
     * Write an integer to the array
     */
    @Test
    public void testWriteIntegerToArrayNode() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add(451);

        assertEquals(
                "[451]",
                mapper.writeValueAsString(arrayNode));
    }

    /**
     * Write a boolean to the array.
     */
    @Test
    public void testWriteBooleanToArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add(true)
                .add(false);

        assertEquals(
                "[true,false]",
                mapper.writeValueAsString(arrayNode));
    }

    /**
     * Write null value to array
     */
    @Test
    public void testWriteNullValueToArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode()
                .addNull();

        assertEquals(
                "[null]",
                mapper.writeValueAsString(arrayNode));
    }

    /**
     * Write object value to array.
     * Can use add(JsonNode) or addObject().
     */
    @Test
    public void testWriteObjectToArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode();

        // using add(JsonNode)
        arrayNode.add(mapper.createObjectNode()
                        .put("mountainName", "Catbells")
                        .put("height", 451));

        // using addObject()
        arrayNode.addObject()
                .put("mountainName", "Scafell Pike")
                .put("height", 978);

        assertEquals(
                "[{`mountainName`:`Catbells`,`height`:451},{`mountainName`:`Scafell Pike`,`height`:978}]"
                        .replace("`", "\""),
                mapper.writeValueAsString(arrayNode));
    }

    /**
     * Write array value to this ArrayNode.
     * Can use add(JsonNode) or addArray()
     */
    @Test
    public void testWriteArrayToArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode();

        // using add(JsonNode)
        arrayNode.add(mapper.createArrayNode()
                .add("Green Gable")
                .add("Great Gable")
                .add("Kirk Fell"));

        // using addArray()
        arrayNode.addArray()
            .add(801)
            .add(899)
            .add(802);

        assertEquals(
                "[[`Green Gable`,`Great Gable`,`Kirk Fell`],[801,899,802]]"
                        .replace("`", "\""),
                mapper.writeValueAsString(arrayNode));
    }

    /**
     * Write a pojo to array.
     * Output is of the form:
     *      [{fieldName:value,fieldName:value ...}]
     */
    @Test
    public void testWritePojoToArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.addPOJO(new Mountain("Catbells", 451));

        assertEquals(
                "[{\"mountainName\":\"Catbells\",\"height\":451}]",
                mapper.writeValueAsString(arrayNode));
    }

    //==============================================================================================
    // Insert values inside array.
    // All primitive types, JsonNode and String are supported by insert().
    // Special insertArray(), insertObject(), insertNull() and insertPojo() are available too.
    //==============================================================================================
    /**
     * Insert int at front of array.
     */
    @Test
    public void testInsertPrimitiveValueAtFrontOfArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add(2)
                .add(3);

        int insertionIndex = 0;
        int newValue = 1;
        arrayNode.insert(insertionIndex, newValue);

        assertEquals(
                "[1,2,3]",
                mapper.writeValueAsString(arrayNode));
    }
    /**
     * Insert null value at front of this array.
     */
    @Test
    public void testInsertNullValueAtFrontOfArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add(1)
                .add(2)
                .add(3);

        int insertionIndex = 0;
        arrayNode.insertNull(insertionIndex);

        assertEquals(
                "[null,1,2,3]",
                mapper.writeValueAsString(arrayNode));
    }

    /**
     * Insert array at front of array.
     */
    @Test
    public void testInsertArrayAtFrontOfArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add(4)
                .add(5)
                .add(6);

        int insertionIndex = 0;
        arrayNode.insertArray(insertionIndex)
                .add(1)
                .add(2)
                .add(3);

        assertEquals(
                "[[1,2,3],4,5,6]",
                mapper.writeValueAsString(arrayNode));
    }

    /**
     * Insert object at front of array.
     */
    @Test
    public void testInsertObjectAtFrontOfArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add(1)
                .add(2)
                .add(3);

        int insertionIndex = 0;
        arrayNode.insertObject(insertionIndex)
                .put("mountainName", "Catbells")
                .put("height", 451);

        assertEquals(
                "[{`mountainName`:`Catbells`,`height`:451},1,2,3]".replace("`", "\""),
                mapper.writeValueAsString(arrayNode));
    }

    /**
     * Insert pojo at front of array.
     */
    @Test
    public void testInsertPojoAtFrontOfArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add(1)
                .add(2)
                .add(3);

        int insertionIndex = 0;
        arrayNode.insertPOJO(insertionIndex, new Mountain("Catbells", 451));

        assertEquals(
                "[{`mountainName`:`Catbells`,`height`:451},1,2,3]"
                        .replace("`","\""),
                mapper.writeValueAsString(arrayNode));
    }

    //==============================================================================================
    // Remove values from array.
    // Remove single value with remove(int index).
    // Remove all values with removeAll().
    //==============================================================================================
    /**
     * Remove a single value from array.
     */
    @Test
    public void testRemoveSingleValueFromArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add(1)
                .add(2)
                .add(3);

        int removalIndex = 0;
        arrayNode.remove(removalIndex);

        assertEquals(
                "[2,3]",
                mapper.writeValueAsString(arrayNode));
    }

    /**
     * Remove all values from array
     */
    @Test
    public void testRemoveAllValuesFromArray() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add(1)
                .add(2)
                .add(3);

        arrayNode.removeAll();

        assertEquals(
                "[]",
                mapper.writeValueAsString(arrayNode));
    }

    //==============================================================================================
    // Misc
    //==============================================================================================
    @Test
    public void testIfArrayIsEmpty() {
        ArrayNode emptyArrayNode = mapper.createArrayNode();
        ArrayNode populatedArrayNode = mapper.createArrayNode()
                .add(1);

        assertTrue(emptyArrayNode.isEmpty());
        assertFalse(populatedArrayNode.isEmpty());
    }

    @Test
    public void testFindNumberOfValuesInArray() {
        ArrayNode arrayNode = mapper.createArrayNode()
                .add(1)
                .add(2)
                .add(3);

        assertEquals(
                3,
                arrayNode.size());
    }
}
