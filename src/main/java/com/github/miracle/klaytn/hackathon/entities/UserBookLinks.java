package com.github.miracle.klaytn.hackathon.entities;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MongoEntity(collection = "UserBookLinks")
public class UserBookLinks extends ReactivePanacheMongoEntity {
    private String walletAdress;
    private int bookId;
}
