package com.messenger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User extends AbstractEntity{
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID session;

    private String firstname;

    private String lastname;

}
