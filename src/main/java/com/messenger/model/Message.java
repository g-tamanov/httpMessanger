package com.messenger.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Message extends AbstractEntity{

    private User sender;

    private String message;

    private Date timestamp;

}
