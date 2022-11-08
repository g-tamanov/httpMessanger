package com.messanger.model;


import lombok.Data;

import javax.persistence.Transient;
import java.util.UUID;

@Data
public abstract class AbstractEntity {

    @Transient
    private UUID uuid;

}
