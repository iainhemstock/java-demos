/**
 * Jackson provides an easy and convenient way to serialize pojos to json/xml and deserialize
 * json/xml into pojos. It can read json from a byte array, String, InputStream, Reader, File,
 * URL and JsonParser (another Jackson object).
 *
 * The destination for the parsed json can be a pojo, array or collection such as List or Map.
 * Another destination option is a JsonNode (another Jackson object). The JsonNode forms the basis
 * of the alternative tree based structure that Jackson offers.
 *
 * Jackson is comprised of three libraries: jackson-core, jackson-annotations and jackson-databind.
 * Adding the jackson-databind library with maven to the pom automatically includes the other two libraries.
 * For xml mapping then additionally the jackson-dataformat-xml dependency should be added too.
 *
 * The main object in Jackson is ObjectMapper. It maps the json data to the pojo by reflection. It
 * looks for a member variable of the same name (and a getter) as the json field and maps the value
 * to it. It is possible to map a json field to a differently named member variable using @JsonProperty.
 * An immutable object can be mapped to by use of @JsonCreater and @JsonProperty.
 */

package com.iainhemstock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ObjectMapperDeserializationTest
{

    private ObjectMapper mapper;
    private String json;
    private String MOUNTAIN_NAME = "Catbells";
    private int HEIGHT = 451;
    private Mountain EXPECTED_MOUNTAIN = new Mountain(MOUNTAIN_NAME, HEIGHT);

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        json = String.format("{ `mountainName` : `%s`, `height` : %d }", MOUNTAIN_NAME, HEIGHT)
                    .replace("`", "\"");
    }

    /**
     * Map to pojo from a json string.
     */
    @Test
    public void testReadObjectFromString() throws IOException {
        Mountain mountain = mapper.readValue(json, Mountain.class);

        assertEquals(EXPECTED_MOUNTAIN, mountain);
    }

    /**
     * No difference in the client code here but the immutable class has to contain various annotations
     * for the mapping to work.
     */
    @Test
    public void testReadImmutableObjectFromString() throws IOException {
        String s = "{ \"id\" : 1 }";
        ImmutablePost post = mapper.readValue(s, ImmutablePost.class);

        assertEquals(new ImmutablePost(1), post);
    }

    /**
     * Map to pojo from a byte array.
     */
    @Test
    public void testReadObjectFromByteArray() throws IOException {
        byte[] bytes = json.getBytes();
        Mountain mountain = mapper.readValue(bytes, Mountain.class);

        assertEquals(EXPECTED_MOUNTAIN, mountain);
    }

    /**
     * Map to pojo from a StringReader.
     * Any Reader subclass can be used obviously (such as FileReader etc).
     */
    @Test
    public void testReadObjectFromStringReader() throws IOException {
        Reader reader = new StringReader(json);
        Mountain mountain = mapper.readValue(reader, Mountain.class);

        assertEquals(EXPECTED_MOUNTAIN, mountain);
    }

    /**
     * Map to pojo from FileReader.
     */
    @Test
    public void testReadObjectFromFileReader() throws IOException {
        Reader reader = new FileReader(getClass().getResource("/mountain.json").getFile());
        Mountain mountain = mapper.readValue(reader, Mountain.class);

        assertEquals(EXPECTED_MOUNTAIN, mountain);
    }

    /**
     * Map to pojo from InputStream.
     */
    @Test
    public void testReadObjectFromInputStream() throws IOException {
        InputStream input = new FileInputStream(getClass().getResource("/mountain.json").getFile());
        Mountain mountain = mapper.readValue(input, Mountain.class);

        assertEquals(EXPECTED_MOUNTAIN, mountain);
    }

    /**
     * Map to pojo from json located at Url.
     * This examples reads a json response from a rest service.
     */
    @Test
    public void testReadObjectFromUrl() throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/todos/1");
        Todo todo = mapper.readValue(url, Todo.class);

        assertEquals(
                new Todo(1, 1, "delectus aut autem", false),
                todo);
    }

    /**
     * Map to java array from json array of objects.
     */
    @Test
    public void testReadObjectsFromJsonArrayToJavaArray() throws IOException {
        Reader reader = new FileReader(getClass().getResource("/array_of_mountains.json").getFile());
        Mountain[] mountains = mapper.readValue(reader, Mountain[].class);

        assertEquals(2, mountains.length);
        assertEquals(new Mountain("Catbells", 451), mountains[0]);
        assertEquals(new Mountain("Castle Crag", 290), mountains[1]);
    }

    /**
     * Map to java List from json array of objects.
     */
    @Test
    public void testReadObjectsFromJsonArrayToList() throws IOException {
        Reader reader = new FileReader(getClass().getResource("/array_of_mountains.json").getFile());
        List<Mountain> mountains = mapper.readValue(reader, new TypeReference<List<Mountain>>(){});

        assertEquals(2, mountains.size());
        assertEquals(new Mountain("Catbells", 451), mountains.get(0));
        assertEquals(new Mountain("Castle Crag", 290), mountains.get(1));
    }

    /**
     * Map to java Map from json object.
     */
    @Test
    public void testReadObjectintoMap() throws IOException {
        Map<String, Object> map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        assertEquals(2, map.size());
        assertEquals(MOUNTAIN_NAME, map.get("mountainName"));
        assertEquals(HEIGHT, map.get("height"));
    }

    /**
     * Map to JsonNode.
     */
    @Test
    public void testParseObjectIntoJsonNode() throws IOException {
        Reader reader = new FileReader(getClass().getResource("/mountain.json").getFile());
        JsonNode node = mapper.readValue(reader, JsonNode.class);

        assertEquals("Catbells", node.get("mountainName").asText());
        assertEquals(451, node.get("height").asInt());
    }

    /**
     * This json file contains an extra field that is not present in the pojo which causes an exception
     * to be thrown when mapping takes place. Jackson needs to be told to ignore extra fields present
     * in the json source with the FAIL_ON_UNKNOWN_PROPERTIES property set to false.
     *
     * This feature is really useful when the json source, such as a rest service, contains much more
     * data than you need and you only want to read a portion of it.
     */
    @Test
    public void testIgnoreUnknownJsonFields() throws IOException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Reader reader = new FileReader(getClass().getResource("/mountain_with_extra_fields.json").getFile());
        Mountain mountain = mapper.readValue(reader, Mountain.class);

        assertEquals(EXPECTED_MOUNTAIN, mountain);
    }

    /**
     * This json file contains a field that is currently set to null. By default, as a null value cannot be
     * assigned to a java primitive, Jackson ignores the null and the primitive is initialized with
     * its default value (0 for numbers, false for booleans).
     *
     * Setting the FAIL_ON_NULL_FOR_PRIMITIVES property to true ensures a descriptive exception is
     * thrown which clearly explains why the exception was thrown.
     */
    @Test(expected = MismatchedInputException.class)
    public void testFailOnNullJsonPrimitiveValue() throws IOException {
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        Reader reader = new FileReader(getClass().getResource("/mountain_with_null_primitive_field.json").getFile());
        Mountain mountain = mapper.readValue(reader, Mountain.class);
    }
}
