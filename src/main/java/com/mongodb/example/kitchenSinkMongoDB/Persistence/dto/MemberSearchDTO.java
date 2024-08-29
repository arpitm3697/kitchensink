package com.mongodb.example.kitchenSinkMongoDB.Persistence.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class MemberSearchDTO implements Serializable {

    private Long id;
    private String name;
    private String email;
    private Long from;
    private Long to;
    private String phoneNumber;
    private List<String> fields;
}
