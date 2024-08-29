package com.mongodb.example.kitchenSinkMongoDB.Persistence.Model;



import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Document(collection = "members")
public class Member extends BaseEntity {

    @Id
    private String id;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String name;

    @NotNull
    @Email
    @NotEmpty
    private String email;

    @NotNull
    @Size(min = 10, max = 12)
    @Digits(fraction = 0, integer = 12)
    @Field(name = "phone_number")
    private String phoneNumber;

    // Default constructor
    public Member() {
    }
}
