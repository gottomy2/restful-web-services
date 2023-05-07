package com.gottomy2.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;

//@JsonIgnoreProperties({"field1", "field2"}) //Static Filtering example: ignores fields field1 and field2 when returning JSON in REST-API calls
@JsonFilter("SomeBeanFilter") // Allows for dynamic filteriing based on filter named "SomeBeanFilter"
class SomeBean {

    private String field1;
    //    @JsonIgnore // Ignores field2 while returning JSON in REST-API calls
    private String field2;
    private String field3;

    public SomeBean(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public String getField3() {
        return field3;
    }

    @Override
    public String toString() {
        return "SomeBean{" +
               "field1='" + field1 + '\'' +
               ", field2='" + field2 + '\'' +
               ", field3='" + field3 + '\'' +
               '}';
    }
}
