package com.mongodb.example.kitchenSinkMongoDB.Service;

import com.mongodb.example.kitchenSinkMongoDB.Persistence.dto.MemberDTO;
import com.mongodb.example.kitchenSinkMongoDB.Persistence.dto.MemberSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMemberRegistrationService {

    void register(MemberDTO memberDTO);

    Page<MemberDTO> getMembers(MemberSearchDTO memberSearchDTO, Pageable pageable);
}
