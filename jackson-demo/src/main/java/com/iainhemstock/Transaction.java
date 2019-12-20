package com.iainhemstock;

import lombok.Getter;

import java.util.Date;

@Getter
public class Transaction {
    private Date date;

    public Transaction(Date date) {
        this.date = date;
    }
}
