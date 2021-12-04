package com.rest.client.service;

import com.rest.client.exception.BackendException;
import com.rest.client.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService{
    private RestTemplate restTemplate;
    @Value("${backend.url}")
    private String URL;

    public TransactionServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<Map> triggerTransaction(Transaction transaction, String body) {
        HttpEntity<String> entity = new HttpEntity<>(body);
        try {
            return restTemplate.postForEntity(URL, entity, Map.class);
        } catch (RestClientException ex) {
            BackendException backendException = BackendException.create("503", "service unavailable", ex);
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("code", backendException.getCode());
            errorMap.put("message", backendException.getMessage());
            return new ResponseEntity<>(errorMap, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
