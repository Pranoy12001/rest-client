package com.rest.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.client.model.Transaction;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService{
    private RestTemplate restTemplate;
    ObjectMapper mapper;

    public TransactionServiceImpl(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Map> triggerTransaction(Map<String, String> response, Transaction transaction) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = null;

        try {
            requestJson = mapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            response.put("message", "invalid request");
            response.put("code", "400");
        }

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        return restTemplate.postForEntity("http://localhost:8081/test", entity, Map.class);
    }
}
