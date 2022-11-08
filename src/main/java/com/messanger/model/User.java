package com.messanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User extends AbstractEntity{
    private String login;
    private String password;

    private String firstname;

    private String lastname;

}
