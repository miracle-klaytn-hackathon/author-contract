package com.github.miracle.klaytn.hackathon.entities;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

@MongoEntity(collection = "User")
public class User extends ReactivePanacheMongoEntity {
    public String walletAdress;
    public String avatarLink;
    public String userName;
    public String email;
    public int totalNft;
    public int totalTransaction;

    public String getName() {
        return userName;
    }

    public String getWalletAddress() {
        return walletAdress;
    }

    public String getEmail() {
        return email;
    }

    public int getTotalNft() {
        return totalNft;
    }

     public int getTotalTransaction() {
        return totalTransaction;
    }
}
