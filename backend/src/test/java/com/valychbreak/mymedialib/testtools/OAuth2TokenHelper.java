package com.valychbreak.mymedialib.testtools;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

public class OAuth2TokenHelper {
    private TestRestTemplate testRestTemplate;

    public OAuth2TokenHelper(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public String requestToken(String username, String password) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> stringResponseEntity = testRestTemplate.withBasicAuth("gigy", "secret").postForEntity(
                "/oauth/token",
                request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(stringResponseEntity.getBody());
        JsonNode accessToken = jsonNode.get("access_token");

        return accessToken.asText();
    }
}
