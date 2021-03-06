package com.str.orm;

import com.mysql.cj.x.protobuf.Mysqlx;

import java.util.ArrayList;
import java.util.List;

public final class OrderBy<T> extends CriteriaQuery<T> {

    public OrderBy(Criteria<T> criteria, String orderBy) {
        super(criteria);
        orderBy(orderBy);
    }

    /**
     * @param  orderBy The field name.
     * @return Criteria query object.
     * */
    public OrderBy<T> orderBy(String orderBy) {
        if (criteria.orderBy == null) {
            criteria.orderBy = new ArrayList<>();
        }
        orderBy = checkProperty(orderBy);
        criteria.orderBy.add(orderBy);
        return this;
    }

    String checkProperty(String orderBy) {
        String prop = null;
        String upper = orderBy.toUpperCase();
        if (upper.endsWith(" DESC")) {
            prop = orderBy.substring(0, orderBy.length() - 5).trim();
            return propertyToField(prop) + " DESC";
        } else if (upper.endsWith(" ASC")) {
            prop = orderBy.substring(0, orderBy.length() - 4).trim();
            return propertyToField(prop) + " ASC";
        } else {
            prop = orderBy.trim();
            return propertyToField(prop);
        }
    }

    String propertyToField(String prop) {
        AccessibleProperty ap = this.criteria.mapper.allPropertiesMap.get(prop.toLowerCase());
        if (ap == null) {
            throw new IllegalArgumentException("Invalid property when use order by: " + prop);
        }
        return ap.columnName;
    }

    public OrderBy<T> desc() {
        int last = this.criteria.orderBy.size() - 1;
        String s = criteria.orderBy.get(last);
        if (!s.toUpperCase().endsWith(" DESC")) {
            s = s + " DESC";
        }
        criteria.orderBy.set(last, s);
        return this;
    }

    /**
     * Add limit clause
     * */
    public Limit<T> limit(int maxResults) {
        return limit(0, maxResults);
    }

    public Limit<T> limit(int offset, int maxResults) {
        return new Limit<>(this.criteria, offset, maxResults);
    }

    public List<T> list() {
        return criteria.list();
    }

    public T first() {
        return criteria.first();
    }
 }
