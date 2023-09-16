package com.github.miracle.klaytn.hackathon.entities;

import org.bson.types.Binary;
import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

@MongoEntity(collection = "BookContractMetadata")
public class BookContractMetadata extends ReactivePanacheMongoEntity {
    public ObjectId id;
    public Binary imageBinary;
    public String title;
    public String description;
}
