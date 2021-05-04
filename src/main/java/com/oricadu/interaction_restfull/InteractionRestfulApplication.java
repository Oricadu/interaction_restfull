package com.oricadu.interaction_restfull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class InteractionRestfulApplication {

    static RestTemplate restTemplate = new RestTemplate();
    static final String url = "http://91.241.64.178:7081/api/users";
    static String sessionId = "";
    static String code = "";


    public static void main(String[] args) {

        MultiValueMap<String, String> headers = new HttpHeaders();


        List<User> userList = Arrays.asList(getAllUsers().getBody());
        for (User user : userList) {
            System.out.println(user);
        }


        headers.add(HttpHeaders.COOKIE, sessionId);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        User newUser = new User((long)3, "James", "Brown", (byte) 24);

        System.out.println(code += postUser(newUser, headers).getBody());

        newUser = new User((long)3, "Thomas", "Shelby", (byte) 24);

        System.out.println(code += putUser(newUser, headers).getBody());




        code += deleteUser(headers).getBody();
        System.out.println(code);

        SpringApplication.run(InteractionRestfulApplication.class, args);

    }

    public static ResponseEntity<User[]> getAllUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity("http://91.241.64.178:7081/api/users", User[].class);
        System.out.println(responseEntity);
        sessionId = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        return responseEntity;

    }

    public static ResponseEntity<String> postUser(User newUser, MultiValueMap<String, String> headers) {
        HttpEntity<User> postUser = new HttpEntity<>(newUser, headers);

        return restTemplate.postForEntity(url, postUser, String.class);

    }

    public static ResponseEntity<String> putUser(User newUser, MultiValueMap<String, String> headers) {
        HttpEntity<User> putUser = new HttpEntity<>(newUser, headers);

        return restTemplate.exchange(url,
                HttpMethod.PUT,
                putUser,
                String.class);


    }

    public static ResponseEntity<String> deleteUser (MultiValueMap<String, String> headers) {
        HttpEntity deleteUser = new HttpEntity(null, headers);
        return restTemplate.exchange(url + "/3",
                HttpMethod.DELETE,
                deleteUser,
                String.class);
    }

}

