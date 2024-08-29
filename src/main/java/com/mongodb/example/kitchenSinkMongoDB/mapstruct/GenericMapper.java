package com.mongodb.example.kitchenSinkMongoDB.mapstruct;

import com.mongodb.example.kitchenSinkMongoDB.Constants.MongoConstants;
import com.mongodb.example.kitchenSinkMongoDB.Persistence.Model.Member;
import com.mongodb.example.kitchenSinkMongoDB.Persistence.dto.MemberDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = MongoConstants.SPRING_CONSTANT)
public interface GenericMapper {


    Member toMember(MemberDTO memberDTO);

    MemberDTO toMemberDTO(Member member);
//    GenericMapper INSTANCE = Mappers.getMapper(GenericMapper.class);
}
