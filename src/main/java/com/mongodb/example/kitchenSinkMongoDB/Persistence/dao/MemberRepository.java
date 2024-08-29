package com.mongodb.example.kitchenSinkMongoDB.Persistence.dao;

import com.mongodb.example.kitchenSinkMongoDB.Persistence.Model.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface MemberRepository extends MongoRepository<Member, String> {

    // Find a member by email
    @Query("{ 'email': ?0 }")
    Optional<Member> findByEmail(String email);
}
