/**
 * JsonNode is an immutable container for a json value such as a string, boolean, number, object or
 * array. It is similar conceptually to a DOM node. It can return its contained value in all of the
 * primitive forms and string too:
 *              String asText();
 *              int asInt();
 *              boolean asBoolean();
 *              ... etc
 *
 * There are two subclasses, ObjectNode and ArrayNode, of JsonNode that provide extra specific
 * features related to objects and arrays. These subclasses are capable of creating json from scratch.
 *
 * Parsing a JsonNode is useful for when mapping a pojo is undesired.
 *
 * Values can be extracted by get(int index), get(String fieldName), findValue(String fieldName).
 * The difference between get() and findValue() is that get() will only look in the current node for
 * the value with the given field name and return null if it doesn't find it. findValue() on the other
 * hand searches both this node and all child nodes for a match and also returns null if it doesn't
 * find a match.
 *
 * A POJO can be converted into a JsonNode and vice versa.
 */

package com.iainhemstock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class JsonNodeReadTest {

    private ObjectMapper mapper;
    private Reader reader;
    private JsonNode node;

    /**
     * readTree() returns a tree of JsonNode instances containing deserialized data from the json source
     */
    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        reader = new FileReader(getClass().getResource("/user.json").getFile());
        node = mapper.readTree(reader);
    }

    /**
     * Extract the value as an integer.
     */
    @Test
    public void testExtractIntValueFromNode() {
        assertEquals(
                1,
                node.get("id").asInt());
    }

    /**
     * Extract the value as a String.
     */
    @Test
    public void testExtractStringValueFromNode() {
        assertEquals(
                "Sincere@april.biz",
                node.get("email").asText());
    }

    /**
     * The conversion methods as*() can also convert the json value into another compatible type.
     * For example, the id value in the json source is a number but can be read as an int or String
     * amongst others with asInt(), asText() etc.
     */
    @Test
    public void testExtractIntValueAsStringFromNode() {
        assertEquals(
                "1",
                node.get("id").asText());
    }

    /**
     * get() will only find values located in this node's top level. It will return null if it can't
     * find a value by the given name in the top level.
     *
     * To find a value in chid nodes use findValue() or findValues() instead.
     */
    @Test
    public void testNullIsReturnedWhenFieldNameNotFoundInThisNode() {
        JsonNode latitudeNode = node.get("lat");
        assertNull(latitudeNode); // 'lat' is a field located in a child object
    }

    /**
     * Extract a child object from this node.
     */
    @Test
    public void testExtractObjectValueFromNode() {
        JsonNode companyObject = node.get("company");

        assertTrue(companyObject.isObject());

        assertEquals("Romaguera-Crona",
                companyObject.get("name").asText());
        assertEquals("Multi-layered client-server neural-net",
                companyObject.get("catchPhrase").asText());
        assertEquals("harness real-time e-markets",
                companyObject.get("bs").asText());
    }

    /**
     * Extract a child array from this node.
     */
    @Test
    public void textExtractArrayValueFromNode() {
        JsonNode aliasesArray = node.get("aliases");

        assertTrue(aliasesArray.isArray());

        assertEquals("Antonette", aliasesArray.get(0).asText());
        assertEquals("Samantha", aliasesArray.get(1).asText());
        assertEquals("Karianne", aliasesArray.get(2).asText());
    }

    /**
     * An Iterator can be used to loop through all top level field names in this node. Field names
     * in child objects or arrays are not included.
     */
    @Test
    public void testExtractAllTopLevelFieldNames() {
        Iterator<String> fieldNames = node.fieldNames();

        assertEquals("id", fieldNames.next());
        assertEquals("name", fieldNames.next());
        assertEquals("username", fieldNames.next());
        assertEquals("email", fieldNames.next());
        assertEquals("address", fieldNames.next());
        assertEquals("phone", fieldNames.next());
        assertEquals("website", fieldNames.next());
        assertEquals("company", fieldNames.next());
        assertEquals("aliases", fieldNames.next());
    }

    /**
     * An Iterator can be used to loop through all top level values in this node. Values in child
     * objects or arrays are not included.
     */
    @Test
    public void testExtractAllTopLevelValues() {
        Iterator<JsonNode> elements = node.elements();

        assertEquals(1, elements.next().asInt());
        assertEquals("Leanne Graham", elements.next().asText());
        assertEquals("Bret", elements.next().asText());
        assertEquals("Sincere@april.biz", elements.next().asText());
        assertTrue(elements.next().isObject()); // address object
        assertEquals("1-770-736-8031 x56442", elements.next().asText());
        assertEquals("hildegard.org", elements.next().asText());
        assertTrue(elements.next().isObject()); // company object
        assertTrue(elements.next().isArray()); // aliases array
    }

    /**
     * Find a value in this node or one of its children by its field name.
     */
    @Test
    public void testFindValueInThisNodeOrChildNode() {
        String email = node.findValue("email").asText();
        String city = node.findValue("city").asText();
        String latitude = node.findValue("lat").asText();
        JsonNode geo = node.findValue("geo");

        assertEquals("Sincere@april.biz", email);
        assertEquals("Gwenborough", city);
        assertEquals("-37.3159", latitude);
        assertTrue(geo.isObject());
    }

    /**
     * Find all values that share the same field name. This node and all child nodes are searched.
     * A similar method exists that returns a JsonNode List instead of a String List:
     *          List<JsonNode> findValues(String, List<JsonNode>);
     */
    @Test
    public void testExtractAllValuesThatHaveTheSameFieldName() {
        String fieldName = "name";
        List<String> namesList = node.findValuesAsText(fieldName, new ArrayList<>());
//        List<JsonNode> namesList = node.findValues(fieldName, new ArrayList<>());

        assertEquals(2, namesList.size());
        assertEquals("Leanne Graham", namesList.get(0));
        assertEquals("Romaguera-Crona", namesList.get(1));
    }

    /**
     * Convert a pojo to a JsonNode.
     */
    @Test
    public void testConvertPojoToJsonNode() {
        Mountain mountain = new Mountain("Catbells", 451);
        JsonNode mountainNode = mapper.valueToTree(mountain);

        assertEquals("Catbells", mountainNode.get("mountainName").asText());
        assertEquals(451, mountainNode.get("height").asInt());
    }

    @Test
    public void testConvertJsonNodeToPojo() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = "{`mountainName`:`Catbells`,`height`:451}".replace("`", "\"");
        JsonNode node = mapper.readValue(json, JsonNode.class);

        Mountain mountain = mapper.treeToValue(node, Mountain.class);

        assertEquals(
                new Mountain("Catbells", 451),
                mountain);
    }

    /**
     * Get a JsonNode by specifying its path through the child nodes.
     * Here this node contains an object called address which contains an object called geo which
     * contains a field called lat.
     */
    @Test
    public void testGetNodeAtPath() {
        JsonNode latitudeNode = node.at("/address/geo/lat");

        assertNotNull(latitudeNode);
        assertEquals("-37.3159", latitudeNode.asText());
    }

    /**
     * Specify a default value for a json value when it is explicitly set to null.
     * All of the as*() methods can be supplied with a default value.
     */
    @Test
    public void testNullsAreReplacedWithDefaultValues() throws IOException {
        String json = "{\"size\":null}";
        JsonNode jsonNode = mapper.readTree(json);
        int size = jsonNode.asInt(-1);

        assertEquals(-1, size);
    }

    /**
     * If a field is explicitly set to null in the json source then a valid JsonNode that represents
     * the value null is returned. The node's value can be checked with isNull().
     */
    @Test
    public void testExplicitNullReturnsValidJsonNodeContainingNullValue() throws IOException {
        String json = "{\"size\":null}";
        JsonNode jsonNode = mapper.readTree(json);
        JsonNode sizeNode = jsonNode.get("size");

        assertNotNull(sizeNode); // the JsonNode isn't null ...
        assertTrue(sizeNode.isNull()); // ... but the value is the json 'null' value
    }
}
