/**
 * ResponseEntity is a sublass of HttpEntity.
 *
 * It contains headers, body and a status code (from the server).
 *
 * Accessor methods are getStatusCode(), getStatusCodeValue() and the inherited getHeaders() and getBody()
 *
 * It can be used in Spring MVC as a return value from a @Controller method
 *
 *      @RequestMapping("/handle")
 *      public ResponseEntity<String> handle() {
 *          URI location = ...;
 *          HttpHeaders responseHeaders = new HttpHeaders();
 *          responseHeaders.setLocation(location);
 *          responseHeaders.set("MyResponseHeader", "MyValue");
 *          return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
 *      }
 */

package com.iainhemstock;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResponseEntityTest extends BaseTest {

    @Test
    void testInstantiateDefaultResponse() {
        // compile time error: no default constructor
        // new ResponseEntity<User>();
    }

    @Test
    void testInstantiateResponseWithStatusCode() {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        assertNotNull(response);
    }

    @Test
    void testInstantiateResponseWithHeadersAndStatusCode() {
        ResponseEntity<String> response = new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
        assertNotNull(response);
    }

    @Test
    void testInstantiateResponseWithBodyAndStatusCode() {
        ResponseEntity<String> response = new ResponseEntity<>("body content", HttpStatus.ACCEPTED);
        assertNotNull(response);
    }

    @Test
    void testInstantiateResponseWithBodyAndHeadersAndStatusCode() {
        ResponseEntity<String> response = new ResponseEntity<>(
                "body content",
                new HttpHeaders(),
                HttpStatus.CREATED);

        assertNotNull(response);
    }

    /**
     * Each builder provides further configuration than is shown here.
     * For example the OK response could be further configured by:
     *      ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).build()
     */
    @Test
    void testInstantiatePrebuiltResponses() {
        ResponseEntity<String> okResponseWithBody = ResponseEntity.ok("body content");
        ResponseEntity<String> okResponse = ResponseEntity.ok().build();
        ResponseEntity<String> acceptedResponse = ResponseEntity.accepted().build();
        ResponseEntity<String> notFoundResponse = ResponseEntity.notFound().build();
        ResponseEntity<String> noContentResponse = ResponseEntity.noContent().build();
        ResponseEntity<String> badRequestResponse = ResponseEntity.badRequest().build();
        ResponseEntity<String> httpStatusResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        ResponseEntity<String> httpStatusCodeResponse = ResponseEntity.status(404).build();
        ResponseEntity<String> unprocessableEntityResponse = ResponseEntity.unprocessableEntity().build();
        ResponseEntity<String> createdResponse = ResponseEntity.created(URI.create(GET_ALL_USERS_URL)).build();

        // shortcut for status 404 (not found) and empty body
        ResponseEntity.of(Optional.empty());

        // shortcut for status 200 (OK) and given body
        ResponseEntity.of(Optional.of("body content"));
    }

    @Test
    void testGetStatusCodeFromResponse() {
        ResponseEntity<String> response = new ResponseEntity<>(
                "body content",
                new HttpHeaders(),
                HttpStatus.OK);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
    }
}
