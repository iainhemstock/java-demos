package com.iainhemstock;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.web.client.RestTemplate;

public class BaseTest {

    protected static RestTemplate rest;
    protected static String BASE_URL;
    protected static String GET_ALL_USERS_URL;
    protected static String GET_USER_URL;
    protected static String POST_USER_URL;
    protected static String UPDATE_USER_URL;
    protected static String DELETE_USER_URL;

    @BeforeAll
    static void beforeAll() {
        rest = new RestTemplate();
        BASE_URL = "https://jsonplaceholder.typicode.com";
        GET_ALL_USERS_URL = "https://jsonplaceholder.typicode.com/users";
        GET_USER_URL = "https://jsonplaceholder.typicode.com/users/1";
        POST_USER_URL = "https://jsonplaceholder.typicode.com/users";
        UPDATE_USER_URL = "https://jsonplaceholder.typicode.com/users/1";
        DELETE_USER_URL = "https://jsonplaceholder.typicode.com/users/1";
    }

}
