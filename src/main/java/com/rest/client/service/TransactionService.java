package com.rest.client.service;

import com.rest.client.model.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TransactionService {
    public ResponseEntity<Map> triggerTransaction(Transaction transaction, String body);
}
