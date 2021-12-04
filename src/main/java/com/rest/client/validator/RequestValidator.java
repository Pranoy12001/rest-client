package com.rest.client.validator;

import com.rest.client.exception.ClientException;
import com.rest.client.model.Transaction;

import java.util.Objects;

public class RequestValidator {
    public static void validate(Transaction transaction) {
        if (Objects.isNull(transaction)) {
            throw ClientException.create("400", "invalid request", null);
        } else if (Objects.isNull(transaction.getRequestId())
                || Objects.isNull(transaction.getRequester())
                || Objects.isNull(transaction.getTransactionType())
                || Objects.isNull(transaction.getTransactionType())
                || Objects.isNull(transaction.getAmount())
                || Objects.isNull(transaction.getDestinationAccountNumber())
                || Objects.isNull(transaction.getNote())) {
            throw ClientException.create("400", "invalid request", null);
        }
    }
}
