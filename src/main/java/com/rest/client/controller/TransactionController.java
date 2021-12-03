package com.rest.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.client.model.Transaction;
import com.rest.client.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TransactionController {
    private Transaction transaction;
    private TransactionService transactionService;
    private ObjectMapper mapper;

    /**
     * Api controller constructor
     *
     * @param transaction
     * @param transactionService
     */
    @Autowired
    public TransactionController(Transaction transaction, TransactionService transactionService,
                                 ObjectMapper mapper) {
        this.transaction = transaction;
        this.transactionService = transactionService;
        this.mapper = mapper;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Map> triggerTransaction(@RequestBody String body) {
        try {
            transaction = mapper.readValue(body, Transaction.class);
        } catch (JsonProcessingException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "invalid request");
            errorResponse.put("code", "400");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Map<String, String> response = new HashMap<>();
        ResponseEntity<Map> responseEntity = transactionService.triggerTransaction(response, transaction);
        return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
    }
}
