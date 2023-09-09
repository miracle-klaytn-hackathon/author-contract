package com.github.miracle.klaytn.hackathon.entities;

import java.time.LocalDate;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

@MongoEntity(collection = "BookContractRecommendation")
public class BookContractRecommendation extends ReactivePanacheMongoEntity {
    public ObjectId id;
    public LocalDate recommendDate;
    public int rank;
    public String name;
    public String address;
    public String symbol;
    public String owner;
}
