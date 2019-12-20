/**
 * RestTemplate makes reading and writing to a rest api easy. It integrates with Jackson so mapping
 * to and from JSON is simple.
 *
 * There are a couple of ways to request resources from the rest service:
 *
 * - - T getForObject(String url, Class<T> responseType);
 * - - - - This makes a call to the url for a single resource which is returned in T.
 *
 * - - ResponseEntity<T> getForEntity(String url, Class<T> responseType);
 * - - - - This makes a call to the url for a single resource and returns a ResponseEntity with response
 * - - - - type T which can be inspected for headers. body content etc.)
 *
 * Note that these methods typically return a single resource. To return a collection of resources it
 * is possible to use getForObject() that returns the multiple resources in an array.
 */

package com.iainhemstock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static com.iainhemstock.BaseTest.*;
import static org.junit.jupiter.api.Assertions.*;

public class RestTemplateGetTest extends BaseTest
{
    /**
     * Gets the http status code from the response.
     */
    @Test
    public void testGetStatusCode() {
        ResponseEntity<String> response = rest.getForEntity(GET_USER_URL, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Gets the body content as a string from the response.
     */
    @Test
    void testGetResponseBodyAsString() {
        ResponseEntity<String> response = rest.getForEntity(GET_USER_URL, String.class);
        String content = response.getBody();
        assertNotNull(content);
    }

    /**
     * Using Jackson, the response can be manipulated as JSON.
     */
    @Test
    void testGetResponseBodyAsJson() {
        ResponseEntity<String> response = rest.getForEntity(GET_USER_URL, String.class);
        ObjectMapper om = new ObjectMapper();
        try {
            JsonNode root = om.readTree(response.getBody());
            JsonNode id = root.path("id");
            JsonNode name = root.path("name");
            JsonNode email = root.path("email");

            assertEquals(1, id.asInt());
            assertEquals("\"Leanne Graham\"", name.toString());
            assertEquals("\"Sincere@april.biz\"", email.toString());
        }
        catch (JsonMappingException ex) { fail(); }
        catch (JsonProcessingException ex) { fail(); }
    }

    /**
     * The response can be mapped directly into a POJO.
     * Here a single User is mapped to a User object.
     */
    @Test
    void testMapSingleUserToPojo() {
        User user = rest.getForObject(GET_USER_URL, User.class);
        assertEquals(1, user.getId());
        assertEquals("Leanne Graham", user.getName());
        assertEquals("Sincere@april.biz", user.getEmail());
    }

    /**
     * A collection of users are returned from the rest service and mapped into a list of Users.
     */
    @Test
    void testMapMultipleUsersToPojosArray() {
        ResponseEntity<User[]> response = rest.getForEntity(GET_ALL_USERS_URL, User[].class);
        User[] users = response.getBody();

        assertEquals(10, users.length);
        assertEquals(users[0], new User(1, "Leanne Graham", "Sincere@april.biz"));
        // ... etc
    }

    /**
     * To get the headers associated with a url call headForHeaders(url)
     */
    @Test
    void testRetrieveHeaders() {
        HttpHeaders headers = rest.headForHeaders(GET_USER_URL);
        assertTrue(headers.getContentType().includes(MediaType.APPLICATION_JSON));
        // ... etc
    }

    /**
     * To get the content of the Allowed header call optionsForAllow(url)
     */
    @Test
    void testGetAllowedOptions() {
        Set<HttpMethod> allowedHttpMethods = rest.optionsForAllow(GET_USER_URL);
        // sample result could be: [ HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE ]
    }
}
