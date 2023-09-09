package com.github.miracle.klaytn.hackathon.utils.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.github.miracle.klaytn.hackathon.entities.BookContractRecommendation;
import com.github.miracle.klaytn.hackathon.openapi.model.SmartContract;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface BookContractMapper {

    List<SmartContract> toSmartContract(List<BookContractRecommendation> recommendations);

}
