package com.mongodb.example.kitchenSinkMongoDB.Service.Implementation;

import com.mongodb.example.kitchenSinkMongoDB.mapstruct.GenericMapper;
import com.mongodb.example.kitchenSinkMongoDB.Persistence.Model.Member;
import com.mongodb.example.kitchenSinkMongoDB.Persistence.dao.MemberRepository;
import com.mongodb.example.kitchenSinkMongoDB.Persistence.dao.QueryExecutor;
import com.mongodb.example.kitchenSinkMongoDB.Persistence.dto.MemberDTO;
import com.mongodb.example.kitchenSinkMongoDB.Persistence.dto.MemberSearchDTO;
import com.mongodb.example.kitchenSinkMongoDB.Service.IMemberRegistrationService;
import com.mongodb.example.kitchenSinkMongoDB.Specification.SpecificationFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class MemberRegistrationService implements IMemberRegistrationService {

    private final MemberRepository memberRepository;
    private final GenericMapper genericMapper;
    private final QueryExecutor queryExecutor;

    @Autowired
    public MemberRegistrationService(MemberRepository memberRepository, GenericMapper genericMapper, QueryExecutor queryExecutor) {
        this.memberRepository = memberRepository;
        this.genericMapper = genericMapper;
        this.queryExecutor = queryExecutor;
    }

    @Override
    public void register(MemberDTO memberDTO) {
        Member member = genericMapper.toMember(memberDTO);
        memberRepository.save(member);
    }

    @Override
    public Page<MemberDTO> getMembers(MemberSearchDTO request, Pageable pageable) {
        SpecificationFactory specificationFactory = new SpecificationFactory();
        specificationFactory.pageable(pageable);
        Date start = new Date(request.getFrom());
        Date end = new Date();
        if(Objects.nonNull(request.getTo())) {
            end = new Date(request.getTo());
        }
        specificationFactory.isBetween("createdDate", start, end);

        if(!StringUtils.isEmpty(request.getEmail())) {
            specificationFactory.with("email", request.getEmail());
        }
        if(!StringUtils.isEmpty(request.getPhoneNumber())) {
            specificationFactory.with("phoneNumber", request.getPhoneNumber());
        }

        // Add sorting if provided by Pageable
        if (Objects.nonNull(pageable.getSort()) && pageable.getSort().isSorted()) {
            pageable.getSort().forEach(order -> specificationFactory.sort(order.getDirection(), order.getProperty()));
        } else {
            // Default sorting
            specificationFactory.sort(Sort.Direction.ASC, "name");
        }
        if (!CollectionUtils.isEmpty(request.getFields())) {
            request.getFields().forEach(specificationFactory::include);
        }
        Page<Member> members = queryExecutor.execute(specificationFactory.build(), Member.class, pageable);
        return members.map(genericMapper::toMemberDTO);

    }
}
