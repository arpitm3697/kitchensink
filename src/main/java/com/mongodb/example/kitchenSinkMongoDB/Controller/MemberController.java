package com.mongodb.example.kitchenSinkMongoDB.Controller;

import com.mongodb.example.kitchenSinkMongoDB.Persistence.dto.MemberDTO;
import com.mongodb.example.kitchenSinkMongoDB.Persistence.dto.MemberSearchDTO;
import com.mongodb.example.kitchenSinkMongoDB.Service.IMemberRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final IMemberRegistrationService memberRegistrationService;

    @Autowired
    private MemberController(IMemberRegistrationService memberRegistrationService) {
        this.memberRegistrationService = memberRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberDTO newMember) {
        memberRegistrationService.register(newMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<MemberDTO>> getAllMembers(Pageable pageable, MemberSearchDTO memberSearchDTO) {
        Page<MemberDTO> memberDTOS = memberRegistrationService.getMembers(memberSearchDTO, pageable);
        return new ResponseEntity<Page<MemberDTO>>(memberDTOS, HttpStatus.OK);
    }
}
