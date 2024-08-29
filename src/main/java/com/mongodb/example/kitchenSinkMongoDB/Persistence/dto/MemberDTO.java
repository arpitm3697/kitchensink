package com.mongodb.example.kitchenSinkMongoDB.Persistence.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class MemberDTO implements Serializable {

    private static final long serialVersionUID = -394666041605250274L;

    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    private Long id;

}
