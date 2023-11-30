package com.github.miracle.klaytn.hackathon.entities;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MongoEntity(collection = "User")
public class User extends ReactivePanacheMongoEntity {
    private String walletAddress;
    private String avatarUrl;
    private String username;
    private String email;
    private int totalNft;
    private int totalTransaction;

    public static Uni<Void> isUserNotExist(String walletAddress) {
        return findByWalletAddress(walletAddress)
                .onItem().ifNotNull().failWith(new RuntimeException("User Existed!"))
                .onItem().ignore().andContinueWithNull();
    }

    public static Uni<User> findByWalletAddress(String walletAddress) {
        return find("walletAddress", walletAddress).<User>firstResult()
                .onItem().ifNull().failWith(new RuntimeException("User Not Found!"));
    }
}
