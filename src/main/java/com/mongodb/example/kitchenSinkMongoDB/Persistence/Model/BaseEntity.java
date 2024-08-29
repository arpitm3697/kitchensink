package com.mongodb.example.kitchenSinkMongoDB.Persistence.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mongodb.example.kitchenSinkMongoDB.Constants.MongoConstants;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = MongoConstants.ISO_8601_DATE_TIME_FORMAT_WITHOUT_T, timezone = MongoConstants.TIMEZONE_IST)
    @CreatedDate
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = MongoConstants.ISO_8601_DATE_TIME_FORMAT_WITHOUT_T, timezone = MongoConstants.TIMEZONE_IST)
    @LastModifiedDate
    private Date lastModifiedDate;

    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long lastModifiedBy;
}