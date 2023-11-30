package com.github.miracle.klaytn.hackathon.entities;

import java.time.LocalDate;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MongoEntity(collection = "BookContract")
public class BookContract extends ReactivePanacheMongoEntity {
    private ObjectId id;
    private LocalDate recommendDate;
    private int rank;
    private String name;
    private String address;
    private String symbol;
    private String owner;
}
