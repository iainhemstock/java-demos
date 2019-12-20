/**
 * Posting to the rest service can be achieved in a couple of ways:
 *
 *      T postForObject(URI uri, Object request, Class<T> responseType;
 *      T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables);
 *      T postForObject(String url, Object request, Class<T> responseType, Object...  uriVariables);
 *
 * where url is the rest service, the request of type Object is the new data (could be in POJO form
 * or an HttpEntity for example), response type is the class type of the resource being created and
 * uriVariables are the any variables that need to be passed to the service. All version return an
 * object of type T that represents the newly created resource.
 */

package com.iainhemstock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestTemplatePostTest extends BaseTest {

    /**
     * Post a new User.
     * It returns a pojo of the newly created resource.
     */
    @Test
    void testPostNewUserThroughPojo() {
        String name = "Bob Dylan";
        String email = "bob@bobdylan.com";
        User newUser = new User(name, email);

        User createdUser = rest.postForObject(POST_USER_URL, newUser, User.class);

        assertEquals(name, createdUser.getName());
        assertEquals(email, createdUser.getEmail());
    }

    /**
     * Post a new user (wrapped in an HttpEntity).
     * It returns a pojo of the newly created resource.
     */
    @Test
    void testPostNewUserThroughRequest() {
        String name = "Bob Dylan";
        String email = "bob@bobdylan.com";

        HttpEntity<User> request = new HttpEntity<>(new User(name, email));
        User createdUser = rest.postForObject(POST_USER_URL, request, User.class);

        assertEquals(name, createdUser.getName());
        assertEquals(email, createdUser.getEmail());
    }

    /**
     * postForLoaction() posts the object to the service and it returns the location of the new resource.
     */
    @Test
    void testPostNewUserForLocation() {
        HttpEntity<User> request = new HttpEntity<>(new User(
                "Bob Dylan",
                "bob@bobdylan.com"));
        URI location = rest.postForLocation(POST_USER_URL, request);
        assertEquals(
                // 11 is the next auto incremented id from this service
                URI.create("http://jsonplaceholder.typicode.com/users/11"),
                location);
    }

    /**
     * postForEntity() posts the object to the service and it returns a response which can be
     * further manipulated as desired.
     */
    @Test
    void testPostNewUserForEntity() {
        HttpEntity<User> request = new HttpEntity<>(new User(
                "Bob Dylan",
                "bob@bobdylan.com"));
        ResponseEntity<String> response = rest.postForEntity(POST_USER_URL, request, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    /**
     * exchange() is the more general form of sending a request. It requires the url, the type of
     * request (i.e. GET, POST etc), request entity and the response type. It returns a response
     * entity.
     */
    @Test
    void testPostNewUserWithExchange() {
        HttpEntity<User> request = new HttpEntity<>(new User(
                "Bob Dylan",
                "bob@bobdylan.com"));
        ResponseEntity<String> response = rest.exchange(
                POST_USER_URL,
                HttpMethod.POST,
                request,
                String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
