package com.github.miracle.klaytn.hackathon.utils.mappers;

import java.util.List;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.github.miracle.klaytn.hackathon.entities.BookContract;
import com.github.miracle.klaytn.hackathon.openapi.model.SmartContract;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface BookContractMapper {

    default String toId(ObjectId objectId) {
        return objectId.toHexString();
    }

    @Mapping(source = "id", target = "id", qualifiedByName = "toId")
    List<SmartContract> toSmartContract(List<BookContract> recommendations);

}
