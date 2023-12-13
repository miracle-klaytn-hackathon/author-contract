package com.github.miracle.klaytn.hackathon.utils.mappers;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import com.github.miracle.klaytn.hackathon.entities.User;
import com.github.miracle.klaytn.hackathon.openapi.model.UserProfile;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface UserMapper {

    @Named("toId")
    default String toId(ObjectId objectId) {
        return objectId.toHexString();
    }
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalNft", ignore = true)
    @Mapping(target = "totalTransaction", ignore = true)
    User toUser(UserProfile userProfile);

    @Mapping(source = "id", target = "id", qualifiedByName = "toId")
    UserProfile toUserProfile(User user);

}
