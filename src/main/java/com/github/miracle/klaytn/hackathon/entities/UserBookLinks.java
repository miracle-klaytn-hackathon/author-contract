package com.github.miracle.klaytn.hackathon.entities;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

@MongoEntity(collection = "UserBookLinks")
public class UserBookLinks extends ReactivePanacheMongoEntity {
    public String walletAdress;
    public int bookId;

    public int bookId() {
        return bookId;
    }

    public String getWalletAddress() {
        return walletAdress;
    }
}
