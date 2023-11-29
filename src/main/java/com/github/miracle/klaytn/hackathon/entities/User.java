package com.github.miracle.klaytn.hackathon.entities;

import org.bson.Document;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;

@MongoEntity(collection = "User")
public class User extends ReactivePanacheMongoEntity {
    public String walletAddress;
    public String avatarUrl;
    public String username;
    public String email;
    public int totalNft;
    public int totalTransaction;

    public String getName() {
        return username;
    }

    public String getWalletAddress() {
        return walletAddress;
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

    public static Uni<User> findByWalletAddress(String walletAddress) {
        return find("walletAddress", walletAddress).firstResult();
    }
}
