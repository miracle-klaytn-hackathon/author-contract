package com.github.miracle.klaytn.hackathon.entities;

import org.bson.types.Binary;
import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MongoEntity(collection = "BookContractMetadata")
public class BookContractMetadata extends ReactivePanacheMongoEntity {
    private ObjectId id;
    private Binary imageBinary;
    private String title;
    private String description;
}
