/**
 * An ObjectNode is a mutable container that represents a json object.
 *
 * A field can be put() into the ObjectNode. All of the primitive and primitive wrapper types are
 * supported. put() will either add the field if it is not already present or replace it if it is.
 *
 * Special versions of put() are also available:
 *              putNull(String fieldName)
 *              putObject(String fieldName)
 *              putArray(String fieldName)
 *
 * putObject() and putArray() just adds an empty object or array to the current node. To add a
 * pre-existing object or array that maybe already contains fields use:
 *              set(String fieldName, JsonNode objectOrArrayNode)
 *
 * get() returns a field as a JsonNode.
 *
 * Replacing a existing field is easier with put() as it replaces any existing field.
 * The method replace(String fieldName, JsonNode value) is useful when the value to be replaced is in
 * JsonNode form rather than its primitive form.
 *
 * Fields can be removed with remove(String fieldName), remove(Collection fieldNames), removeAll()
 * and retain(Collection fieldNames). Note that retain() removes all fields EXCEPT the ones specified
 * in the collection.
 */

package com.iainhemstock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

public class ObjectNodeTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
    }

    //==============================================================================================
    // Creation
    //==============================================================================================
    /**
     * Create a mutable ObjectNode through ObjectMapper.
     */
    @Test
    public void testObjectNodeCreation() {
        ObjectNode objectNode = mapper.createObjectNode();

        assertNotNull(objectNode);
        assertTrue(objectNode.isObject());
    }

    /**
     * with() returns a field as an ObjectNode. If the field doesn't exist then an ObjectNode is created,
     * added and returned. If the field exists but is not an object type then an exception is thrown.
     */
    @Test
    public void testCreateObjectValueIfNotExists() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode();;
        objectNode.with("details")
                .put("mountainName", "Catbells")
                .put("height", 451);

        assertEquals(
                "{\"details\":{\"mountainName\":\"Catbells\",\"height\":451}}",
                mapper.writeValueAsString(objectNode));

        assertEquals(
                "{\"mountainName\":\"Catbells\",\"height\":451}",
                mapper.writeValueAsString(objectNode.with("details")));
    }

    /**
     * withArray() returns a field as an ArrayNode. If the field doesn't exist then an ArrayNode is
     * created, added and returned. If the field exists but is not an array type then an exception
     * is thrown.
     */
    @Test
    public void testCreateArrayValueIfNotExists() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode();
        ArrayNode visibleSummitsNode = objectNode.withArray("visibleSummits")
                .add("Green Gable")
                .add("Great Gable")
                .add("Kirk Fell");

        assertEquals(
                "{`visibleSummits`:[`Green Gable`,`Great Gable`,`Kirk Fell`]}".replace("`", "\""),
                mapper.writeValueAsString(objectNode));

        assertEquals(
                "[`Green Gable`,`Great Gable`,`Kirk Fell`]".replace("`", "\""),
                mapper.writeValueAsString(objectNode.withArray("visibleSummits")));
    }

    //==============================================================================================
    // Get values from ObjectNode
    //==============================================================================================
    /**
     * Get all field names in object in Iterator.
     */
    @Test
    public void testGetAllFieldNames() {
        ObjectNode objectNode = mapper.createObjectNode()
                .put("mountainName", "Catbells")
                .put("height", 451);

        Iterator<String> fieldNames = objectNode.fieldNames();

        assertEquals("mountainName", fieldNames.next());
        assertEquals("height", fieldNames.next());
    }

    /**
     * Get individual top level values from object.
     * To get values from child nodes use findValue() or at().
     */
    @Test
    public void testGetIndividualValuesFromObject() {
        ArrayNode childArrayNode = mapper.createArrayNode()
                .add(1).add(2).add(3);

        ObjectNode childObjectNode = mapper.createObjectNode()
                .put("intValue", 1)
                .put("booleanValue", true);

        ObjectNode objectNode = mapper.createObjectNode()
                .put("intValue", 1)
                .put("booleanValue", true)
                .put("stringValue", "a string")
                .putNull("nullValue");
        objectNode.set("childArrayValue", childArrayNode);
        objectNode.set("childObjectValue", childObjectNode);

        assertEquals(
                1,
                objectNode.get("intValue").asInt());

        assertEquals(
                true,
                objectNode.get("booleanValue").asBoolean());

        assertEquals(
                "a string",
                objectNode.get("stringValue").asText());

        assertEquals(
                mapper.nullNode(),
                objectNode.get("nullValue"));

        assertEquals(
                childArrayNode,
                objectNode.get("childArrayValue"));

        assertEquals(
                childObjectNode,
                objectNode.get("childObjectValue"));
    }

    /**
     * Get all top level values from object in an Iterator.
     * To get valus from child nodes use findValue() or at().
     */
    @Test
    public void testGetAllValuesFromObject() {
        ObjectNode objectNode = mapper.createObjectNode()
                .put("intValue", 123)
                .put("booleanValue", false)
                .put("stringValue", "a string");

        Iterator<JsonNode> allValues = objectNode.elements();

        assertEquals(123, allValues.next().asInt());
        assertEquals(false, allValues.next().asBoolean());
        assertEquals("a string", allValues.next().asText());
    }

    /**
     * Get a value in a child object of this object.
     * get() only looks in the top level object for a value so to get a value in a child object or
     * array use findValue() or at() instead.
     */
    @Test
    public void testGetValueFromChildObject() throws IOException {
        Reader reader = new FileReader(getClass().getResource("/user.json").getFile());
        ObjectNode objectNode = (ObjectNode) mapper.readTree(reader);

        assertEquals(
                "Multi-layered client-server neural-net",
                objectNode.findValue("catchPhrase").asText());

        assertEquals(
                "-37.3159",
                objectNode.at("/address/geo/lat").asText());
    }

    //==============================================================================================
    // Write values to ObjectNode
    //==============================================================================================
    /**
     * Write a string value to an ObjectNode.
     */
    @Test
    public void testWriteStringValueToObjectNode() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("mountainName", "Catbells");

        assertEquals(
                "{`mountainName`:`Catbells`}".replace("`", "\""),
                mapper.writeValueAsString(objectNode));
    }

    /**
     * Write an int value to an ObjectNode.
     */
    @Test
    public void testWriteIntValueToObjectNode() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("height", 451);

        assertEquals(
                "{`height`:451}".replace("`", "\""),
                mapper.writeValueAsString(objectNode));
    }

    /**
     * Write a boolean value to an ObjectNode
     */
    @Test
    public void testWriteBooleanToObjectNode() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode()
                .put("isValid", true)
                .put("isActive", false);

        assertEquals(
                "{\"isValid\":true,\"isActive\":false}",
                mapper.writeValueAsString(objectNode));
    }

    /**
     * Write a null value to an ObjectNode.
     */
    @Test
    public void testWriteNullValeToObjectNode() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.putNull("date");

        assertEquals(
                "{\"date\":null}",
                mapper.writeValueAsString(objectNode));
    }

    /**
     * Write an object value to an ObjectNode.
     */
    @Test
    public void testWriteObjectValueToObjectNode() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.putObject("details")
                .put("mountainName", "Catbells")
                .put("height", 451);

        assertEquals(
                "{`details`:{`mountainName`:`Catbells`,`height`:451}}".replace("`", "\""),
                mapper.writeValueAsString(objectNode));
    }

    /**
     * Write an array value to an ObjectNode.
     */
    @Test
    public void testWriteArrayValueToObjectNode() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.putArray("visibleSummits")
                .add("Green Gable")
                .add("Great Gable")
                .add("Kirk Fell");

        assertEquals(
                "{`visibleSummits`:[`Green Gable`,`Great Gable`,`Kirk Fell`]}".replace("`", "\""),
                mapper.writeValueAsString(objectNode));
    }

    /**
     * Write a pojo to an ObjectNode.
     */
    @Test
    public void testWritePojoToObjectNode() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode()
                .putPOJO("mountain", new Mountain("Catbells", 451));

        assertEquals(
                "{`mountain`:{`mountainName`:`Catbells`,`height`:451}}".replace("`", "\""),
                mapper.writeValueAsString(objectNode));
    }

    /**
     * Write other ObjectNode to this ObjectNode.
     */
    @Test
    public void testWriteOtherObjectNodeToThisObjectNode() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode()
                .put("mountainName", "Catbells")
                .put("height", 451);

        ObjectNode parentObjectNode = mapper.createObjectNode()
                .set("details", objectNode);

        assertEquals(
                "{`details`:{`mountainName`:`Catbells`,`height`:451}}".replace("`", "\""),
                mapper.writeValueAsString(parentObjectNode));

    }

    /**
     * Set all the fields from the other ObjectNode on this ObjectNode.
     */
    @Test
    public void testSetAllFieldsOfOtherObjectNodeOnThisObjectNode() throws IOException {
        ObjectNode otherNode = mapper.createObjectNode()
                .put("mountainName", "Catbells")
                .put("height", 451);

        ObjectNode thisNode = mapper.createObjectNode()
                .setAll(otherNode);

        assertEquals(
                "{\"mountainName\":\"Catbells\",\"height\":451}",
                mapper.writeValueAsString(thisNode));
    }

    //==============================================================================================
    // Remove fields from ObjectNode.
    // To remove single field use remove(String fieldName).
    // To remove multiple fields use remove(Collection<String>).
    // To remove all fields use removeAll().
    // To remove all fields except specified ones use retain()
    //==============================================================================================
    /**
     * Remove a single field from an ObjectNode.
     */
    @Test
    public void testRemoveSingleFieldFromObjectNode() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode()
                .put("mountainName", "Catbells")
                .put("height", 451);

        objectNode.remove("mountainName");

        assertEquals(
                "{\"height\":451}",
                mapper.writeValueAsString(objectNode));
    }

    /**
     * Remove multiple fields specified by field names in Collection.
     */
    @Test
    public void testRemoveMultipleFieldsFromObjectNode() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode()
                .put("mountainName", "Catbells")
                .put("height", 451)
                .put("region", "Western");

        objectNode.remove(Arrays.asList("mountainName", "height"));

        assertEquals(
                "{\"region\":\"Western\"}",
                mapper.writeValueAsString(objectNode));
    }

    /**
     * Remove all fields from ObjectNode.
     */
    @Test
    public void testRemoveAllFieldsFromObjectNode() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode()
                .put("mountainName", "Catbells")
                .put("height", 451)
                .put("region", "Western");

        objectNode.removeAll();

        assertEquals(
                "{}",
                mapper.writeValueAsString(objectNode));
    }

    /**
     * Remove all fields except the specified fields.
     * The retain() method has two forms:
     *              ObjectNode retain(String... fieldNames);
     *              ObjectNode retain(Collection<String> fieldNames);
     */
    @Test
    public void testRemoveAllFieldsExceptSpecifiedFields() throws  IOException {
        ObjectNode objectNode = mapper.createObjectNode()
                .put("mountainName", "Catbells")
                .put("height", 451)
                .put("region", "Western");

        // these are functionally equivalent
        objectNode.retain("height");
        objectNode.retain(Arrays.asList("height"));

        assertEquals(
                "{\"height\":451}",
                mapper.writeValueAsString(objectNode));
    }

    //==============================================================================================
    // Replacing fields in ObjectNode
    //==============================================================================================
    /**
     * When put()ting a field with an existing name into an ObjectNode it replaces the old field.
     */
    @Test
    public void testPuttingFieldWithExistingNameReplacesOldField() throws IOException {
        ObjectNode objectNode = mapper.createObjectNode();

        objectNode
                .put("mountainName", "Catbells")
                .put("mountainName", "Scafell Pike");

        assertEquals(
                "{\"mountainName\":\"Scafell Pike\"}",
                mapper.writeValueAsString(objectNode));
    }

    //==============================================================================================
    // Misc
    //==============================================================================================
    /**
     * Find the number of fields in the ObjectNode.
     */
    @Test
    public void testFindNumberOfFieldsInObjectNode() {
        ObjectNode objectNode = mapper.createObjectNode()
                .put("mountainName", "Catbells")
                .put("height", 451);

        assertEquals(2, objectNode.size());
    }
}
