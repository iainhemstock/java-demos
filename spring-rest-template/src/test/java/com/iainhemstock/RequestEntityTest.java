/**
 * HttpRequest is a subclass of HttpEntity.
 *
 * It contains headers, body, http method and URI.
 *
 * Accessor methods are getURI(), getType() and the inherited getHeaders() and getBody()
 *
 * It can be used in Spring MVC as a parameter in a @Controller method
 *      @GetMapping("/")
 *      public void doSomething(RequestEntity<String> request);
 */

package com.iainhemstock;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

public class RequestEntityTest extends BaseTest {

    @Test
    void testInstantiateDefaultRequest() {
        // compile time error: no default constructor
        // new RequestEntity();
    }

    @Test
    void testInstantiateRequestWithMethodAndURI() {
        RequestEntity<User> request = new RequestEntity<>(
                HttpMethod.GET,
                URI.create(GET_ALL_USERS_URL));

        assertNotNull(request);
    }

    @Test
    void testInstantiateRequestWithHeadersAndMethodAndURI() {
        RequestEntity<User> request = new RequestEntity<>(
                new HttpHeaders(),
                HttpMethod.GET,
                URI.create(GET_ALL_USERS_URL));

        assertNotNull(request);
    }

    @Test
    void testInstantiateRequestWithBodyAndMethodAndURI() {
        RequestEntity<User> request = new RequestEntity<>(
                new User(),
                HttpMethod.GET,
                URI.create(GET_ALL_USERS_URL));

        assertNotNull(request);
    }

    @Test
    void testInstantiateRequestWithBodyAndMethodAndURIAndType() {
        RequestEntity<User> request = new RequestEntity<>(
                new User(),
                HttpMethod.GET,
                URI.create(GET_ALL_USERS_URL),
                User.class);

        assertNotNull(request);
    }

    @Test
    void testInstantiateRequestWithBodyAndHeadersAndMethodAndURI() {
        RequestEntity<User> request = new RequestEntity<>(
                new User(),
                new HttpHeaders(),
                HttpMethod.GET,
                URI.create(GET_ALL_USERS_URL));

        assertNotNull(request);
    }

    @Test
    void testInstantiateRequestWithBodyAndHeadersAndMethodAndURIAndType() {
        RequestEntity<User> request = new RequestEntity<>(
                new User(),
                new HttpHeaders(),
                HttpMethod.GET,
                URI.create(GET_ALL_USERS_URL),
                User.class);

        assertNotNull(request);
    }

    /**
     * RequestEntity, using a builder, can build requests for GET, POST, PUT, PATCH and DELETE.
     * This example creates a GET request.
     */
    @Test
    void testInstantiateRequestWithGetMethodFromBuilder() {
        RequestEntity<Void> request = RequestEntity.get(URI.create(GET_ALL_USERS_URL))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        assertNotNull(request);
        assertTrue(request.getHeaders().getAccept().contains(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetHttpMethodFromRequest() {
        RequestEntity<User> request = new RequestEntity<>(
                HttpMethod.GET,
                URI.create(GET_ALL_USERS_URL));

        assertEquals(HttpMethod.GET, request.getMethod());
    }

    @Test
    void testGetURIFromRequest() {
        RequestEntity<User> request = new RequestEntity<>(
                HttpMethod.GET,
                URI.create(GET_ALL_USERS_URL));

        assertEquals(URI.create(GET_ALL_USERS_URL), request.getUrl());
    }

    @Test
    void testGetTypeFromRequest() {
        RequestEntity<User> request = new RequestEntity<>(
                new User(),
                HttpMethod.GET,
                URI.create(GET_ALL_USERS_URL),
                User.class);

        assertEquals(User.class, request.getType());
    }
}
