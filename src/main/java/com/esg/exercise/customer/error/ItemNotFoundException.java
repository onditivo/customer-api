package com.esg.exercise.customer.error;

public class ItemNotFoundException  extends RuntimeException {

    private String reference;

    public ItemNotFoundException(String reference) {
        super("Could not find customer reference " + reference);
    }
}
