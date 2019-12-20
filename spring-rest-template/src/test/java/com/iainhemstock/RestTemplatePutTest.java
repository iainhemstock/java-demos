package com.iainhemstock;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestTemplatePutTest extends BaseTest {

    @Test
    void testUpdateExistingResource() {
        User updatedUser = new User("Michael Jones", "mick@work.com");

        // update using put() ...
        rest.put(URI.create(UPDATE_USER_URL), updatedUser);

        // ... or update using exchange()
        HttpEntity<User> updateRequest = new HttpEntity<>(updatedUser);
        ResponseEntity<Void> response = rest.exchange(
                UPDATE_USER_URL,
                HttpMethod.PUT,
                updateRequest,
                Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

