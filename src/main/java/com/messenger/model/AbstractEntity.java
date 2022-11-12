package com.messenger.model;


import lombok.Data;

import java.util.UUID;

@Data
public abstract class AbstractEntity {

    private UUID uuid=UUID.randomUUID();

}
