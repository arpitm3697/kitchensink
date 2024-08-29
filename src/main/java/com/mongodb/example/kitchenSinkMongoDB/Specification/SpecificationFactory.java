package com.mongodb.example.kitchenSinkMongoDB.Specification;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;


@Component
public class SpecificationFactory {


    private final Query query;
    private boolean addToQuery = true;

    public SpecificationFactory() {
        this.query = new Query();
    }

    public SpecificationFactory(boolean addToQuery) {
        this.query = new Query();
        this.addToQuery = addToQuery;
    }


    public void pageable(Pageable pageable) {
        query.with(pageable);
    }

    public CriteriaDefinition remove(String key, Set<Object> collection) {
        CriteriaDefinition criteriaDefinition = Criteria.where(key).in(collection);
        if (this.addToQuery) {
            query.addCriteria(criteriaDefinition);
        }
        return criteriaDefinition;
    }

    public CriteriaDefinition with(String key, Object value) {
        CriteriaDefinition criteriaDefinition = Criteria.where(key).is(value);
        if (this.addToQuery) {
            query.addCriteria(criteriaDefinition);
        }
        return criteriaDefinition;
    }

    public CriteriaDefinition isBetween(String key, Date start, Date end) {
        CriteriaDefinition criteriaDefinition = Criteria.where(key).gt(start).lte(end);
        if (this.addToQuery) {
            query.addCriteria(criteriaDefinition);
        }
        return criteriaDefinition;
    }

    public CriteriaDefinition isIn(String key, Set<String> collection) {
        CriteriaDefinition criteriaDefinition = Criteria.where(key).in(collection);
        if (this.addToQuery) {
            query.addCriteria(criteriaDefinition);
        }
        return criteriaDefinition;
    }

    public Criteria between(String key, Date start, Date end) {
        Criteria criteria = Criteria.where(key).gt(start).lte(end);
        if (this.addToQuery) {
            query.addCriteria(criteria);
        }
        return criteria;
    }

    public Query build() {
        return query;
    }

    public void include(String... fields) {
        query.fields().include(fields).exclude("_id");
    }

    public void sort(Sort.Direction direction, String key) {
        query.with(Sort.by(direction, key));
    }
}
