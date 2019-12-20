/**
 * An HttpEntity represents both an http request and http response. It is the superclass of
 * HttpRequest and HttpResponse.
 *
 * It optionally contains headers and a body.
 */

package com.iainhemstock;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

public class HttpEntityTest {

    @Test
    void testInstantiateEmptyEntityWithNoHeadersAndNoBody() {
        // compile time error: protected constructor
        // new HttpEntity<User>();

        HttpEntity entity = HttpEntity.EMPTY;
    }

    /**
     * Note that HttpHeaders is a subclass of MultiValueMap so can be passed to HttpEntity constructor
     */
    @Test
    void testInstantiateEntityWithGivenHeadersAndNoBody() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> entity = new HttpEntity<>(headers);
    }

    @Test
    void testInstantiateEntityWithNoHeadersAndGivenBody() {
        HttpEntity<User> entity = new HttpEntity<>(new User());
        HttpEntity<String> entity2 = new HttpEntity<String>("some string");
        HttpEntity<Integer> entity3 = new HttpEntity<>(123);
    }

    @Test
    void testInstantiateEntityWithGivenHeadersAndGivenBody() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        User user = new User();

        HttpEntity<User> entity = new HttpEntity<>(user, headers);
    }

    @Test
    void testGetHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        assertEquals(headers, entity.getHeaders());
    }

    @Test
    void testGetBody() {
        User user = new User();
        assertEquals(new User(), new HttpEntity<>(user).getBody());

        assertEquals(123, new HttpEntity<>(123).getBody());
    }

    @Test
    void testEntityHasBody() {
        assertTrue(new HttpEntity<>("body content", new HttpHeaders()).hasBody());
        assertTrue(new HttpEntity<>("body content").hasBody());
        assertFalse(new HttpEntity<String>(new HttpHeaders()).hasBody());
        assertFalse(HttpEntity.EMPTY.hasBody());
    }
}
