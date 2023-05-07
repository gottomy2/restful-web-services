package com.gottomy2.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
class FilteringController {

    //Returns a MappingJacksonValue filtered to contain only fields provided within String[] fields:
    public MappingJacksonValue filterAllExcept(Object bean, String[] fields){

        //Creating MappingJacksonValue on retrieved bean
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(bean);

        //Creating Single filter on retrieved bean to filter out all fields except provided in fields Array.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);

        //Creating filterProvider with list of filters and adding Single filter to it:
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);

        //Setting mappingJacksonValue Filters to (FilterProvider) filters:
        mappingJacksonValue.setFilters(filters);

        //Returning the dynamically filtered JSON response for the provided bean:
        return mappingJacksonValue;
    }

    @GetMapping("/filtering")
    public MappingJacksonValue filtering() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");

        return filterAllExcept(someBean,new String[]{"field1","field2"});
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList() {
        List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),new SomeBean("value4", "value5", "value6"));

        return filterAllExcept(list,new String[]{"field2","field3"});
    }
}
