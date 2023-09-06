package com.github.miracle.klaytn.hackathon.entities;

import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@MongoEntity(collection = "BookContractRecommendation")
public class BookTokenRecommendation extends PanacheMongoEntity {

    @BsonProperty
    private LocalDate recommendDate;

    @BsonProperty
    private int rank;

    @BsonProperty
    private String tokenName;
    
    @BsonProperty
    private String tokenAddress;

    @BsonProperty
    private String tokenSymbol;

    @BsonProperty
    private String tokenOwner;

}
