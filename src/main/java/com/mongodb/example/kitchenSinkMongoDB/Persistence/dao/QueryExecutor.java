package com.mongodb.example.kitchenSinkMongoDB.Persistence.dao;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author - Arpit Matha
 */

@Repository
@Slf4j
public class QueryExecutor {

    @Autowired
    private MongoTemplate mongoTemplate;

    public <T> List<T> execute(Query query, Class<T> entityClass) {
        return mongoTemplate.find(query, entityClass);
    }

    public <T> Page<T> execute(Query query, Class<T> entityClass, Pageable pageable) {
        long total = mongoTemplate.count(query, entityClass);  // Get the total count of documents matching the query

        // Apply pagination to the query
        query.with(pageable);

        // Execute the paginated query and get the result
        List<T> results = mongoTemplate.find(query, entityClass);

        // Return the results as a Page object
        return new PageImpl<>(results, pageable, total);
    }


    public <T> void remove(Query query, Class<T> entityClass) {
        mongoTemplate.remove(query, entityClass);
    }

    public <T> void save(T objectToSave) {
        mongoTemplate.save(objectToSave);
    }

    public <T> List<T> aggregate(Aggregation aggregation, Class<?> entityClass, Class<T> outputType) {
        AggregationResults<T> res =  mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(entityClass), outputType);
        log.info("res {}", res.getRawResults());
        log.info("mapped {}", res.getMappedResults());
        return res.getMappedResults();
    }

    public <T> T aggregateUnique(Aggregation aggregation, Class<?> entityClass, Class<T> outputType) {
        AggregationResults<T> res =  mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(entityClass), outputType);
        log.info("res {}", res.getRawResults());
        log.info("mapped {}", res.getMappedResults());
        return res.getUniqueMappedResult();
    }

    public AggregationOperation getMatchOperation(CriteriaDefinition criteria) {
        return Aggregation.match(criteria);
    }

    public GroupOperation getGroupCountOperation(String... args) {
        return Aggregation.group(args).count().as("count");
    }

    public <T> AggregationOperation getProjectionOperation(Class<T> mappedClass, String idName) {
        return Aggregation.project(mappedClass).and(idName).previousOperation();
    }

    public AggregationOperation getSortOperation(Sort.Direction direction, String... args) {
        return Aggregation.sort(direction, args);
    }

    public <T> long update(Query query, Update update, Class<T> entityClass) {
        return mongoTemplate.updateMulti(query, update, entityClass).getModifiedCount();
    }
}
