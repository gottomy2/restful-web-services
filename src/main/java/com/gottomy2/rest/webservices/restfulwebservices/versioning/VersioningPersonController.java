package com.gottomy2.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Controller created to practice different ways of versioning REST API:
@RestController
public class VersioningPersonController {

    //Versioning through URI:
    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionOfPerson(){
        return new PersonV1("Igor Motowidło");
    }

    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPerson(){
        return new PersonV2(new Name("Igor","Motowidło"));
    }

    //Versioning through RequestParameter:
    @GetMapping(path="/person", params = "version=1")
    public PersonV1 getFirstVersionOfPersonRequestParameter(){
        return new PersonV1("Igor Motowidło");
    }

    @GetMapping(path="/person", params = "version=2")
    public PersonV2 getSecondVersionOfPersonRequestParameter(){
        return new PersonV2(new Name("Igor","Motowidło"));
    }

    //Versioning through request header:
    @GetMapping(path="/person/header", headers = "version=1")
    public PersonV1 getFirstVersionOfPersonRequestHeader(){
        return new PersonV1("Igor Motowidło");
    }

    @GetMapping(path="/person/header", headers = "version=2")
    public PersonV2 getSecondVersionOfPersonRequestHeader(){
        return new PersonV2(new Name("Igor","Motowidło"));
    }

    //Media Type versioning:
    @GetMapping(path="/person/accept", produces = "application/com.gottomy2-v1+json")
    public PersonV1 getFirstVersionOfPersonMediaType(){
        return new PersonV1("Igor Motowidło");
    }

    @GetMapping(path="/person/accept", produces = "application/com.gottomy2-v2+json")
    public PersonV2 getSecondVersionOfPersonMediaType(){
        return new PersonV2(new Name("Igor","Motowidło"));
    }
}
