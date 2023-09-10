package com.github.miracle.klaytn.hackathon.entities;

import java.time.LocalDate;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

@MongoEntity(collection = "BookContract")
public class BookContract extends ReactivePanacheMongoEntity {
    public ObjectId id;
    public LocalDate recommendDate;
    public int rank;
    public String name;
    public String address;
    public String symbol;
    public String owner;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getOwner() {
        return owner;
    }
}
