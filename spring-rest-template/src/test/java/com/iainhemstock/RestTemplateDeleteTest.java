package com.iainhemstock;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestTemplateDeleteTest extends BaseTest {

    @Test
    void testDeleteExistingUser() {

        // delete resource at url ...
        rest.delete(DELETE_USER_URL);

        // ... or using exchange()
        User user = new User("Leanne Graham", "Sincere@april.biz");
        HttpEntity<User> deleteRequest = new HttpEntity<>(user);
        ResponseEntity<Void> response = rest.exchange(
                DELETE_USER_URL,
                HttpMethod.DELETE,
                deleteRequest,
                Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteAllExistingUsers() {
        User[] users = rest.getForObject(GET_ALL_USERS_URL, User[].class);
        System.out.println(users.length);

        for (User user : users) {
            rest.delete(String.format("%s/users/%d", BASE_URL, user.getId()));
        }
    }
}
