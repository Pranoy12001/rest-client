package com.rest.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.client.exception.ClientException;
import com.rest.client.model.Transaction;
import com.rest.client.service.TransactionService;
import com.rest.client.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.ConnectException;
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
        } catch (JsonProcessingException ex) {
            ClientException clientException =
                    ClientException.create("400", "invalid request", ex);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", clientException.getMessage());
            errorResponse.put("code", clientException.getCode());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            RequestValidator.validate(transaction);
        } catch (ClientException exception) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("code", exception.getCode());
            errorMap.put("message", exception.getMessage());
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<Map> responseEntity = transactionService.triggerTransaction(transaction, body);
        return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
    }
}
